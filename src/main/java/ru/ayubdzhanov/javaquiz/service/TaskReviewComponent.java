package ru.ayubdzhanov.javaquiz.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ayubdzhanov.javaquiz.dao.TaskReviewRepository;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.TaskReview;

import java.time.LocalDateTime;

@Service
public class TaskReviewComponent {
    @Autowired
    private TaskReviewRepository taskReviewRepository;

    public void reviewTask(TaskReview taskReview, Boolean isIncorrect, String comment, Task task) {
        taskReview.setReviewedAt(LocalDateTime.now());
        if (isIncorrect) {
            taskReview.setIsApproved(Boolean.FALSE);
        } else {
            taskReview.setIsApproved(Boolean.TRUE);
            task.setIsApproved(Boolean.TRUE);
        }
        if (Strings.isNotEmpty(comment)) {
            taskReview.setComment(comment);
        }
        taskReviewRepository.save(taskReview);
    }
}
