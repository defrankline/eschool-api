package com.kachinga.eschool.dto;

import com.kachinga.eschool.entity.Role;
import com.kachinga.eschool.entity.School;
import com.kachinga.eschool.entity.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterUserDto implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private Sex sex;
    private School school;
    private Role role;
    private String photo;
}
