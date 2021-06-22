package ru.balancetracker.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BTExceptionDTO {
    private UserErrorMessage userErrorMessage;
    private List<UserErrorMessage> userErrorMessageList;
    private String message;


    public BTExceptionDTO(String message, UserErrorMessage userErrorMessage) {
        this.userErrorMessage = userErrorMessage;
        this.message = message;
    }

    public BTExceptionDTO(String message, @NonNull List<UserErrorMessage> userErrorMessageList) {
        this.userErrorMessageList = userErrorMessageList;
        this.message = message;
    }

    public BTExceptionDTO(String message) {
        this.message = message;
    }
}
