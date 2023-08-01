package com.bol.kalaha.web;


import com.bol.kalaha.exception.GameIsFullException;
import com.bol.kalaha.exception.GameNotFountException;
import com.bol.kalaha.exception.GameNotStartedException;
import com.bol.kalaha.exception.WrongMoveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {


    @ExceptionHandler(GameIsFullException.class)
    private ResponseEntity<ProblemDetail> handleGameIsFullException(GameIsFullException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.of(createProblem(ex.getMessage(), HttpStatus.BAD_REQUEST)).build();
    }

    @ExceptionHandler(GameNotFountException.class)
    private ResponseEntity<ProblemDetail> handleGameNotFountException(GameNotFountException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.of(createProblem(ex.getMessage(), HttpStatus.NOT_FOUND)).build();
    }

    @ExceptionHandler(GameNotStartedException.class)
    private ResponseEntity<ProblemDetail> handleGameNotStartedException(GameNotStartedException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.of(createProblem(ex.getMessage(), HttpStatus.BAD_REQUEST)).build();
    }

    @ExceptionHandler(WrongMoveException.class)
    private ResponseEntity<ProblemDetail> handleWrongMoveException(WrongMoveException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.of(createProblem(ex.getMessage(), HttpStatus.BAD_REQUEST)).build();
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.of(createProblem(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.of(createProblem(ex.getMessage(), HttpStatus.BAD_REQUEST)).build();
    }

    private ProblemDetail createProblem(String detail, HttpStatus status) {
        return ProblemDetail.forStatusAndDetail(status, detail);
    }

}
