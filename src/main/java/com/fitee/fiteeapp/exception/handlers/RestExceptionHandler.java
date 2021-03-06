package com.fitee.fiteeapp.exception.handlers;

import com.fitee.fiteeapp.dto.ExceptionDto;
import com.fitee.fiteeapp.exception.ExpiredTokenException;
import com.fitee.fiteeapp.exception.ResourceAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * This class makes sure that the correct error response is send back to the client and
 * gives the flexibility to create custom error responses.
 * <p>
 * NOTE: Since Spring boot v.2.3.0 they changed server messages to never be included.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExpiredTokenException.class)
    public ExceptionDto handleExpiredTokenException(ExpiredTokenException ex, HttpServletRequest request) {
        return new ExceptionDto(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ExceptionDto handleExistingResourceException(ResourceAlreadyExistsException ex, HttpServletRequest request) {
        return new ExceptionDto(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }
}
