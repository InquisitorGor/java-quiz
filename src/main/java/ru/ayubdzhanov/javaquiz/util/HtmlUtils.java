package ru.ayubdzhanov.javaquiz.util;

public class HtmlUtils {

    public static String processHtml(String html){
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

    public static String parseLinks(String content, String path, String toReplace){
        return content.replaceFirst(toReplace, "<br><img align=\"center\" src=\"" + path + "\" class=\"img-rounded\" alt=\"Cinque Terre\">");
    }
}
