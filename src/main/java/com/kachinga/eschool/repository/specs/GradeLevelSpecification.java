package com.kachinga.eschool.repository.specs;

import com.kachinga.eschool.entity.GradeLevel;
import com.kachinga.eschool.entity.Stream;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class GradeLevelSpecification {
    public static Specification<GradeLevel> nameLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> field = criteriaBuilder.lower(root.get("name"));
            return criteriaBuilder.like(field, "%" + searchTermLowerCase + "%");
        };
    }

    public static Specification<GradeLevel> byLevelId(Long educationLevelId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("educationLevel").get("id"), educationLevelId);
    }
}
