package io.test.gameloft.exceptions;

import io.test.gameloft.models.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(
            Exception ex,
            HttpServletRequest request
    ) {
        return createError(
                ex,
                request,
                HttpStatus.NOT_FOUND
        );
    }

    private ErrorDto createError(Exception exception, HttpServletRequest request, HttpStatus errorCode) {
        return new ErrorDto(
                errorCode.value(),
                exception.getMessage(),
                request.getRequestURI()
        );
    }
}
