package com.dinsaren.springbootjwtapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Body of every error response. {@code errors} maps a field name to its
 * validation message and is only present for 400 validation failures.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorRes(int status, String message, Map<String, String> errors, LocalDateTime timestamp) {

    public static ErrorRes of(HttpStatus status, String message) {
        return new ErrorRes(status.value(), message, null, LocalDateTime.now());
    }

    public static ErrorRes of(HttpStatus status, String message, Map<String, String> errors) {
        return new ErrorRes(status.value(), message, errors, LocalDateTime.now());
    }
}
