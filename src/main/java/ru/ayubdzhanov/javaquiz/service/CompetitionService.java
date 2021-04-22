package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.dao.CompetitionInfoRepository;
import ru.ayubdzhanov.javaquiz.dao.CompetitionRepository;
import ru.ayubdzhanov.javaquiz.dao.ContestantInfoRepository;
import ru.ayubdzhanov.javaquiz.dao.TaskRepository;
import ru.ayubdzhanov.javaquiz.dao.UserDataRepository;
import ru.ayubdzhanov.javaquiz.domain.Competition;
import ru.ayubdzhanov.javaquiz.domain.CompetitionInfo;
import ru.ayubdzhanov.javaquiz.domain.ContestantInfo;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.TaskOption;
import ru.ayubdzhanov.javaquiz.domain.UserData;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CompetitionService {
    @Autowired
    private CompetitionInfoRepository competitionInfoRepository;
    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private UserDataContainer userDataContainer;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ContestantInfoRepository contestantInfoRepository;

    private List<CompetitionInfo> competitionsList;

    public Competition getCompetition(Long categoryId, Long existedCompetitionId) {
        return existedCompetitionId == null ? getNewCompetition(categoryId) : getExistedCompetition(existedCompetitionId);
    }

    public Competition getExistedCompetition(Long existedCompetitionId) {
        Competition existedCompetition = competitionRepository.getOne(existedCompetitionId);
        wrapTasks(existedCompetition.getTasks());
        return existedCompetition;
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
        return wrapCompetitions(competitionRepository.findAllCompletedCompetitions(userDataContainer.getId(), PageRequest.of(0, 10)));
    }

    private Competition getNewCompetition(Long categoryId) {
        Competition newCompetition = new Competition();
        ContestantInfo currentContestant = new ContestantInfo();
        currentContestant.setUserData(userDataRepository.getOne(userDataContainer.getId()));
        currentContestant.setCompetition(newCompetition);
        List<ContestantInfo> allAccessibleContestants = contestantInfoRepository.findAllAccessibleContestants(userDataContainer.getId(), categoryId);
        if (!allAccessibleContestants.isEmpty()) {
            ContestantInfo possibleContestant = allAccessibleContestants.stream().findFirst().get();
            ContestantInfo opponent = new ContestantInfo();
            opponent.setCompetition(newCompetition);
            opponent.setUserData(possibleContestant.getUserData());
            newCompetition.getContestants().add(opponent);
        }
        List<Task> tasks = taskRepository.findAllByCategoryId(categoryId, PageRequest.of(0, 5));
        wrapTasks(tasks);
        newCompetition.setTasks(tasks);
        newCompetition.setStartedAt(LocalDateTime.now());
        newCompetition.getContestants().add(currentContestant);
        newCompetition.setCategory(categoryRepository.getOne(categoryId));
        competitionRepository.save(newCompetition);
        return newCompetition;
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
                (taskOption.getCorrect() && allParams.get(task.getId() + "").contains(taskOption.getOption().getId() + "")) ||
                    (!taskOption.getCorrect() && !allParams.get(task.getId() + "").contains(taskOption.getOption().getId() + "")))) {
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
                opponent.setPrestige(getNegativePrestige(opponent, competition));
                opponent.getUserData().setDefeats(opponent.getUserData().getDefeats() + 1);
                currentContestant.getUserData().setVictories(currentContestant.getUserData().getVictories() + 1);
            } else if (currentContestant.getScore() < opponent.getScore()) {
                currentContestant.setPrestige(getNegativePrestige(currentContestant, competition));
                currentContestant.getUserData().setDefeats(currentContestant.getUserData().getDefeats() + 1);
                opponent.getUserData().setVictories(opponent.getUserData().getVictories() + 1);
            } else {
                opponent.getUserData().setDraws(opponent.getUserData().getDraws());
                opponent.setPrestige(0);
                currentContestant.setPrestige(0);
                currentContestant.getUserData().setDraws(currentContestant.getUserData().getDraws());
            }
        }
        competitionRepository.save(competition);
    }

    private Integer getNegativePrestige(ContestantInfo info, Competition competition) {
        List<TaskOption> contestantResults = info.getContestantResults();
        AtomicInteger prestige = new AtomicInteger(0);
        List<Task> tasks = competition.getTasks();
        tasks.forEach(task -> {
            System.out.println(task.getId());
            task.getTaskOption().stream().forEach(taskOption -> {
                System.out.println(taskOption.getCorrect() && contestantResults.stream().anyMatch(tOption -> tOption.equals(taskOption)));
                System.out.println(!taskOption.getCorrect() && contestantResults.stream().noneMatch(tOption -> tOption.equals(taskOption)));
                System.out.println((taskOption.getCorrect() && contestantResults.stream().anyMatch(tOption -> tOption.equals(taskOption)) ||
                    (!taskOption.getCorrect() && contestantResults.stream().noneMatch(tOption -> tOption.equals(taskOption)))));
            });
            if (task.getTaskOption().stream().allMatch(taskOption ->
                (taskOption.getCorrect() && contestantResults.stream().anyMatch(tOption -> tOption.equals(taskOption))) ||
                    (!taskOption.getCorrect() && contestantResults.stream().noneMatch(tOption -> tOption.equals(taskOption))))) {
                prestige.addAndGet(task.getPrestige());
            }
        });
        Integer totalPrestige = tasks.stream().map(Task::getPrestige).reduce(Integer::sum).get();
        return prestige.get() - totalPrestige;
    }


    public ContestantInfo getOpponent(Competition competition) {
        return competition.getContestants().stream().filter(contestant -> !contestant.getUserData().getId().equals(userDataContainer.getId())).findFirst().orElse(null);
    }

    public List<CompetitionInfo> getCompetitionsList() {
        if (competitionsList == null) {
            competitionsList = competitionInfoRepository.findAll();
        }
        return competitionsList;
    }

    public CompetitionInfo getCompetitionInfo(Long categoryId) {
        Optional<CompetitionInfo> competitionInfo = competitionsList.stream()
            .filter(competition -> competition.getCategory().getId().equals(categoryId))
            .findFirst();
        return competitionInfo.orElseGet(() -> competitionInfoRepository.findByCategoryId(categoryId));
    }

    private void wrapTasks(List<Task> tasks) {
        tasks.forEach(task -> {
            task.setMenu("menu" + task.getId());
        });
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setMenuCounter(i + 1);
        }
    }

    private List<Competition> wrapCompetitions(List<Competition> competitions) {
        competitions.forEach(competition -> {
            competition.setImageLink(competitionsList.stream().filter(c -> c.getCategory().getId().equals(competition.getCategory().getId())).findFirst().get().getImageLink());
            competition.setUserData(userDataRepository.getOne(competition.getContestants().stream().filter(con -> !con.getUserData().getId().equals(userDataContainer.getId())).findFirst().get().getUserData().getId()));
            ContestantInfo currentPlayer = competition.getContestants().stream().filter(com -> com.getUserData().getId().equals(userDataContainer.getId())).findFirst().get();
            ContestantInfo opponent = competition.getContestants().stream().filter(com -> !com.getUserData().getId().equals(userDataContainer.getId())).findFirst().get();
            competition.setContestants(Arrays.asList(currentPlayer, opponent));
            if (currentPlayer.getScore() == null || opponent.getScore() == null) return;
            if (currentPlayer.getScore() > opponent.getScore()) {
                competition.setStyle("border-width: 2px; border-color: #2df622; border-radius: 15px;");
            } else if (currentPlayer.getScore() < opponent.getScore()) {
                competition.setStyle("border-width: 2px; border-color: red; border-radius: 15px;");
            } else {
                competition.setStyle("border-width: 2px; border-color: grey; border-radius: 15px;");
            }
        });
        return competitions;
    }

}
