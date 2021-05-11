package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.service.AuthorComponent;

@Controller
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorComponent authorComponent;

    @RequestMapping("/tasks")
    public String getAuthorPage(Model model,
                                @RequestParam(required = false) String category,
                                @RequestParam(required = false) String keyword){
        model.addAttribute("categories", authorComponent.getCategories());
        model.addAttribute("tasks", authorComponent.getUnconfirmedTasks(category, keyword));
        return "authorPage";
    }
    @RequestMapping("/task/{taskId}")
    public String getUnconfirmedTask(Model model,
                                     @PathVariable String taskId){
        Task unconfirmedTask = authorComponent.getTask(taskId);
        model.addAttribute("task", unconfirmedTask);
        model.addAttribute("categories", authorComponent.getCategories());
        model.addAttribute("options", authorComponent.getOptions(unconfirmedTask));
        model.addAttribute("is_author", Boolean.TRUE);
        return "competitionEditingPage";
    }

    @RequestMapping("/task/review")
    public String approveTask(@RequestParam(required = false) String comment,
                              @RequestParam(required = false, defaultValue = "false") Boolean isIncorrect,
                              @RequestParam String taskId){
        authorComponent.reviewTask(taskId, isIncorrect, comment);
        return "redirect:/author/tasks";
    }
}
