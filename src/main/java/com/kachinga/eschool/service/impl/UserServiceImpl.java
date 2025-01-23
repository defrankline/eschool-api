package com.kachinga.eschool.service.impl;


import com.kachinga.eschool.dto.LoggedInUserDto;
import com.kachinga.eschool.dto.MenuDto;
import com.kachinga.eschool.entity.Role;
import com.kachinga.eschool.entity.User;
import com.kachinga.eschool.repository.UserRepository;
import com.kachinga.eschool.service.MenuItemService;
import com.kachinga.eschool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MenuItemService menuItemService;

    @Override
    public List<User> findAll(Long companyId, String sortBy, String direction) {
        return List.of();
    }

    @Override
    public List<User> findAll(Long companyId, String sortBy, String direction, int page, int size) {
        return List.of();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void activate(Long id) {
        Optional<User> row = userRepository.findById(id);
        if (row.isPresent()) {
            User user = row.get();
            user.setActive(true);
            userRepository.save(user);
        }
    }

    @Override
    public void deactivate(Long id) {
        Optional<User> row = userRepository.findById(id);
        if (row.isPresent()) {
            User user = row.get();
            user.setActive(false);
            userRepository.save(user);
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public LoggedInUserDto currentUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
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
        } else {
            return null;
        }
    }
}
