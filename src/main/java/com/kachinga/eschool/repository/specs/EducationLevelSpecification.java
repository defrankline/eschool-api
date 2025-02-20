package com.kachinga.eschool.repository.specs;

import com.kachinga.eschool.entity.EducationLevel;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class EducationLevelSpecification {
    public static Specification<EducationLevel> nameLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> field = criteriaBuilder.lower(root.get("name"));
            return criteriaBuilder.like(field, "%" + searchTermLowerCase + "%");
        };
    }
}
