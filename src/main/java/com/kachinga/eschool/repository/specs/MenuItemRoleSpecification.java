package com.kachinga.eschool.repository.specs;

import com.kachinga.eschool.entity.MenuItemRole;
import org.springframework.data.jpa.domain.Specification;

public class MenuItemRoleSpecification {
    public static Specification<MenuItemRole> getByRole(Long roleId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role").get("id"), roleId);
    }

    public static Specification<MenuItemRole> getByMenuItem(Long menuItemId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("menuItem").get("id"), menuItemId);
    }
}
