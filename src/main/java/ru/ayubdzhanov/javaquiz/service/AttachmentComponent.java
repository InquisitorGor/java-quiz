package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ayubdzhanov.javaquiz.dao.AttachmentRepository;
import ru.ayubdzhanov.javaquiz.domain.Attachment;

@Component
public class AttachmentComponent {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private FileComponent fileComponent;

    public void deleteAttachment(String id){
        Attachment attachment = attachmentRepository.findById(Long.parseLong(id)).get();
        String[] split = attachment.getPath().split("/");
        fileComponent.deleteFile(split[split.length - 1]);
        attachmentRepository.deleteById(Long.parseLong(id));
    }
}
