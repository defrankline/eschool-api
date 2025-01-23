package com.kachinga.eschool.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequest implements Serializable {
    private String email;
    private String password;
}
