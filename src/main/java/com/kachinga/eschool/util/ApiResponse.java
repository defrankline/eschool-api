package com.kachinga.eschool.util;

import com.kachinga.eschool.dto.ApiListResponse;
import com.kachinga.eschool.dto.ApiObjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

public final class ApiResponse {

    public static <T> ResponseEntity<?> list(List<T> list, long totalElements, int totalPages, String message) {
        ApiListResponse<T> response = new ApiListResponse<>(list, totalElements, totalPages, message.toUpperCase());
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<?> message(String message) {
        ApiObjectResponse<T> response = new ApiObjectResponse<>(message.toUpperCase());
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<?> deleteMessage(String message) {
        ApiObjectResponse<T> response = new ApiObjectResponse<>(message.toUpperCase());
        return ResponseEntity.accepted().body(response);
    }

    public static <T> ResponseEntity<?> get(T created, String message) {
        ApiObjectResponse<T> response = new ApiObjectResponse<>(created, message.toUpperCase());
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<?> create(T created, Object id, String message) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        ApiObjectResponse<T> response = new ApiObjectResponse<>(created, message.toUpperCase());
        return ResponseEntity.created(location).body(response);
    }

    public static <T> ResponseEntity<?> update(T created, String message) {
        ApiObjectResponse<T> response = new ApiObjectResponse<>(created, message.toUpperCase());
        return ResponseEntity.accepted().body(response);
    }

    public static <T> ResponseEntity<?> notFound(T created, String message) {
        ApiObjectResponse<T> response = new ApiObjectResponse<>(created, message.toUpperCase());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
