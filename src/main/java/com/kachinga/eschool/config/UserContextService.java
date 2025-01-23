package com.kachinga.eschool.config;

import com.kachinga.eschool.dto.LoggedInUserDto;
import com.kachinga.eschool.dto.MenuDto;
import com.kachinga.eschool.entity.Role;
import com.kachinga.eschool.entity.User;
import com.kachinga.eschool.repository.UserRepository;
import com.kachinga.eschool.service.MenuItemService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserContextService {

    private final UserRepository userRepository;
    private final MenuItemService menuItemService;

    public UserContextService(UserRepository userRepository, MenuItemService menuItemService) {
        this.userRepository = userRepository;
        this.menuItemService = menuItemService;
    }

    public LoggedInUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Handle the case where there is no authenticated user
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            // Assuming username is unique and used for fetching the user
            User user = userRepository.findByEmail(username).orElse(null);
            List<String> roles = user.getRoles().stream().map(Role::getName).toList();
            List<Long> roleIds = user.getRoles().stream().map(Role::getId).toList();
            List<MenuDto> menuItems = menuItemService.roleMenuItems(roleIds, 0L);
            LoggedInUserDto userDto = new LoggedInUserDto();
            userDto.setId(user.getId());
            userDto.setSchool(user.getSchool());
            userDto.setEmail(user.getEmail());
            userDto.setFirstName(user.getFirstName());
            userDto.setMiddleName(user.getMiddleName());
            userDto.setLastName(user.getLastName());
            userDto.setSex(user.getSex());
            userDto.setRoles(roles);
            userDto.setMenus(menuItems);
            return userDto;
        }

        // Handle any other cases, such as different types of Authentication tokens
        return null;
    }
}

