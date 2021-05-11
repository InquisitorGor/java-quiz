package ru.ayubdzhanov.javaquiz.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.OptionRepository;
import ru.ayubdzhanov.javaquiz.dao.TaskOptionRepository;
import ru.ayubdzhanov.javaquiz.dao.TaskRepository;
import ru.ayubdzhanov.javaquiz.domain.Option;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.TaskOption;
import ru.ayubdzhanov.javaquiz.domain.TaskReview;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TaskOptionRepository taskOptionRepository;

    //TODO Utilise Criteria API rather then conditions
    public List<Task> getTasks(String category, String keyword, Boolean isApproved) {
        if ((category == null || category.equals("None")) && keyword == null) return taskRepository.findAllByIsApproved(isApproved, PageRequest.of(0, 20));
        else if (category != null && keyword == null) return taskRepository.findAllByCategoryCategoryAndIsApproved(category, isApproved, PageRequest.of(0, 20));
        else if (category == null || category.equals("None")) return taskRepository.findAllByQuestionAndIsApproved(keyword, isApproved);
        return taskRepository.findAllByTitleAndCategoryAndIsApproved(keyword, category, isApproved);
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElse(getEmptyTask(taskId));
    }

    private Task getEmptyTask(Long taskId) {
        Task task = new Task();
        task.setId(taskId);
        task.setTaskOption(Collections.emptyList());
        task.setPrestige(0);
        task.setQuestion(Strings.EMPTY);
        task.setCategory(categoryService.getCategories(Boolean.FALSE).stream().findFirst().get());
        return task;
    }

    public List<TaskOptionsWrapper> getOptions(Task task) {
        List<TaskOption> taskOptions = task.getTaskOption();
        String[] titleNames = {"Первый вариант ответа", "Второй вариант ответа", "Третий вариант ответа", "Четвертый вариант ответа", "Пятый вариант ответа"};
        String[] textAreaIds = {"firstOption", "secondOption", "thirdOption", "fourthOption", "fifthOption"};
        String[] checkBoxIds = {"isFirstCorrect", "isSecondCorrect", "isThirdCorrect", "isFourthCorrect", "isFifthCorrect"};
        List<TaskOptionsWrapper> taskOptionsWrapper = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            if (taskOptions.size() > i) {
                taskOptionsWrapper.add(new TaskOptionsWrapper(taskOptions.get(i), titleNames[i], textAreaIds[i], checkBoxIds[i]));
            } else {
                taskOptionsWrapper.add(new TaskOptionsWrapper(null, titleNames[i], textAreaIds[i], checkBoxIds[i]));
            }
        }
        return taskOptionsWrapper;
    }

    public void saveTask(Map<String, String> allParams) {
        Task task;
        if (allParams.get("taskId").equals("0")){
            task = new Task();
        } else {
            task = getTask(Long.parseLong(allParams.get("taskId")));
            taskOptionRepository.deleteAll(task.getTaskOption());
        }
        task.setCategory(categoryService.getCategories(Boolean.TRUE).stream()
            .filter(cat -> cat.getCategory().equals(allParams.get("category"))).findFirst().get());
        task.setQuestion(allParams.get("title"));
        task.setPrestige(Integer.parseInt(allParams.get("prestige")));
        List<TaskOption> taskOptions = new LinkedList<>();
        addOptions(allParams.get("firstOption"), allParams.containsKey("isFirstCorrect"), task, taskOptions);
        addOptions(allParams.get("secondOption"), allParams.containsKey("isSecondCorrect"), task, taskOptions);
        addOptions(allParams.get("thirdOption"), allParams.containsKey("isThirdCorrect"), task, taskOptions);
        addOptions(allParams.get("fourthOption"), allParams.containsKey("isFourthCorrect"), task, taskOptions);
        addOptions(allParams.get("fifthOption"), allParams.containsKey("isFifthCorrect"), task, taskOptions);
        task.setTaskOption(taskOptions);
        task.setIsApproved(Boolean.FALSE);
        TaskReview taskReview = new TaskReview();
        taskReview.setTask(task);
        task.getReviews().add(taskReview);
        taskRepository.save(task);
    }

    private void addOptions(String theOption, Boolean isCorrect, Task task, List<TaskOption> taskOptions) {
        TaskOption taskOption = new TaskOption();
        taskOption.setTask(task);
        if (!theOption.isEmpty()) {
            Optional<Option> firstOption = optionRepository.findByOption(theOption);
            if (firstOption.isPresent()) {
                taskOption.setOption(firstOption.get());
            } else {
                Option option = new Option();
                option.setOption(theOption);
                taskOption.setOption(option);
            }
            if (isCorrect) {
                taskOption.setIsCorrect(Boolean.TRUE);
            } else {
                taskOption.setIsCorrect(Boolean.FALSE);
            }
            taskOptions.add(taskOption);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TaskOptionsWrapper {
        TaskOption taskOption;
        String titleName;
        String textAreaId;
        String checkBoxId;
    }
}
