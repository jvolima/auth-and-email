package com.jvolima.authandemail.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception e) {
        ProblemDetail problemDetail = null;
        if (e instanceof BadRequestException) {
            problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(400), e.getMessage()
            );
        }
        if (e instanceof BadCredentialsException) {
            problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(401), e.getMessage()
            );
            problemDetail.setProperty("access_denied_reason", "Authentication Failure.");
        }
        if (e instanceof SignatureException || e instanceof MalformedJwtException) {
            problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(401), e.getMessage()
            );
            problemDetail.setProperty("access_denied_reason", "Invalid token.");
        }
        if (e instanceof ExpiredJwtException) {
            problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(401), e.getMessage()
            );
            problemDetail.setProperty("access_denied_reason", "Token expired.");
        }
        if (e instanceof UnauthorizedException) {
            problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(401), e.getMessage()
            );
        }
        if (e instanceof AccessDeniedException) {
            problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403), e.getMessage()
            );
            problemDetail.setProperty("access_denied_reason", "Not authorized.");
        }
        if (e instanceof NotFoundException) {
            problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(404), e.getMessage()
            );
        }

        return problemDetail;
    }
}
