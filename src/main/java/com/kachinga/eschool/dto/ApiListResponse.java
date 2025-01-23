package com.kachinga.eschool.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiListResponse<T> implements Serializable {
    private List<T> data;
    private long totalItems;
    private int totalPages;
    private String message;
}
