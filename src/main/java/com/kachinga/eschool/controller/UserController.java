package com.kachinga.eschool.controller;

import com.kachinga.eschool.config.UserContextService;
import com.kachinga.eschool.dto.LoggedInUserDto;
import com.kachinga.eschool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserContextService userContextService;

    @GetMapping("/me")
    public ResponseEntity<LoggedInUserDto> authenticatedUser() {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/currentUser/{id}")
    public ResponseEntity<LoggedInUserDto> currentUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.currentUser(id));
    }
}
