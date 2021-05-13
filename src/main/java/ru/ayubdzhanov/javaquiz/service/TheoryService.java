package ru.ayubdzhanov.javaquiz.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ayubdzhanov.javaquiz.controllers.AdminController.UpdatedTheory;
import ru.ayubdzhanov.javaquiz.dao.AttachmentRepository;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Attachment;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Size;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Type;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.util.HtmlUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TheoryService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TheoryRepository theoryRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private FileComponent fileComponent;

    public List<Category> getCategories() {
        List<Category> categories = categoryService.getCategories(Boolean.FALSE);
//        categories.forEach(category -> wrapTheories(category.getTheories()));
        return categories;
    }

    public Category getCategory(String category) {
        return getCategories().stream().filter(cat -> cat.getCategory().equals(category)).findFirst().get();
    }

    public List<Theory> getTheories(String category, String keyword, Integer olderThan) {
        if ((category == null || category.equals("None")) && keyword == null) return theoryRepository.findAll(PageRequest.of(olderThan, 20)).getContent();
        else if (category != null && keyword == null) return theoryRepository.findAllByCategoryCategory(category, PageRequest.of(olderThan, 20));
        else if (category == null || category.equals("None")) return theoryRepository.findAllByTitle(keyword, PageRequest.of(olderThan, 20));
        return theoryRepository.findAllByTitleAndCategory(keyword, category, PageRequest.of(olderThan, olderThan + 20));
    }

    //FIXME Correct attach logic
    // - add matchers for attach
    // - add StringBuilder
    // - add Exception handling
//    public void wrapTheories(List<Theory> theories) {
//        theories.forEach(theory -> {
//            if (theory.getAttachments().isEmpty()) {
//                theory.setParsedDescription(theory.getDescription());
//                return;
//            }
//            AtomicReference<String> parsedDescription = new AtomicReference<>(theory.getDescription());
//            theory.getAttachments().forEach(attachment -> {
//                if (attachment.getType() == Type.IMAGE) {
//                    Pattern firstImagePattern = Pattern.compile(".*(картинка1).*");
//                    Pattern secondImagePattern = Pattern.compile(".*(картинка2).*");
//                    Pattern thirdImagePattern = Pattern.compile(".*(картинка3).*");
//                    if (firstImagePattern.matcher(attachment.getPath()).matches()) {
//                        parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 1%", attachment.getSize(), attachment.getCaption()));
//                    } else if (secondImagePattern.matcher(attachment.getPath()).matches()) {
//                        parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 2%", attachment.getSize(), attachment.getCaption()));
//                    } else if (thirdImagePattern.matcher(attachment.getPath()).matches()) {
//                        parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 3%", attachment.getSize(), attachment.getCaption()));
//                    }
//                } else if (attachment.getType() == Type.VIDEO) {
//                    parsedDescription.set(HtmlUtils.parseVideoLink(parsedDescription.get(), attachment.getPath(), "%видео%"));
//                } else {
//                    try {
//                        throw new Exception("Unknown attachment type");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            theory.setParsedDescription(parsedDescription.get());
//        });
//    }

    public Theory wrapTheory(Theory theory) {
        if (theory.getAttachments().isEmpty()) {
            theory.setParsedDescription(theory.getDescription());
            return theory;
        }
        AtomicReference<String> parsedDescription = new AtomicReference<>(theory.getDescription());
        theory.getAttachments().forEach(attachment -> {
            if (attachment.getType() == Type.IMAGE) {
                Pattern firstImagePattern = Pattern.compile(".*(картинка1).*");
                Pattern secondImagePattern = Pattern.compile(".*(картинка2).*");
                Pattern thirdImagePattern = Pattern.compile(".*(картинка3).*");
                if (firstImagePattern.matcher(attachment.getPath()).matches()) {
                    parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 1%", attachment.getSize(), attachment.getCaption()));
                } else if (secondImagePattern.matcher(attachment.getPath()).matches()) {
                    parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 2%", attachment.getSize(), attachment.getCaption()));
                } else if (thirdImagePattern.matcher(attachment.getPath()).matches()) {
                    parsedDescription.set(HtmlUtils.parseImageLinks(parsedDescription.get(), attachment.getPath(), "%картинка 3%", attachment.getSize(), attachment.getCaption()));
                }
            } else if (attachment.getType() == Type.VIDEO) {
                parsedDescription.set(HtmlUtils.parseVideoLink(parsedDescription.get(), attachment.getPath(), "%видео%"));
            } else {
                try {
                    throw new Exception("Unknown attachment type");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        theory.setParsedDescription(parsedDescription.get());
        return theory;
    }

    public Theory getTheory(Long theoryId) {
        return wrapTheory(theoryRepository.findById(theoryId).orElse(getEmptyTheory(theoryId)));
    }

    public Theory getEmptyTheory(Long theoryId) {
        Theory emptyTheory = new Theory();
        emptyTheory.setId(theoryId);
        emptyTheory.setDescription(Strings.EMPTY);
        emptyTheory.setTitle(Strings.EMPTY);
        emptyTheory.setAttachments(Collections.emptyList());
        emptyTheory.setCategory(getCategories().stream().findFirst().get());
        return emptyTheory;
    }

    public void updateTheory(UpdatedTheory updatedTheory) throws Exception {
        Long parsedToLongTheoryId = Long.parseLong(updatedTheory.getTheoryId());
        Theory theory;
        if (parsedToLongTheoryId == 0) {
            theory = new Theory();
        } else {
            theory = theoryRepository.findById(parsedToLongTheoryId).orElseThrow(() -> new Exception("theory des not exist"));
        }
        saveTheory(updatedTheory, theory);
        theoryRepository.save(theory);
    }

    private void saveTheory(UpdatedTheory updatedTheory, Theory theory) {
        theory.setTitle(updatedTheory.getTitle());
        theory.setCategory(getCategories().stream().filter(cat -> cat.getCategory().equals(updatedTheory.getCategory())).findFirst().get());
        if (!updatedTheory.getFirstImage().isEmpty()) {
            addAttachmentForTheory(theory, updatedTheory.getFirstImage(), "картинка1", Size.valueOf(updatedTheory.getFirstImageSize()), updatedTheory.getFirstImageCaption());
        }
        if (!updatedTheory.getSecondImage().isEmpty()) {
            addAttachmentForTheory(theory, updatedTheory.getSecondImage(), "картинка2", Size.valueOf(updatedTheory.getSecondImageSize()), updatedTheory.getSecondImageCaption());
        }
        if (!updatedTheory.getThirdImage().isEmpty()) {
            addAttachmentForTheory(theory, updatedTheory.getThirdImage(), "картинка3", Size.valueOf(updatedTheory.getThirdImageSize()), updatedTheory.getThirdImageCaption());
        }
        if (!updatedTheory.getVideoLinkAttach().isEmpty()) {
            if (theory.getId() != null) {
                theory.getAttachments().removeAll(theory.getAttachments().stream().peek(attachment -> {
                    if (attachment.getType() == Type.VIDEO) attachment.setTheory(null);
                }).filter(attachment -> attachment.getType() == Type.VIDEO)
                    .collect(Collectors.toList()));
            }
            Attachment attachment = new Attachment();
            attachment.setType(Type.VIDEO);
            attachment.setPath(updatedTheory.getVideoLinkAttach());
            attachment.setTheory(theory);
            attachmentRepository.save(attachment);
            theory.getAttachments().add(attachment);
        }
        theory.setDescription(updatedTheory.getContent());
    }

    private void addAttachmentForTheory(Theory theory, MultipartFile file, String pictureName, Size size, String pictureCaption) {
        Attachment attachment = new Attachment();
        attachment.setType(Type.IMAGE);
        attachment.setPath(fileComponent.uploadFileAndGetLink(file, pictureName));
        attachment.setTheory(theory);
        attachment.setSize(size);
        attachment.setCaption(pictureCaption);
        theory.getAttachments().add(attachment);
    }

    public void deleteTheory(Long theoryId) {
        theoryRepository.deleteById(theoryId);
    }

    public Attachment getVideoLinkAttach(List<Attachment> attachment) {
        return attachment.stream().filter(attach -> attach.getType() == Type.VIDEO).findFirst().orElse(null);
    }
}
