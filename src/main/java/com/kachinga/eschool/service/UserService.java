package com.kachinga.eschool.service;

import com.kachinga.eschool.dto.LoggedInUserDto;
import com.kachinga.eschool.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll(Long companyId, String sortBy, String direction);

    List<User> findAll(Long companyId, String sortBy, String direction, int page, int size);

    User save(User user);

    void activate(Long id);

    void deactivate(Long id);

    void delete(Long id);

    LoggedInUserDto currentUser(Long userId);

    Optional<User> findById(Long id);
}
