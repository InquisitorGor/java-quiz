package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileComponent {

    @Value("${file.theory-images.full-path}")
    private String theoryImagesFullPath;

    @Value("${file.theory-images.web-path}")
    private String theoryImagesWebPath;

    public String uploadFileAndGetLink(MultipartFile multipartFile, String pictureName) {
        String newFileName = UUID.randomUUID() + pictureName + multipartFile.getOriginalFilename();
        Path filePath = Paths.get(theoryImagesFullPath + newFileName);
        try {
            Files.write(filePath, multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return theoryImagesWebPath + newFileName;
    }

    public void deleteFile(String path){
        try {
            Files.delete(Paths.get(theoryImagesFullPath + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
