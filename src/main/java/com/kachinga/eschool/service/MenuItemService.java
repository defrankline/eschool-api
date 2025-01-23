package com.kachinga.eschool.service;

import com.kachinga.eschool.dto.MenuDto;
import com.kachinga.eschool.dto.TreeDto;
import com.kachinga.eschool.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MenuItemService {
    Optional<MenuItem> findById(Long id);

    MenuItem save(MenuItem menuItem);

    MenuItem update(MenuItem menuItem);

    void delete(Long id);

    void deleteMultiple(List<Long> ids);

    List<MenuItem> findAll(Long parentId, String searchTerm, String sortBy, String direction);

    Page<MenuItem> findAll(Long parentId, int page, int size, String searchTerm, String sortBy, String direction);

    List<MenuDto> parents();

    List<MenuDto> parents(List<Long> roles);

    List<MenuDto> roleMenuItems(List<Long> roles, Long parentId);

    List<TreeDto> tree(Long parentId);

    List<TreeDto> tree(List<Long> roles, Long parentId);

    List<MenuDto> children(Long parentId);

    List<MenuDto> children(List<Long> roles, Long parentId);
}

