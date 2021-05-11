package ru.ayubdzhanov.javaquiz.util;

import ru.ayubdzhanov.javaquiz.domain.Attachment.Size;

public class HtmlUtils {

    private static final String SMALL_IMAGE_SIZE = "style=\"width:550px;height:350px;margin-left: auto;margin-right: auto; display: block;\"";
    private static final String AVERAGE_IMAGE_SIZE = "style=\"width:1000px;height:700px;margin-left: auto;margin-right: auto; display: block;\"";
    private static final String BIG_IMAGE_SIZE = "style=\"width:1400px;height:1000px;margin-left: auto;margin-right: auto; display: block;\"";

    public static String wrapHtmlAttributes(String html) {
        String before = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "\t<meta charset=\"UTF-8\">\n" +
            "\t<title>Title</title>\n" +
            "</head>\n" +
            "<body> ";
        String after = "</body>\n" +
            "</html>";
        return before + html + after;
    }

    public static String parseImageLinks(String content, String path, String toReplace, Size size, String caption) {
        String imageAttributeStyle;
        if (size == Size.SMALL) imageAttributeStyle = SMALL_IMAGE_SIZE;
        else if (size == Size.AVERAGE) imageAttributeStyle = AVERAGE_IMAGE_SIZE;
        else imageAttributeStyle = BIG_IMAGE_SIZE;
        return content.replaceFirst(toReplace, "<br><figure class=\"figure\"><img " + imageAttributeStyle + " src=\"" + path + "\" class=\"img-rounded\" alt=\"Cinque Terre\"> <figcaption class=\"figure-caption text-center\">"+ caption +"</figcaption></figure>");
    }

    public static String parseVideoLink(String content, String url, String toReplace) {
        return content.replaceFirst(toReplace, "<br><div class=\"embed-responsive embed-responsive-16by9\">" +
            "<iframe class=\"embed-responsive-item\" src=\"" + url + "\"></iframe>" +
            "</div>");
    }
}
