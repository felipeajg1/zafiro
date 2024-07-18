package com.app.zafiro.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
    private static final String MESSAGE = "message";

    public static ResponseEntity<Map<String, Object>> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return createErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Map<String, Object>> handleResourceNotFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    private static ResponseEntity<Map<String, Object>> createErrorResponse(Exception e, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE, e.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}
