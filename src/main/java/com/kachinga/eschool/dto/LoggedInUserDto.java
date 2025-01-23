package com.kachinga.eschool.dto;

import com.kachinga.eschool.entity.School;
import com.kachinga.eschool.entity.Sex;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoggedInUserDto implements Serializable {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Sex sex;
    private String photo;
    private String token;
    private Long tokenExpire;
    private String email;
    private School school;
    private List<String> roles;
    private List<MenuDto> menus;
    private List<NotificationDto> notifications;
}
