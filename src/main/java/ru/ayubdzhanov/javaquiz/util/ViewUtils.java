package ru.ayubdzhanov.javaquiz.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.ayubdzhanov.javaquiz.domain.UserData;

@Builder
@Getter
@Setter
public class ViewUtils {
    private String menu;
    private Integer menuCounter;
    private String style;
    private UserData userData;
    private String imageLink;
    private String imageSize;
}
