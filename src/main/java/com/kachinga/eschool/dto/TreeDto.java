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
public class TreeDto implements Serializable {
    private Long id;
    private String key;
    private String label;
    private String data;
    private String icon;
    private String routerLink;
    private int sortOrder;
    private List<TreeDto> children;
    private Long parentId;
}
