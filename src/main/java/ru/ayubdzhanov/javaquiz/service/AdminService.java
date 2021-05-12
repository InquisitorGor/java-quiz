package ru.ayubdzhanov.javaquiz.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ayubdzhanov.javaquiz.controllers.AdminController.UpdatedTheory;
import ru.ayubdzhanov.javaquiz.domain.Attachment;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.TaskReview;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.service.TaskService.TaskOptionsWrapper;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private TheoryService theoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private CategoryService categoryService;

    //TODO caching categories

    public List<Category> getCategories() {
        return categoryService.getCategories(Boolean.TRUE);
    }

    public List<Theory> getTheories(String category, String keyword, String olderThan) {
        return theoryService.getTheories(category, keyword, Integer.parseInt(olderThan));
    }

    public Theory getTheory(Long theoryId) {
        return theoryService.getTheory(theoryId);
    }

    public void updateTheory(UpdatedTheory updatedTheory) throws Exception {
        theoryService.updateTheory(updatedTheory);
    }

    public void deleteTheory(String theoryId) {
        theoryService.deleteTheory(Long.parseLong(theoryId));
    }

    public Attachment getVideoLinkAttach(List<Attachment> attachment) {
        return theoryService.getVideoLinkAttach(attachment);
    }

    public List<Task> getTasks(String category, String keyword, Boolean isApproved, String olderThan) {
        return taskService.getTasks(category, keyword, isApproved, Integer.parseInt(olderThan));
    }

    public List<Task> getUnconfirmedTasks(String category, String keyword) {
        List<Task> unconfirmedTasks = taskService.getTasks(category, keyword, Boolean.FALSE, 0);
        return unconfirmedTasks.stream()
            .filter(task -> task.getReviews().stream().anyMatch(taskReview -> taskReview.getIsApproved() == null))
            .collect(Collectors.toList());
    }

    public List<Task> getIncorrectTasks(String category, String keyword) {
        List<Task> unconfirmedTasks = taskService.getTasks(category, keyword, Boolean.FALSE, 0);
        return unconfirmedTasks.stream()
            .filter(task -> task.getReviews().stream().allMatch(taskReview -> taskReview.getIsApproved() != null))
            .collect(Collectors.toList());
    }

    public Task getTask(Long taskId) {
        return taskService.getTask(taskId);
    }

    public List<TaskOptionsWrapper> getOptions(Task task) {
        return taskService.getOptions(task);
    }

    public void saveTask(Map<String, String> allParams) {
        taskService.saveTask(allParams);
    }

    public String getAuthorComments(Task task) {
        String comments = task.getReviews().stream()
            .filter(review -> review.getIsApproved() != null)
            .sorted(Comparator.comparing(TaskReview::getReviewedAt).reversed())
            .map(review -> "reviewed at:" + review.getReviewedAt() + " comment: " + review.getComment())
            .collect(Collectors.joining("\r\n"));
        return comments.isEmpty() ? Strings.EMPTY: comments;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TaskWrapper {
        private Task task;
        private TaskReview lastReview;
    }
}
