package ru.ayubdzhanov.javaquiz.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HtmlValidationException extends RuntimeException{

    private String errors;

    public HtmlValidationException(String message, String errors) {
        super(message);
        this.errors = errors;
    }
    
}
