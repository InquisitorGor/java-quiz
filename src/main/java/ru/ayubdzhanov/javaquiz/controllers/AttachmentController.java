package ru.ayubdzhanov.javaquiz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ayubdzhanov.javaquiz.service.AttachmentComponent;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentComponent attachmentComponent;

    @RequestMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttachment(@PathVariable String id){
        attachmentComponent.deleteAttachment(id);
        return ResponseEntity.ok("ok");
    }

}
