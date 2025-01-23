package com.kachinga.eschool.repository.specs;

import com.kachinga.eschool.entity.Stream;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class StreamSpecification {
    public static Specification<Stream> nameLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> field = criteriaBuilder.lower(root.get("name"));
            return criteriaBuilder.like(field, "%" + searchTermLowerCase + "%");
        };
    }

    public static Specification<Stream> byGradeLevelId(Long gradeLevelId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gradeLevel").get("id"), gradeLevelId);
    }
}
