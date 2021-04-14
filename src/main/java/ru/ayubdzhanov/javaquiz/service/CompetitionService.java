package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.CompetitionInfoRepository;
import ru.ayubdzhanov.javaquiz.dao.CompetitionRepository;
import ru.ayubdzhanov.javaquiz.dao.TaskRepository;
import ru.ayubdzhanov.javaquiz.dao.UserDataRepository;
import ru.ayubdzhanov.javaquiz.domain.Competition;
import ru.ayubdzhanov.javaquiz.domain.CompetitionInfo;
import ru.ayubdzhanov.javaquiz.domain.ContestantInfo;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.UserData;

import java.util.Collection;
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
    private List<CompetitionInfo> competitionsList;

    public UserData findOpponent(Long categoryId){
        List<Competition> competitions = competitionRepository.findAllStartedCompetitions(categoryId, userDataContainer.getId());
        if (competitions.isEmpty()) {
            return null;
        }
        Competition competition = competitions.get(0);
        ContestantInfo opponent = competition.getContestants().get(0);
        return userDataRepository.getOne(opponent);
        return null;
    }

    public List<CompetitionInfo> getCompetitionsList(){
        if (competitionsList == null) {
            competitionsList = competitionInfoRepository.findAll();
        }
        return competitionsList;
    }

    public CompetitionInfo getCompetitionInfo(Long categoryId){
        Optional<CompetitionInfo> competitionInfo = competitionsList.stream()
            .filter(competition -> competition.getCategory().getId().equals(categoryId))
            .findFirst();
        return competitionInfo.orElseGet(() -> competitionInfoRepository.findByCategoryId(categoryId));
    }
    public List<Task> getTasks(Long categoryId){
        List<Task> tasks = taskRepository.findAllByCategoryId(categoryId);
        wrapTasks(tasks);
        return tasks;
    }

    private void wrapTasks(List<Task> tasks){
        tasks.forEach(task -> {
            task.setMenu("menu" + task.getId());
        });
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setMenuCounter(i + 1);
        }
    }


}
