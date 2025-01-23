package com.kachinga.eschool.repository.specs;

import com.kachinga.eschool.entity.MenuItem;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class MenuItemSpecification {
    public static Specification<MenuItem> labelLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> nameLowerCase = criteriaBuilder.lower(root.get("label"));
            return criteriaBuilder.like(nameLowerCase, "%" + searchTermLowerCase + "%");
        };
    }

    public static Specification<MenuItem> routerLinkLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> nameLowerCase = criteriaBuilder.lower(root.get("routerLink"));
            return criteriaBuilder.like(nameLowerCase, "%" + searchTermLowerCase + "%");
        };
    }

    public static Specification<MenuItem> parent(Long parentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("parentId"), parentId);
    }
}
