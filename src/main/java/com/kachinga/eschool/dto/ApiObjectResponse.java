package com.kachinga.eschool.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiObjectResponse<T> implements Serializable {
    private T data;
    private String message;

    public ApiObjectResponse(String message) {
        this.message = message;
    }
}
