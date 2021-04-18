package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.dao.CompetitionInfoRepository;
import ru.ayubdzhanov.javaquiz.dao.CompetitionRepository;
import ru.ayubdzhanov.javaquiz.dao.TaskRepository;
import ru.ayubdzhanov.javaquiz.dao.UserDataRepository;
import ru.ayubdzhanov.javaquiz.domain.Competition;
import ru.ayubdzhanov.javaquiz.domain.CompetitionInfo;
import ru.ayubdzhanov.javaquiz.domain.ContestantInfo;
import ru.ayubdzhanov.javaquiz.domain.Task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

    private List<CompetitionInfo> competitionsList;

    public Competition getCompetition(Long categoryId) {
        Optional<Competition> competition = competitionRepository.findAllStartedCompetitions(categoryId, userDataContainer.getId()).stream().findFirst();
        if (!competition.isPresent()) {
            Competition newCompetition = new Competition();
            ContestantInfo contestant = new ContestantInfo();
            contestant.setUserData(userDataRepository.getOne(userDataContainer.getId()));
            contestant.setCompetition(newCompetition);
            List<Task> tasks = taskRepository.findAllByCategoryId(categoryId, PageRequest.of(0, 5));
            wrapTasks(tasks);
            newCompetition.setTasks(tasks);
            newCompetition.setStartedAt(LocalDateTime.now());
            newCompetition.getContestants().add(contestant);
            newCompetition.setCategory(categoryRepository.getOne(categoryId));
            competitionRepository.save(newCompetition);
            return newCompetition;
        }
        Competition existedCompetition = competition.get();
        ContestantInfo contestant = new ContestantInfo();
        contestant.setUserData(userDataRepository.getOne(userDataContainer.getId()));
        contestant.setCompetition(existedCompetition);
        existedCompetition.getContestants().add(contestant);
        wrapTasks(existedCompetition.getTasks());
        competitionRepository.save(existedCompetition);
        return existedCompetition;
    }

    public void finishCompetition(MultiValueMap<String, String> allParams) {
        allParams.entrySet().forEach(System.out::println);
        Competition competition = competitionRepository.getOne(Long.parseLong(allParams.get("competitionId").get(0)));
        AtomicInteger score = new AtomicInteger(5);
        ContestantInfo currentContestant = competition.getContestants().stream().filter(contestant -> contestant.getUserData().getId() == Long.parseLong(allParams.get("currentContestant").get(0))).findFirst().get();
        competition.getTasks().forEach(task -> {
            if (!allParams.containsKey(task.getId() + "")) {
                currentContestant.setScore(score.decrementAndGet());
                return;
            }
            AtomicBoolean decrement = new AtomicBoolean(true);
            task.getTaskOption().forEach(taskOption -> {
                currentContestant.getContestantResults().add(taskOption);
                if (decrement.get() && (taskOption.getCorrect() && !allParams.get(task.getId() + "").contains(taskOption.getOption().getId() + "") ||
                    !taskOption.getCorrect() && allParams.get(task.getId() + "").contains(taskOption.getOption().getId() + ""))) {
                    currentContestant.setScore(score.decrementAndGet());
                    decrement.set(false);
                }
            });
        });
        if (currentContestant.getScore() == null) {
            currentContestant.setScore(score.get());
        }
        if (competition.getContestants().size() == 2) {
            competition.setFinishedAt(LocalDateTime.now());
        }
        competitionRepository.save(competition);
    }

    public ContestantInfo getOpponent(Competition competition){
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

}
