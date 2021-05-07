package ru.ayubdzhanov.javaquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ayubdzhanov.javaquiz.dao.TheoryRepository;
import ru.ayubdzhanov.javaquiz.domain.Attachment.Type;
import ru.ayubdzhanov.javaquiz.domain.Theory;
import ru.ayubdzhanov.javaquiz.exception.HtmlValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HtmlValidatorComponent {
    @Autowired
    private HtmlValidatorAdapter htmlValidatorAdapter;
    @Autowired
    private TheoryRepository theoryRepository;

    public void checkHtml(String content, Boolean firstImageExist, Boolean secondImageExist,
                          Boolean thirdImageExist, Boolean linkAttachExist, String theoryId) throws HtmlValidationException {
        htmlValidatorAdapter.validateHtml(content);
        if ((firstImageExist && !keywordExist(content, "%картинка 1%") ||
            !firstImageExist && keywordExist(content, "%картинка 1%")) ||
            (secondImageExist && !keywordExist(content, "%картинка 2%") ||
                !secondImageExist && keywordExist(content, "%картинка 2%")) ||
            (thirdImageExist && !keywordExist(content, "%картинка 3%") ||
                !thirdImageExist && keywordExist(content, "%картинка 3%")) ||
            (linkAttachExist && !keywordExist(content, "%видео%") ||
                !linkAttachExist && keywordExist(content, "%видео%"))
        ) {
            throw new HtmlValidationException("validation failed", "Problem with attachment. There are two cases that trigger this problem:\r\n " +
                "attach is present, but keyword is absent\r\n attach is absent, but keyword is present");
        }
        if (theoryId.equals("0")) return;
        Theory theory = theoryRepository.findById(Long.parseLong(theoryId)).orElseThrow(() -> new HtmlValidationException("validation failed", "there is no entity"));
        theory.getAttachments().stream()
            .filter(attachment -> attachment.getType() == Type.PICTURE)
            .forEach(attachment -> {
                if (firstImageExist) {
                    Pattern pattern = Pattern.compile(".*(картинка1).*");
                    if (pattern.matcher(attachment.getPath()).matches())
                        throw new HtmlValidationException("validation failed", "picture 1 exists. delete before updating");
                }
                if (secondImageExist) {
                    Pattern pattern = Pattern.compile(".*(картинка2).*");
                    if (pattern.matcher(attachment.getPath()).matches())
                        throw new HtmlValidationException("validation failed", "picture 2 exists. delete before updating");
                }
                if (thirdImageExist) {
                    Pattern pattern = Pattern.compile(".*(картинка3).*");
                    if (pattern.matcher(attachment.getPath()).matches())
                        throw new HtmlValidationException("validation failed", "picture 3 exists. delete before updating");
                }
            });
    }

    private boolean keywordExist(String content, String keyword) {
        Pattern pattern = Pattern.compile(".*(" + keyword + ").*");
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

}
