package ru.ayubdzhanov.javaquiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/theory")
    public String getTheoryAdminPanel(Model model,
                                      @RequestParam(required = false) String category,
                                      @RequestParam(required = false) String keyword) {
        model.addAttribute("categories", adminService.getCategories());
        model.addAttribute("theories", adminService.getTheories(category, keyword));
        return "theoryAdminPage";
    }

    @RequestMapping("/theory/{theoryId}")
    public String getTheoryEditingPage(Model model,
                                       @PathVariable Long theoryId) {
        Theory theory = adminService.getTheory(theoryId);
        model.addAttribute("theory", theory);
        model.addAttribute("categories", adminService.getCategories());
        model.addAttribute("linkAttach", adminService.getLinkAttach(theory.getAttachment()));
        return "theoryEditingPage";
    }

    @RequestMapping("/theory/update")
    public String saveTheory(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam String category,
                             @RequestParam String theoryId,
                             @RequestParam(required = false) MultipartFile firstImage,
                             @RequestParam(required = false) MultipartFile secondImage,
                             @RequestParam(required = false) MultipartFile thirdImage,
                             @RequestParam(required = false) String linkAttach) throws Exception {
        adminService.updateTheory(title, content, category, firstImage, secondImage, thirdImage, linkAttach, theoryId);
        return "redirect:/admin/theory";
    }

    @RequestMapping("/theory/delete/{theoryId}")
    public String deleteTheory(@PathVariable String theoryId) {
        adminService.deleteTheory(theoryId);
        return "redirect:/admin/theory";
    }


    @RequestMapping("/competition")
    public String getCompetitionAdminPanel(Model model) {
        model.addAttribute("categories", adminService.getCategories());
        return "competitionAdminPage";
    }
}
