package com.example.tennisscore.infrastructure.adapter.output.exception.handler;

import com.example.tennisscore.domain.exception.InvalidGameInputException;
import com.example.tennisscore.infrastructure.adapter.output.exception.InvalidInputException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidGameInputException.class)
    public ProblemDetail handleInputError(InvalidGameInputException ex, HttpServletRequest request) {
        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InvalidInputException.class)
    public ProblemDetail handleGameInputError(InvalidInputException ex, HttpServletRequest request) {
        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String title, String instanceUri) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(title);
        problemDetail.setInstance(URI.create(instanceUri));
        return problemDetail;
    }
}