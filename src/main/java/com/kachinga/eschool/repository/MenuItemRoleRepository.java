package com.kachinga.eschool.repository;

import com.kachinga.eschool.entity.MenuItemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRoleRepository extends JpaRepository<MenuItemRole, Long>, JpaSpecificationExecutor<MenuItemRole> {
    Optional<MenuItemRole> findByRoleIdAndMenuItemId(Long roleId, Long menuItemId);

    List<MenuItemRole> findAllByRoleId(Long roleId);

    List<MenuItemRole> findAllByMenuItemId(Long menuItemId);
}