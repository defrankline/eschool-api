package com.kachinga.eschool.controller;

import com.kachinga.eschool.dto.*;
import com.kachinga.eschool.entity.Role;
import com.kachinga.eschool.entity.User;
import com.kachinga.eschool.service.AuthenticationService;
import com.kachinga.eschool.security.JwtService;
import com.kachinga.eschool.service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final MenuItemService menuItemService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, MenuItemService menuItemService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.menuItemService = menuItemService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedInUserDto> authenticate(@RequestBody LoginRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoggedInUserDto loginResponse = new LoggedInUserDto();
        loginResponse.setToken(jwtToken);
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + 86400000);
        loginResponse.setTokenExpire(tomorrow.getTime());
        loginResponse.setFirstName(authenticatedUser.getFirstName());
        loginResponse.setMiddleName(authenticatedUser.getMiddleName());
        loginResponse.setLastName(authenticatedUser.getLastName());
        loginResponse.setEmail(authenticatedUser.getEmail());
        loginResponse.setSchool(authenticatedUser.getSchool());
        loginResponse.setId(authenticatedUser.getId());

        List<Long> roleIds = authenticatedUser.getRoles().stream().map(Role::getId).toList();
        List<MenuDto> menuItems = menuItemService.roleMenuItems(roleIds, 0L);
        List<NotificationDto> notificationSet = authenticatedUser.getNotifications().stream().map(NotificationDto::new).toList();
        loginResponse.setMenus(menuItems);
        loginResponse.setNotifications(notificationSet);
        loginResponse.setRoles(authenticatedUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return ResponseEntity.accepted().body(loginResponse);
    }
}
