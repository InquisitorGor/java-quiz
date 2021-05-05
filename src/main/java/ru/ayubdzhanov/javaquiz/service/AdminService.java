package ru.ayubdzhanov.javaquiz.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Attachment;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Type;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Theory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private TheoryRepository theoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileComponent fileComponent;
    @Autowired
    private HtmlValidatorAdapter htmlValidatorAdapter;

    private List<Category> categories = new LinkedList<>();

    public List<Category> getCategories() {
        if (categories.isEmpty()) categories = categoryRepository.findAll();
        return categories;
    }

    //TODO Utilise Criteria API rather then conditions
    public List<Theory> getTheories(String category, String keyword) {
        if ((category == null || category.equals("None")) && keyword == null) return theoryRepository.findAll(PageRequest.of(0, 20)).getContent();
        else if (category != null && keyword == null) return theoryRepository.findAllByCategoryCategory(category);
        else if (category == null || category.equals("None")) return theoryRepository.findAllByTitle(keyword);
        return theoryRepository.findAllByTitleAndCategory(keyword, category);
    }

    public List<Theory> getTheories(String regex) {
        return theoryRepository.findAllByTitle(regex);
    }

    public Theory getTheory(Long theoryId) {
        return theoryRepository.findById(theoryId).orElse(getEmptyTheory(theoryId));
    }

    public Theory getEmptyTheory(Long theoryId){
        Theory emptyTheory = new Theory();
        emptyTheory.setId(theoryId);
        emptyTheory.setDescription(Strings.EMPTY);
        emptyTheory.setTitle(Strings.EMPTY);
        emptyTheory.setAttachment(Collections.emptyList());
        emptyTheory.setCategory(getCategories().stream().findFirst().get());
        return emptyTheory;
    }

    public void updateTheory(String title, String content, String category,
                             MultipartFile firstImage, MultipartFile secondImage,
                             MultipartFile thirdImage, String linkAttach, String id) throws Exception {
        List<MultipartFile> multipartFiles = verifyContent(Arrays.asList(firstImage, secondImage, thirdImage));
        Long theoryId = Long.parseLong(id);
        Theory theory;
        if (theoryId == 0) {
            theory = new Theory();
        } else {
            theory = theoryRepository.findById(theoryId).orElseThrow(() -> new Exception("theory des not exist"));
        }
        saveTheory(title, content, category, multipartFiles, linkAttach, theory);
        theoryRepository.save(theory);
    }
    //TODO throw exception if pattern didnt match
    private List<MultipartFile> verifyContent(List<MultipartFile> multipartFiles) {
        Pattern pattern = Pattern.compile(".*(\\.jpg|\\.png)");
        return multipartFiles.stream().filter(Objects::nonNull)
            .filter(file -> pattern.matcher(Objects.requireNonNull(file.getOriginalFilename()))
                .matches()).collect(Collectors.toList());
    }

    private void saveTheory(String title, String content, String category, List<MultipartFile> multipartFiles, String linkAttach, Theory theory) throws IOException {
        theory.setTitle(title);
        theory.setDescription(content);
        theory.setCategory(getCategories().stream().filter(cat -> cat.getCategory().equals(category)).findFirst().get());
        if (!multipartFiles.isEmpty()) {
            multipartFiles.forEach(file -> {
                Attachment attachment = new Attachment();
                attachment.setType(Type.PICTURE);
                attachment.setPath(fileComponent.uploadFileAndGetLink(file));
                attachment.setTheory(theory);
                theory.getAttachment().add(attachment);
            });
        }
        if (!linkAttach.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setType(Type.VIDEO);
            attachment.setPath(linkAttach);
            attachment.setTheory(theory);
            theory.getAttachment().add(attachment);
        }
    }

    public void deleteTheory(String theoryId) {
        theoryRepository.deleteById(Long.parseLong(theoryId));
    }

    public Attachment getLinkAttach(List<Attachment> attachment) {
        return attachment.stream().filter(attach -> attach.getType() == Type.VIDEO).findFirst().orElse(null);
    }
}
