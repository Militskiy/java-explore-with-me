package ru.practicum.ewm.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleThrowable(final Throwable e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error: " + e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleWebExchangeBindException(final WebExchangeBindException e) {
        List<String> errorList = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        final String errors = errorList.toString().substring(1, errorList.toString().length() - 1);
        log.warn(errors);
        return ResponseEntity.status(e.getStatus()).body(errors);
    }
}
