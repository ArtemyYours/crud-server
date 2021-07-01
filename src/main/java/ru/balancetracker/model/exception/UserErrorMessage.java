package ru.balancetracker.model.exception;

import lombok.*;

import java.util.Map;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserErrorMessage {

    @NonNull
    private final String errorMessage;
    private Map<String, String> errorParameters;
}
