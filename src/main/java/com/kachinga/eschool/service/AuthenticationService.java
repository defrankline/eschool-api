package com.kachinga.eschool.service;

import com.kachinga.eschool.dto.LoginRequest;
import com.kachinga.eschool.dto.RegisterUserDto;
import com.kachinga.eschool.entity.Role;
import com.kachinga.eschool.entity.User;
import com.kachinga.eschool.entity.UserRole;
import com.kachinga.eschool.repository.RoleRepository;
import com.kachinga.eschool.repository.UserRepository;
import com.kachinga.eschool.repository.UserRoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setMiddleName(input.getMiddleName());
        user.setLastName(input.getLastName());
        user.setSex(input.getSex());
        user.setEmail(input.getEmail());
        user.setPhoto(input.getPhoto());
        user.setSchool(input.getSchool());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        User created = userRepository.save(user);
        Role role = roleRepository.findById(input.getRole().getId()).orElse(null);
        if (role != null) {
            UserRole userRole = new UserRole(created, role);
            userRoleRepository.save(userRole);
        }
        return created;
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
