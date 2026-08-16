package com.tennis.scoreboard.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleBadRequest(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<ErrorMessage>handleNotFound(MatchNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleTypeMismatch(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest().body(new ErrorMessage("Некорректный формат идентификатора"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleUnreadableBody(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body(new ErrorMessage("Тело запроса отсутствует или некорректно"));
    }
}
