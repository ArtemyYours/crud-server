package ru.balancetracker.model.exception;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import ru.balancetracker.exceptions.MessageCode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class BTRestException extends RuntimeException {
    private final HttpStatus status;
    private UserErrorMessage userErrorMessage;
    private List<UserErrorMessage> userErrorMessageList;

    public BTRestException(MessageCode messageCode, @NonNull String message, Map<String, String> errorParameters) {
        super(message);
        this.status = messageCode.getHttpStatus();
        this.userErrorMessage = new UserErrorMessage(messageCode.getKey(), errorParameters);
    }

    public BTRestException(@NonNull String message, @NonNull List<UserErrorMessageDTO> userErrorMessageDTOList) {
        super(message);

        Set<HttpStatus> httpStatusSet = userErrorMessageDTOList.stream()
                .map(userErrorMessageDTO -> userErrorMessageDTO.getMessageCode().getHttpStatus())
                .collect(Collectors.toSet());

        if (httpStatusSet.size() == 1) {
            this.status = httpStatusSet.stream().findFirst().get();
        } else {
            this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        this.userErrorMessageList = userErrorMessageDTOList.stream()
                .map(UserErrorMessageDTO::toUserErrorMessage)
                .collect(Collectors.toList());
    }

    public BTExceptionDTO toDto() {
        if (userErrorMessageList == null) {
            return new BTExceptionDTO(getMessage(), userErrorMessage);
        } else {
            return new BTExceptionDTO(getMessage(), userErrorMessageList);
        }
    }


}
