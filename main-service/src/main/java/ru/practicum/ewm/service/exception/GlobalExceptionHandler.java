package ru.practicum.ewm.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiError> handleNotFoundException(final NotFoundException e) {
        log.warn(e.getMessage());
        final var status = HttpStatus.NOT_FOUND;
        final var apiError = ApiError.builder()
                .status(status)
                .message(e.getMessage())
                .reason("The required object was not found.")
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleConflictException(final ConflictException e) {
        log.warn(e.getMessage());
        final var status = HttpStatus.CONFLICT;
        final var apiError = ApiError.builder()
                .status(status)
                .message(e.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleThrowable(final Throwable e) {
        log.error(ExceptionUtils.getStackTrace(e));
        final var status = HttpStatus.INTERNAL_SERVER_ERROR;
        List<String> stackTrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
        final var apiError = ApiError.builder()
                .errors(stackTrace)
                .status(status)
                .message(e.getMessage())
                .reason("Unexpected error")
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn(e.getMessage());
        final var status = HttpStatus.CONFLICT;
        final var apiError = ApiError.builder()
                .status(status)
                .message(e.getMessage())
                .reason("Integrity constraint has been violated.")
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn(e.getMessage());
        final var apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .reason("Constraint violation")
                .build();
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final var field = e.getBindingResult().getFieldErrors().stream()
                .map(err -> "Field: " + err.getField() + ". Error: " + err.getDefaultMessage() + ". Value: " +
                        err.getRejectedValue())
                .collect(Collectors.toList());
        final String errors = field.toString().substring(1, field.toString().length() - 1);
        log.warn(errors);
        final var apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(errors)
                .reason("Incorrectly made request.")
                .build();
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleEventDateException(final EventDateException e) {
        log.warn(e.getMessage());
        final var apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .build();
        return ResponseEntity.status(409).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleMissingServletRequestParameterException(
            final MissingServletRequestParameterException e
    ) {
        final String error = "Missing request parameter: " + e.getParameterName();
        log.warn(error);
        final var apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(error)
                .reason("Incorrectly made request.")
                .build();
        return ResponseEntity.status(400).body(apiError);
    }
}
