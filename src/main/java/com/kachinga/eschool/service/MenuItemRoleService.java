package com.kachinga.eschool.service;

import com.kachinga.eschool.entity.MenuItemRole;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MenuItemRoleService {
    Optional<MenuItemRole> findById(Long id);

    Optional<MenuItemRole> findExisting(Long roleId, Long menuId);

    List<MenuItemRole> save(List<MenuItemRole> menuItemRole);

    void delete(Long id);

    void deleteMultiple(List<Long> ids);

    List<MenuItemRole> findAll(Long roleId, Long menuItemId);

    List<MenuItemRole> findAllByRoleId(Long roleId);
    List<MenuItemRole> findAllByMenuItemId(Long menuItemId);

    Page<MenuItemRole> findAll(Long roleId, Long menuItemId, int page, int size);

    void deleteByRoleAndMenu(Long roleId, Long menuItemId);
}

