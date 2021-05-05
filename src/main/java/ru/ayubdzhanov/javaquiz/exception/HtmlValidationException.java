package ru.ayubdzhanov.javaquiz.exception;

import lombok.Getter;
import lombok.Setter;
import ru.ayubdzhanov.javaquiz.service.HtmlValidatorAdapter.ValidatorResponse;

@Getter
@Setter
public class HtmlValidationException extends RuntimeException{

    private ValidatorResponse validatorResponse;

    public HtmlValidationException(String message, ValidatorResponse validatorResponse) {
        super(message);
        this.validatorResponse = validatorResponse;
    }
    
}
