package ru.ayubdzhanov.javaquiz.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.dao.CompetitionRepository;
import ru.ayubdzhanov.javaquiz.dao.ContestantInfoRepository;
import ru.ayubdzhanov.javaquiz.dao.TaskRepository;
import ru.ayubdzhanov.javaquiz.dao.UserDataRepository;
import ru.ayubdzhanov.javaquiz.domain.Competition;
import ru.ayubdzhanov.javaquiz.domain.CompetitionInfo;
import ru.ayubdzhanov.javaquiz.domain.ContestantInfo;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.TaskOption;
import ru.ayubdzhanov.javaquiz.util.ViewUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CompetitionService {
    @Autowired
    private CompetitionInfoService competitionInfoService;
    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private UserDataContainer userDataContainer; //TODO enlarge container
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ContestantInfoRepository contestantInfoRepository;

    public Competition getCompetition(Long categoryId, Long existedCompetitionId) {
        return existedCompetitionId == null ? getNewCompetition(categoryId) : getExistedCompetition(existedCompetitionId);
    }

    public Competition getExistedCompetition(Long existedCompetitionId) {
        Competition existedCompetition = competitionRepository.getOne(existedCompetitionId);
        wrapTasks(existedCompetition.getTasks());
        return wrapCompetition(existedCompetition);
    }

    public List<Competition> getChallenges() {
        List<Competition> allChallenges = competitionRepository.findAllChallenges(userDataContainer.getId(), PageRequest.of(0, 10));
        allChallenges = allChallenges.stream().filter(competition -> {
            ContestantInfo currentContestant = competition.getContestants().stream().filter(contestant -> contestant.getUserData().getId().equals(userDataContainer.getId())).findFirst().get();
            return currentContestant.getScore() == null;
        }).collect(Collectors.toList());
        if (allChallenges.isEmpty()) return Collections.emptyList();
        return wrapCompetitions(allChallenges);
    }

    public List<Competition> getWaitingCompetitions() {
        List<Competition> allChallenges = competitionRepository.findAllChallenges(userDataContainer.getId(), PageRequest.of(0, 10));
        allChallenges = allChallenges.stream().filter(competition -> {
            ContestantInfo currentContestant = competition.getContestants().stream().filter(contestant -> contestant.getUserData().getId().equals(userDataContainer.getId())).findFirst().get();
            return currentContestant.getScore() != null;
        }).collect(Collectors.toList());
        if (allChallenges.isEmpty()) return Collections.emptyList();
        return wrapCompetitions(allChallenges);
    }

    public List<Competition> getContestantBattleHistory() {
        return wrapCompetitions(competitionRepository.findAllCompletedCompetitionsByUserId(userDataContainer.getId(), PageRequest.of(0, 10)));
    }

    private Competition getNewCompetition(Long categoryId) {
        Competition newCompetition = new Competition();
        ContestantInfo currentContestant = new ContestantInfo();
        currentContestant.setUserData(userDataRepository.getOne(userDataContainer.getId()));
        currentContestant.setCompetition(newCompetition);
        List<ContestantInfo> allAccessibleContestants = contestantInfoRepository.findAllAccessibleContestants(userDataContainer.getId(), categoryId);
        if (!allAccessibleContestants.isEmpty()) {
            Random r = new Random();
            ContestantInfo possibleContestant = allAccessibleContestants.get(r.nextInt(allAccessibleContestants.size()));
            ContestantInfo opponent = new ContestantInfo();
            opponent.setCompetition(newCompetition);
            opponent.setUserData(possibleContestant.getUserData());
            newCompetition.getContestants().add(opponent);
        }
        List<Task> tasks = getRandomTasks(taskRepository.findAllByCategoryIdAndIsApproved(categoryId, Boolean.TRUE, PageRequest.of(0, 200))); //better to cache
        wrapTasks(tasks);
        newCompetition.setTasks(tasks);
        newCompetition.setStartedAt(LocalDateTime.now());
        newCompetition.getContestants().add(currentContestant);
        newCompetition.setCategory(categoryRepository.getOne(categoryId));
        competitionRepository.save(newCompetition);
        return newCompetition;
    }

    private List<Task> getRandomTasks(List<Task> tasks) {
        Random r = new Random();
        List<Task> filteredTasks = new LinkedList<>();
        while (filteredTasks.size() < 5) {
            int randomIndex = r.nextInt(tasks.size());
            if (filteredTasks.contains(tasks.get(randomIndex))) {
                continue;
            }
            filteredTasks.add(tasks.get(randomIndex));
        }
        return filteredTasks;
    }

    public void finishCompetition(MultiValueMap<String, String> allParams) {
        Competition competition = competitionRepository.getOne(Long.parseLong(allParams.get("competitionId").get(0)));
        AtomicInteger score = new AtomicInteger(0);
        AtomicInteger prestige = new AtomicInteger(0);
        ContestantInfo currentContestant = competition.getContestants().stream().filter(contestant -> contestant.getUserData().getId() == Long.parseLong(allParams.get("currentContestant").get(0))).findFirst().get();
        competition.getTasks().forEach(task -> {
            if (!allParams.containsKey(task.getId() + "")) {
                return;
            }
            currentContestant.getContestantResults()
                .addAll(task.getTaskOption()
                    .stream()
                    .filter(taskOption -> allParams.get(task.getId() + "").contains(taskOption.getOption().getId() + ""))
                    .collect(Collectors.toList()));
            if (task.getTaskOption().stream().allMatch(taskOption ->
                (taskOption.getIsCorrect() && allParams.get(task.getId() + "").contains(taskOption.getOption().getId() + "")) ||
                    (!taskOption.getIsCorrect() && !allParams.get(task.getId() + "").contains(taskOption.getOption().getId() + "")))) {
                score.incrementAndGet();
                prestige.addAndGet(task.getPrestige());
            }
        });
        currentContestant.setScore(score.get());
        currentContestant.setPrestige(prestige.get());
        if (competition.getContestants().stream().allMatch(contestant -> contestant.getScore() != null)) {
            competition.setFinishedAt(LocalDateTime.now());
            ContestantInfo opponent = competition.getContestants().stream().filter(contestant -> !contestant.getId().equals(currentContestant.getId())).findFirst().get();
            if (currentContestant.getScore() > opponent.getScore()) {
                setCompetitionResults(competition, currentContestant, opponent);
            } else if (currentContestant.getScore() < opponent.getScore()) {
                setCompetitionResults(competition, opponent, currentContestant);
            } else {
                opponent.getUserData().setDraws(opponent.getUserData().getDraws());
                opponent.setPrestige(0);
                currentContestant.setPrestige(0);
                currentContestant.getUserData().setDraws(currentContestant.getUserData().getDraws());
            }
        }
        competitionRepository.save(competition);
    }

    private void setCompetitionResults(Competition competition, ContestantInfo winner, ContestantInfo loser) {
        loser.setPrestige(getNegativePrestige(loser, competition));
        loser.getUserData().setDefeats(loser.getUserData().getDefeats() + 1);
        winner.getUserData().setVictories(winner.getUserData().getVictories() + 1);
        winner.getUserData().setPrestige(winner.getUserData().getPrestige() + winner.getPrestige());
        loser.getUserData().setPrestige(loser.getUserData().getPrestige() + loser.getPrestige());
    }

    private Integer getNegativePrestige(ContestantInfo info, Competition competition) {
        List<TaskOption> contestantResults = info.getContestantResults();
        AtomicInteger prestige = new AtomicInteger(0);
        List<Task> tasks = competition.getTasks();
        tasks.forEach(task -> {
            if (task.getTaskOption().stream().allMatch(taskOption ->
                (taskOption.getIsCorrect() && contestantResults.stream().anyMatch(tOption -> tOption.equals(taskOption))) ||
                    (!taskOption.getIsCorrect() && contestantResults.stream().noneMatch(tOption -> tOption.equals(taskOption))))) {
                prestige.addAndGet(task.getPrestige());
            }
        });
        Integer totalPrestige = tasks.stream().map(Task::getPrestige).reduce(Integer::sum).get();
        return prestige.get() - totalPrestige;
    }


    public ContestantInfo getOpponent(Competition competition) {
        return competition.getContestants().stream().filter(contestant -> !contestant.getUserData().getId().equals(userDataContainer.getId())).findFirst().orElse(null);
    }

    public ContestantInfo getCurrentContestant(Competition competition) {
        return competition.getContestants().stream().filter(contestant -> contestant.getUserData().getId().equals(userDataContainer.getId())).findFirst().orElse(null);
    }

    public List<CompetitionInfo> getCompetitionsList() {
        return competitionInfoService.findAll();
    }

    public CompetitionInfo getCompetitionInfo(String categoryId) {
        return competitionInfoService.getCompetitionInfoByCategoryId(categoryId);
    }

    private void wrapTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setViewUtil(ViewUtils
                .builder()
                .menu("menu" + tasks.get(i).getId())
                .menuCounter(i + 1)
                .build());
        }
    }

    private List<Competition> wrapCompetitions(List<Competition> competitions) {
        competitions.forEach(competition -> {
            ContestantInfo currentPlayer = competition.getContestants().stream().filter(com -> com.getUserData().getId().equals(userDataContainer.getId())).findFirst().get();
            ContestantInfo opponent = competition.getContestants().stream().filter(com -> !com.getUserData().getId().equals(userDataContainer.getId())).findFirst().get();
            competition.setContestants(Arrays.asList(currentPlayer, opponent));
            String style;
            if (currentPlayer.getScore() == null || opponent.getScore() == null) {
                competition.setViewUtil(ViewUtils
                    .builder()
                    .imageLink(competitionsList.stream().filter(c -> c.getCategory().getId().equals(competition.getCategory().getId())).findFirst().get().getImageLink())
                    .userData(userDataRepository.getOne(competition.getContestants().stream().filter(con -> !con.getUserData().getId().equals(userDataContainer.getId())).findFirst().get().getUserData().getId()))
                    .build());
                return;
            }
            if (currentPlayer.getScore() > opponent.getScore()) {
                style = "border-width: 2px; border-color: #2df622; border-radius: 15px;";
            } else if (currentPlayer.getScore() < opponent.getScore()) {
                style = "border-width: 2px; border-color: red; border-radius: 15px;";
            } else {
                style = "border-width: 2px; border-color: grey; border-radius: 15px;";
            }
            competition.setViewUtil(ViewUtils
                .builder()
                .imageLink(competitionsList.stream().filter(c -> c.getCategory().getId().equals(competition.getCategory().getId())).findFirst().get().getImageLink())
                .userData(userDataRepository.getOne(competition.getContestants().stream().filter(con -> !con.getUserData().getId().equals(userDataContainer.getId())).findFirst().get().getUserData().getId()))
                .style(style)
                .build());
        });
        return competitions;
    }

    public Competition wrapCompetition(Competition competition){
        competition.setViewUtil(ViewUtils
            .builder()
            .imageLink(competitionsList.stream().filter(c -> c.getCategory().getId().equals(competition.getCategory().getId())).findFirst().get().getImageLink())
            .build());
        return competition;
    }

    public List<Task> getCompetitionsCorrectResults(Competition competition) {
        return competition.getTasks();
    }

    public List<ContestantResultWrapper> getContestantResults(Competition competition, Boolean isForCurrentContestant) {
        ContestantInfo contestant;
        if (isForCurrentContestant) {
            contestant = getCurrentContestant(competition);
        } else {
            contestant = getOpponent(competition);
        }
        return getContestantResultWrapper(competition, contestant, competition.getTasks(), contestant.getContestantResults());
    }

    private List<ContestantResultWrapper> getContestantResultWrapper(Competition competition, ContestantInfo contestant, List<Task> competitionTasks, List<TaskOption> contestantResults) {
        List<ContestantResultWrapper> contestantResultWrapperList = new LinkedList<>();
        competitionTasks.forEach(task -> contestantResultWrapperList.add(new ContestantResultWrapper(task, contestantResults.stream().filter(taskOption -> taskOption.getTask().equals(task)).collect(Collectors.toList()))));
        contestantResultWrapperList.forEach(result -> {
            if (result.getTaskOptions().stream().anyMatch(taskOption -> !taskOption.getIsCorrect())) {
                result.setIsCorrect(Boolean.FALSE);
            } else {
                result.setIsCorrect(Boolean.TRUE);
            }
            ContestantInfo opponent = competition.getContestants().stream()
                .filter(c -> !c.getId().equals(contestant.getId()))
                .findFirst().get();
            if (contestant.getScore() > opponent.getScore()) {
                result.setStatus("WINNER");
            } else if (contestant.getScore() < opponent.getScore()) {
                result.setStatus("LOSER");
            } else {
                result.setStatus("NONE");
            }
        });
        return contestantResultWrapperList;
    }

    public Long getCurrentContestantId(){
        return userDataContainer.getId();
    }

    public List<Competition> getPastCompetitions(Long categoryId) {
        return competitionRepository.findAllCompletedCompetitions(categoryId, PageRequest.of(0, 10));
    }

    @Getter
    @Setter
    static class ContestantResultWrapper {
        private Task task;
        private List<TaskOption> taskOptions;
        private Boolean isCorrect;
        private String status;

        public ContestantResultWrapper(Task task, List<TaskOption> taskOptions) {
            this.task = task;
            this.taskOptions = taskOptions;
        }
    }
}
