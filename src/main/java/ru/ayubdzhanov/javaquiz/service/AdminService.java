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
import ru.ayubdzhanov.javaquiz.exception.HtmlValidationException;
import ru.ayubdzhanov.javaquiz.util.HtmlUtils;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
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

    public Theory getEmptyTheory(Long theoryId) {
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
        Long theoryId = Long.parseLong(id);
        Theory theory;
        if (theoryId == 0) {
            theory = new Theory();
        } else {
            theory = theoryRepository.findById(theoryId).orElseThrow(() -> new Exception("theory des not exist"));
        }
        saveTheory(title, content, category, firstImage, secondImage, thirdImage, linkAttach, theory);
        theoryRepository.save(theory);
    }

    private void saveTheory(String title, String content, String category, MultipartFile firstImage, MultipartFile secondImage,
                            MultipartFile thirdImage, String linkAttach, Theory theory) throws IOException {
        theory.setTitle(title);
        theory.setCategory(getCategories().stream().filter(cat -> cat.getCategory().equals(category)).findFirst().get());
        if (!firstImage.isEmpty()) {
            Attachment attachment = addAttachment(theory, firstImage, "картинка1");
            content = HtmlUtils.parseLinks(content, attachment.getPath(), "%картинка 1%");
        }
        if (!secondImage.isEmpty()) {
            Attachment attachment = addAttachment(theory, secondImage, "картинка2");
            content = HtmlUtils.parseLinks(content, attachment.getPath(), "%картинка 2%");
        }
        if (!thirdImage.isEmpty()) {
            Attachment attachment = addAttachment(theory, thirdImage, "картинка3");
            content = HtmlUtils.parseLinks(content, attachment.getPath(), "%картинка 3%");
        }
        if (!linkAttach.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setType(Type.VIDEO);
            attachment.setPath(linkAttach);
            attachment.setTheory(theory);
            theory.getAttachment().add(attachment);
        }
        theory.setDescription(content);
    }

    private Attachment addAttachment(Theory theory, MultipartFile file, String pictureName) {
        Attachment attachment = new Attachment();
        attachment.setType(Type.PICTURE);
        attachment.setPath(fileComponent.uploadFileAndGetLink(file, pictureName));
        attachment.setTheory(theory);
        theory.getAttachment().add(attachment);
        return attachment;
    }

    public void deleteTheory(String theoryId) {
        theoryRepository.deleteById(Long.parseLong(theoryId));
    }

    public Attachment getLinkAttach(List<Attachment> attachment) {
        return attachment.stream().filter(attach -> attach.getType() == Type.VIDEO).findFirst().orElse(null);
    }
}
