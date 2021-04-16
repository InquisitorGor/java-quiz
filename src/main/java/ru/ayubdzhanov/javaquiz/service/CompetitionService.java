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
import ru.ayubdzhanov.javaquiz.domain.UserData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
            List<Task> tasks = taskRepository.findAllByCategoryId(categoryId, PageRequest.of(0, 5));
            wrapTasks(tasks);
            newCompetition.setTasks(tasks);
            newCompetition.setStartedAt(LocalDateTime.now());
            newCompetition.setContestants(Collections.singletonList(contestant));
            newCompetition.setCategory(categoryRepository.getOne(categoryId));
            competitionRepository.save(newCompetition);
            return newCompetition;
        }
        Competition existedCompetition = competition.get();
        ContestantInfo contestant = new ContestantInfo();
        contestant.setUserData(userDataContainer.getUserData());
        existedCompetition.getContestants().add(contestant);
        return existedCompetition;
    }

    public void finishCompetition(MultiValueMap<String, String> allParams) {
        Competition competition = competitionRepository.getOne(Long.parseLong(allParams.get("competitionId").get(0)));
        if (competition.getContestants().size() == 1) {
            List<Task> tasks = competition.getTasks();
            tasks.forEach(task -> {
                if (allParams.containsKey(task.getId() + "")) {
                    task.getTaskOption().forEach(option -> {
                        List<String> userAnswers = allParams.get(task.getId() + "");
                        if (option.getCorrect() && userAnswers.contains(option.getId() + "") ||
                            !option.getCorrect() && !userAnswers.contains(option.getId() + "")) {
                            competition.getContestants().get(0).setScore(competition.getContestants().get(0).getScore() + 1);
                        }
                    });
                }
            });
        }
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
