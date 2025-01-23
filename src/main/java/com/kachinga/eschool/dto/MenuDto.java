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
public class MenuDto implements Serializable {
    private Long id;
    private String label;
    private String icon;
    private String routerLink;
    private int sortOrder;
    private Long parentId;
    private List<MenuDto> children;
}
