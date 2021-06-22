package ru.balancetracker.model.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.balancetracker.exceptions.MessageCode;

import java.util.Map;

@Data
@AllArgsConstructor
public class UserErrorMessageDTO {
    private Map<String, String> errorParameters;
    private MessageCode messageCode;

    public UserErrorMessage toUserErrorMessage() {
        return new UserErrorMessage(this.getMessageCode().getKey(), this.getErrorParameters());
    }
}
