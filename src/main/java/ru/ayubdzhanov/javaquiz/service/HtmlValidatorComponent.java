package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Attachment;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Size;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Type;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.exception.HtmlValidationException;
import ru.ayubdzhanov.javaquiz.util.HtmlUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class HtmlValidatorComponent {

    private static final String ATTACHMENT_EXCEPTION = "Problem with attachment. There are two cases that trigger this problem:\r\n " +
        "attach is present, but keyword is absent\r\n attach is absent, but keyword is present";
    private static final String EMBEDDED_KEYWORD_EXCEPTION = "Keyword was embedded to html code";
    public static final String KEYWORD_REMOVAL_EXCEPTION = "You forgot to insert a keyword";
    public static final String FIRST_PICTURE_IS_PRESENT_EXCEPTION = "picture 1 exists. delete before updating";
    public static final String SECOND_PICTURE_IS_PRESENT_EXCEPTION = "picture 2 exists. delete before updating";
    public static final String THIRD_PICTURE_IS_PRESENT_EXCEPTION = "picture 3 exists. delete before updating";

    @Autowired
    private HtmlValidatorAdapter htmlValidatorAdapter;
    @Autowired
    private TheoryRepository theoryRepository;

    public void validateTheoryContent(String content, Boolean firstImageExist, Boolean secondImageExist,
                                      Boolean thirdImageExist, Boolean linkAttachExist, String theoryId) throws HtmlValidationException {
        htmlValidatorAdapter.validateHtml(content);
        if ((firstImageExist && !keywordExist(content, "%картинка 1%") ||
            !firstImageExist && keywordExist(content, "%картинка 1%") && !isAttachPresent("%картинка 1%", theoryId)) ||
            (secondImageExist && !keywordExist(content, "%картинка 2%") ||
                !secondImageExist && keywordExist(content, "%картинка 2%") && !isAttachPresent("%картинка 2%", theoryId)) ||
            (thirdImageExist && !keywordExist(content, "%картинка 3%") ||
                !thirdImageExist && keywordExist(content, "%картинка 3%") && !isAttachPresent("%картинка 3%", theoryId)) ||
            (linkAttachExist && !keywordExist(content, "%видео%") ||
                !linkAttachExist && keywordExist(content, "%видео%"))
        ) {
            throw new HtmlValidationException("validation failed", ATTACHMENT_EXCEPTION);
        }
        checkParsedHtml(content, firstImageExist, secondImageExist, thirdImageExist, linkAttachExist);
        if (theoryId.equals("0")) return;
        Theory theory = theoryRepository.findById(Long.parseLong(theoryId)).orElseThrow(() -> new HtmlValidationException("validation failed", "there is no entity"));
        if (!firstImageExist && !keywordExist(content, "%картинка 1%") && isAttachPresent("%картинка 1%", theoryId) ||
            !secondImageExist && !keywordExist(content, "%картинка 2%") && isAttachPresent("%картинка 2%", theoryId) ||
            !thirdImageExist && !keywordExist(content, "%картинка 3%") && isAttachPresent("%картинка 3%", theoryId) ||
            !linkAttachExist && !keywordExist(content, "%видео%") && theory.getAttachments().stream().anyMatch(attachment -> attachment.getType() == Type.VIDEO)
        ) {
            throw new HtmlValidationException("validation failed", KEYWORD_REMOVAL_EXCEPTION);
        }
        theory.getAttachments().stream()
            .filter(attachment -> attachment.getType() == Type.IMAGE)
            .forEach(attachment -> {
                if (firstImageExist) {
                    Pattern pattern = Pattern.compile(".*(картинка1).*");
                    if (pattern.matcher(attachment.getPath()).matches())
                        throw new HtmlValidationException("validation failed", FIRST_PICTURE_IS_PRESENT_EXCEPTION);
                }
                if (secondImageExist) {
                    Pattern pattern = Pattern.compile(".*(картинка2).*");
                    if (pattern.matcher(attachment.getPath()).matches())
                        throw new HtmlValidationException("validation failed", SECOND_PICTURE_IS_PRESENT_EXCEPTION);
                }
                if (thirdImageExist) {
                    Pattern pattern = Pattern.compile(".*(картинка3).*");
                    if (pattern.matcher(attachment.getPath()).matches())
                        throw new HtmlValidationException("validation failed", THIRD_PICTURE_IS_PRESENT_EXCEPTION);
                }
            });

    }

    private void checkParsedHtml(String content, Boolean firstImageExist, Boolean secondImageExist, Boolean thirdImageExist, Boolean linkAttachExist) {
        StringBuilder parsedHtmlCode = new StringBuilder();
        if (firstImageExist) {
            parsedHtmlCode.append(HtmlUtils.parseImageLinks(content, "1234", "%картинка 1%", Size.SMALL, "picture"));
        }
        if (secondImageExist) {
            parsedHtmlCode.append(HtmlUtils.parseImageLinks(content, "1234", "%картинка 2%", Size.SMALL, "picture"));
        }
        if (thirdImageExist) {
            parsedHtmlCode.append(HtmlUtils.parseImageLinks(content, "1234", "%картинка 3%", Size.SMALL, "picture"));
        }
        if (linkAttachExist) {
            parsedHtmlCode.append(HtmlUtils.parseVideoLink(content, "1234", "%видео%"));
        }
        try {
            if (!parsedHtmlCode.toString().isEmpty()) htmlValidatorAdapter.validateHtml(parsedHtmlCode.toString());
        } catch (HtmlValidationException ex) {
            throw new HtmlValidationException("validation failed", EMBEDDED_KEYWORD_EXCEPTION);
        }
    }

    private boolean keywordExist(String content, String keyword) {
        content = content.replace("\n", "").replace("\r", "");
        Pattern pattern = Pattern.compile(".*(" + keyword + ").*");
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();

    }

    private boolean isAttachPresent(String keyword, String theoryId) {
        if (theoryId.equals("0")) return false;
        Theory theory = theoryRepository.findById(Long.parseLong(theoryId)).orElseThrow(() -> new HtmlValidationException("validation failed", "there is no entity"));
        List<Attachment> collect = theory.getAttachments().stream()
            .filter(attachment -> attachment.getType() == Type.IMAGE)
            .filter(attachment -> {
                if (keyword.equals("%картинка 1%")) {
                    Pattern pattern = Pattern.compile(".*(картинка1).*");
                    if (pattern.matcher(attachment.getPath()).matches()) return true;
                }
                if (keyword.equals("%картинка 2%")) {
                    Pattern pattern = Pattern.compile(".*(картинка2).*");
                    if (pattern.matcher(attachment.getPath()).matches()) return true;
                }
                if (keyword.equals("%картинка 3%")) {
                    Pattern pattern = Pattern.compile(".*(картинка3).*");
                    if (pattern.matcher(attachment.getPath()).matches()) return true;
                }
                return false;
            }).collect(Collectors.toList());
        return !collect.isEmpty();
    }

    public void validateCompetitionInfoContent(String description) {
        htmlValidatorAdapter.validateHtml(description);
    }
}
