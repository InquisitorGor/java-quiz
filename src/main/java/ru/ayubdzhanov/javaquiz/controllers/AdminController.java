package ru.ayubdzhanov.javaquiz.controllers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.service.AdminService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/theory")
    public String getTheoryAdminPanel(Model model,
                                      @RequestParam(required = false) String category,
                                      @RequestParam(required = false) String keyword) {
        model.addAttribute("categories", adminService.getCategories());
        model.addAttribute("theories", adminService.getTheories(category, keyword, "0"));
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedKeyword", keyword);
        return "theoryAdminPage";
    }

    @RequestMapping(value = "/theory", params = "olderThan")
    @ResponseBody
    public List<Theory> getTheories(@RequestParam(required = false) String category,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam String olderThan) {
        return adminService.getTheories(category, keyword, olderThan);
    }

    @RequestMapping("/theory/{theoryId}")
    public String getTheoryEditingPage(Model model,
                                       @PathVariable Long theoryId) {
        Theory theory = adminService.getTheory(theoryId);
        model.addAttribute("theory", theory);
        model.addAttribute("categories", adminService.getCategories());
        model.addAttribute("videoLinkAttach", adminService.getVideoLinkAttach(theory.getAttachments()));
        return "theoryEditingPage";
    }

    @RequestMapping("/theory/update")
    public String saveTheory(UpdatedTheory updatedTheory) {
        try {
            adminService.updateTheory(updatedTheory);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "redirect:/admin/theory";
    }

    @RequestMapping("/theory/delete/{theoryId}")
    public String deleteTheory(@PathVariable String theoryId) {
        adminService.deleteTheory(theoryId);
        return "redirect:/admin/theory";
    }


    @RequestMapping("/competition")
    public String getCompetitionAdminPanel(Model model,
                                           @RequestParam(required = false) String category,
                                           @RequestParam(required = false) String keyword) {
        model.addAttribute("categories", adminService.getCategories());
        model.addAttribute("approvedTasks", adminService.getTasks(category, keyword, Boolean.TRUE, "0"));
        model.addAttribute("unconfirmedTasks", adminService.getUnconfirmedTasks(category, keyword));
        model.addAttribute("incorrectTasks", adminService.getIncorrectTasks(category, keyword));
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedKeyword", keyword);
        return "competitionAdminPage";
    }

    @RequestMapping(value = "/competition", params = "olderThan")
    @ResponseBody
    public List<Task> getTasks(@RequestParam(required = false) String category,
                               @RequestParam(required = false) String keyword,
                               @RequestParam String olderThan) {
        return adminService.getTasks(category, keyword, Boolean.TRUE, olderThan);
    }

    @RequestMapping("/competition/{taskId}")
    public String getCompetitionEditingPage(Model model,
                                            @PathVariable Long taskId) {
        Task task = adminService.getTask(taskId);
        model.addAttribute("task", task);
        model.addAttribute("comments", adminService.getAuthorComments(task));
        model.addAttribute("categories", adminService.getCategories());
        model.addAttribute("options", adminService.getOptions(task));
        return "competitionEditingPage";
    }

    @RequestMapping("/task/update")
    public String saveTask(@RequestParam Map<String, String> allParams) {
        adminService.saveTask(allParams);
        return "redirect:/admin/competition";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdatedTheory {
        @NotBlank(message = "title is mandatory")
        private String title;
        @NotBlank(message = "content is mandatory")
        private String content;
        @NotBlank(message = "category is mandatory")
        private String category;
        @NotBlank(message = "theoryId is mandatory")
        private String theoryId;
        @NotBlank(message = "firstImageSize is mandatory")
        private String firstImageSize;
        @NotBlank(message = "secondImageSize is mandatory")
        private String secondImageSize;
        @NotBlank(message = "thirdImageSize is mandatory")
        private String thirdImageSize;
        @NotBlank(message = "firstPictureCaption is mandatory")
        private String firstImageCaption;
        @NotBlank(message = "secondPictureCaption is mandatory")
        private String secondImageCaption;
        @NotBlank(message = "thirdPictureCaption is mandatory")
        private String thirdImageCaption;
        private MultipartFile firstImage;
        private MultipartFile secondImage;
        private MultipartFile thirdImage;
        private String videoLinkAttach;
    }
}
