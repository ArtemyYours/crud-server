package ru.balancetracker.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.balancetracker.model.exception.BTExceptionDTO;
import ru.balancetracker.model.exception.BTRestException;

@RestControllerAdvice

@Slf4j
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(BTRestException.class)
    public ResponseEntity<BTExceptionDTO> handle(BTRestException btRestException) {
        log.error(btRestException.getMessage(), btRestException);
        HttpStatus responseStatus = btRestException.getStatus();
        BTExceptionDTO exceptionDto = btRestException.toDto();
        return new ResponseEntity<>(exceptionDto, responseStatus);
    }

    @ExceptionHandler(Throwable.class)
    @Order
    public ResponseEntity<BTExceptionDTO> handle(Exception ex) {
        log.error(ex.getMessage(), ex);
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        BTExceptionDTO exceptionDto = new BTExceptionDTO(ex.getMessage());
        return new ResponseEntity<>(exceptionDto, responseStatus);
    }

}
