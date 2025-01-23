package com.kachinga.eschool.repository.specs;

import com.kachinga.eschool.entity.AcademicYear;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class AcademicYearSpecification {
    public static Specification<AcademicYear> nameLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> field = criteriaBuilder.lower(root.get("name"));
            return criteriaBuilder.like(field, "%" + searchTermLowerCase + "%");
        };
    }

    public static Specification<AcademicYear> bySchoolId(Long schoolId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("school").get("id"), schoolId);
    }
}
