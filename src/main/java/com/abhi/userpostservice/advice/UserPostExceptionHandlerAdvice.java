package com.abhi.userpostservice.advice;

import com.abhi.userpostservice.exception.UserNotFoundException;
import com.abhi.userpostservice.model.ErrorDetails;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserPostExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public UserPostExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage(), request.getDescription(false), LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage(), request.getDescription(false), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        //String validationMessages = Objects.requireNonNull(ex.getFieldErrors()).stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("  "));

        String validationMessages = Objects.requireNonNull(ex.getFieldErrors()).stream().map(fieldError -> messageSource.getMessage(Objects.requireNonNull(fieldError.getDefaultMessage()), null, LocaleContextHolder.getLocale())).collect(Collectors.joining());

        return new ResponseEntity<>(new ErrorDetails(validationMessages, request.getDescription(false), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
}
