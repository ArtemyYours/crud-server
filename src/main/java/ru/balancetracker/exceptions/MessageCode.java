package ru.balancetracker.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MessageCode {
    USER_DOESNT_HAVE_ACCESS_TO_TRANSACTION("balance-tracker.crud.user-doesn't-have-access-to-transaction"),
    USER_DOESNT_HAVE_ACCESS_TO_TRANSACTION_ACCOUNT("balance-tracker.crud.user-doesn't-have-access-to-transaction-account"),

    ;


    private final String key;
    private final HttpStatus httpStatus;

    MessageCode() {
        this.key = name();
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    MessageCode(String key) {
        this.key = key;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
