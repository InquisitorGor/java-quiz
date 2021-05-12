package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.TaskReview;
import ru.ayubdzhanov.javaquiz.service.TaskService.TaskOptionsWrapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorComponent {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskReviewComponent taskReviewComponent;

    public List<Category> getCategories() {
        return categoryService.getCategories(Boolean.TRUE);
    }

    public List<Task> getUnconfirmedTasks(String category, String keyword) {
        List<Task> unconfirmedTasks = taskService.getTasks(category, keyword, Boolean.FALSE, 0);
        return unconfirmedTasks.stream()
            .filter(task -> task.getReviews().stream()
                .anyMatch(review -> review.getReviewedAt() == null))
            .collect(Collectors.toList());
    }

    public Task getTask(String taskId) {
        return taskService.getTask(Long.parseLong(taskId));
    }

    public List<TaskOptionsWrapper> getOptions(Task unconfirmedTask) {
        return taskService.getOptions(unconfirmedTask);
    }

    public void reviewTask(String taskId, Boolean isIncorrect, String comment) {
        Task task = getTask(taskId);
        TaskReview lastReview = task.getReviews().stream()
            .filter(taskReview -> taskReview.getReviewedAt() == null).findFirst().get();
        taskReviewComponent.reviewTask(lastReview, isIncorrect, comment, task);
    }
}
