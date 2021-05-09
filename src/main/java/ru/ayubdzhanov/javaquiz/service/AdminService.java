package ru.ayubdzhanov.javaquiz.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ayubdzhanov.javaquiz.dao.AttachmentRepository;
import ru.ayubdzhanov.javaquiz.dao.CategoryRepository;
import ru.ayubdzhanov.javaquiz.dao.OptionRepository;
import ru.ayubdzhanov.javaquiz.dao.TaskOptionRepository;
import ru.ayubdzhanov.javaquiz.dao.TaskRepository;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Attachment;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Size;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Type;
import ru.ayubdzhanov.javaquiz.domain.Category;
import ru.ayubdzhanov.javaquiz.domain.Option;
import ru.ayubdzhanov.javaquiz.domain.Task;
import ru.ayubdzhanov.javaquiz.domain.TaskOption;
import ru.ayubdzhanov.javaquiz.domain.Theory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private TheoryRepository theoryRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskOptionRepository taskOptionRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private FileComponent fileComponent;

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
        emptyTheory.setAttachments(Collections.emptyList());
        emptyTheory.setCategory(getCategories().stream().findFirst().get());
        return emptyTheory;
    }

    public void updateTheory(String title, String content, String category, String firstPictureCaption, String secondPictureCaption, String thirdPictureCaption,
                             String firstImageSize, String secondImageSize, String thirdImageSize, MultipartFile firstImage, MultipartFile secondImage,
                             MultipartFile thirdImage, String linkAttach, String id) throws Exception {
        Long theoryId = Long.parseLong(id);
        Theory theory;
        if (theoryId == 0) {
            theory = new Theory();
        } else {
            theory = theoryRepository.findById(theoryId).orElseThrow(() -> new Exception("theory des not exist"));
        }
        saveTheory(title, content, category,firstPictureCaption, secondPictureCaption, thirdPictureCaption,  firstImageSize, secondImageSize, thirdImageSize, firstImage, secondImage, thirdImage, linkAttach, theory);
        theoryRepository.save(theory);
    }

    private void saveTheory(String title, String content, String category, String firstPictureCaption, String secondPictureCaption, String thirdPictureCaption, String firstImageSize, String secondImageSize, String thirdImageSize, MultipartFile firstImage, MultipartFile secondImage,
                            MultipartFile thirdImage, String linkAttach, Theory theory) {
        theory.setTitle(title);
        theory.setCategory(getCategories().stream().filter(cat -> cat.getCategory().equals(category)).findFirst().get());
        if (!firstImage.isEmpty()) {
            addAttachment(theory, firstImage, "картинка1", Size.valueOf(firstImageSize), firstPictureCaption);
        }
        if (!secondImage.isEmpty()) {
            addAttachment(theory, secondImage, "картинка2", Size.valueOf(secondImageSize), secondPictureCaption);
        }
        if (!thirdImage.isEmpty()) {
            addAttachment(theory, thirdImage, "картинка3", Size.valueOf(thirdImageSize), thirdPictureCaption);
        }
        if (!linkAttach.isEmpty()) {
            if (theory.getId() != null) {
                theory.getAttachments().removeAll(theory.getAttachments().stream().peek(attachment -> {
                    if (attachment.getType() == Type.VIDEO) attachment.setTheory(null);
                }).filter(attachment -> attachment.getType() == Type.VIDEO)
                    .collect(Collectors.toList()));
            }
            Attachment attachment = new Attachment();
            attachment.setType(Type.VIDEO);
            attachment.setPath(linkAttach);
            attachment.setTheory(theory);
            attachmentRepository.save(attachment);
            theory.getAttachments().add(attachment);
        }
        theory.setDescription(content);
    }

    private void addAttachment(Theory theory, MultipartFile file, String pictureName, Size size, String pictureCaption) {
        Attachment attachment = new Attachment();
        attachment.setType(Type.IMAGE);
        attachment.setPath(fileComponent.uploadFileAndGetLink(file, pictureName));
        attachment.setTheory(theory);
        attachment.setSize(size);
        attachment.setCaption(pictureCaption);
        theory.getAttachments().add(attachment);
    }

    public void deleteTheory(String theoryId) {
        theoryRepository.deleteById(Long.parseLong(theoryId));
    }

    public Attachment getLinkAttach(List<Attachment> attachment) {
        return attachment.stream().filter(attach -> attach.getType() == Type.VIDEO).findFirst().orElse(null);
    }

    //TODO Utilise Criteria API rather then conditions
    public List<Task> getTasks(String category, String keyword) {
        if ((category == null || category.equals("None")) && keyword == null) return taskRepository.findAll(PageRequest.of(0, 20)).getContent();
        else if (category != null && keyword == null) return taskRepository.findAllByCategoryCategory(category);
        else if (category == null || category.equals("None")) return taskRepository.findAllByQuestion(keyword);
        return taskRepository.findAllByTitleAndCategory(keyword, category);
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElse(getEmptyTask(taskId));
    }

    private Task getEmptyTask(Long taskId) {
        Task task = new Task();
        task.setId(taskId);
        task.setTaskOption(Collections.emptyList());
        task.setPrestige(0);
        task.setQuestion(Strings.EMPTY);
        task.setCategory(getCategories().stream().findFirst().get());
        return task;
    }

    public List<TaskOptionsWrapper> getOptions(Task task) {
        List<TaskOption> taskOptions = task.getTaskOption();
        String[] titleNames = {"Первый вариант ответа", "Второй вариант ответа", "Третий вариант ответа", "Четвертый вариант ответа", "Пятый вариант ответа"};
        String[] textAreaIds = {"firstOption", "secondOption", "thirdOption", "fourthOption", "fifthOption"};
        String[] checkBoxIds = {"isFirstCorrect", "isSecondCorrect", "isThirdCorrect", "isFourthCorrect", "isFifthCorrect"};
        List<TaskOptionsWrapper> taskOptionsWrapper = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            if (taskOptions.size() > i) {
                taskOptionsWrapper.add(new TaskOptionsWrapper(taskOptions.get(i), titleNames[i], textAreaIds[i], checkBoxIds[i]));
            } else {
                taskOptionsWrapper.add(new TaskOptionsWrapper(null, titleNames[i], textAreaIds[i], checkBoxIds[i]));
            }
        }
        return taskOptionsWrapper;
    }

    public void saveTask(Map<String, String> allParams) {
        Task task;
        if (allParams.get("taskId").equals("0")) {
            task = new Task();
        } else {
            task = taskRepository.findById(Long.parseLong(allParams.get("taskId"))).get();
            taskOptionRepository.deleteAll(task.getTaskOption());
        }
        task.setCategory(getCategories().stream().filter(cat -> cat.getCategory().equals(allParams.get("category"))).findFirst().get());
        task.setQuestion(allParams.get("title"));
        List<TaskOption> taskOptions = new LinkedList<>();
        addOptions(allParams.get("firstOption"), allParams.containsKey("isFirstCorrect"), task, taskOptions);
        addOptions(allParams.get("secondOption"), allParams.containsKey("isSecondCorrect"), task, taskOptions);
        addOptions(allParams.get("thirdOption"), allParams.containsKey("isThirdCorrect"), task, taskOptions);
        addOptions(allParams.get("fourthOption"), allParams.containsKey("isFourthCorrect"), task, taskOptions);
        addOptions(allParams.get("fifthOption"), allParams.containsKey("isFifthCorrect"), task, taskOptions);
        task.setTaskOption(taskOptions);
        taskRepository.save(task);
    }

    private void addOptions(String theOption, Boolean isCorrect, Task task, List<TaskOption> taskOptions) {
        TaskOption taskOption = new TaskOption();
        taskOption.setTask(task);
        if (!theOption.isEmpty()) {
            Optional<Option> firstOption = optionRepository.findByOption(theOption);
            if (firstOption.isPresent()) {
                taskOption.setOption(firstOption.get());
            } else {
                Option option = new Option();
                option.setOption(theOption);
                taskOption.setOption(option);
            }
            if (isCorrect) {
                taskOption.setIsCorrect(Boolean.TRUE);
            } else {
                taskOption.setIsCorrect(Boolean.FALSE);
            }
            taskOptions.add(taskOption);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TaskOptionsWrapper {
        TaskOption taskOption;
        String titleName;
        String textAreaId;
        String checkBoxId;
    }
}