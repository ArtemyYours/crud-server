package ru.balancetracker.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MessageCode {
    USER_DOESNT_HAVE_ACCESS_TO_TRANSACTION("balance-tracker.crud.user-doesn't-have-access-to-transaction"),
    USER_DOESNT_HAVE_ACCESS_TO_TRANSACTION_ACCOUNT("balance-tracker.crud.user-doesn't-have-access-to-transaction-account"),
    THIS_TRANSACTION_ACCOUNT_DOES_NOT_EXIST("balance-tracker.crud.transaction-account-does-not-exist"),
    INCOME_CANNOT_BE_DESTINATION("balance-tracker.crud.transaction-account-type-income-cannot-be-used-as-destination"),
    OUTCOME_CANNOT_BE_SOURCE("balance-tracker.crud.transaction-account-type-outcome-cannot-be-used-as-source"),

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
