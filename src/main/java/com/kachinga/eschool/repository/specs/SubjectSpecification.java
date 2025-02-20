package com.kachinga.eschool.repository.specs;

import com.kachinga.eschool.entity.Subject;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class SubjectSpecification {
    public static Specification<Subject> nameLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> field = criteriaBuilder.lower(root.get("name"));
            return criteriaBuilder.like(field, "%" + searchTermLowerCase + "%");
        };
    }

    public static Specification<Subject> shortNameLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> field = criteriaBuilder.lower(root.get("shortName"));
            return criteriaBuilder.like(field, "%" + searchTermLowerCase + "%");
        };
    }

    public static Specification<Subject> descriptionLike(String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            String searchTermLowerCase = searchTerm.toLowerCase();
            Expression<String> field = criteriaBuilder.lower(root.get("description"));
            return criteriaBuilder.like(field, "%" + searchTermLowerCase + "%");
        };
    }

    public static Specification<Subject> search(Long schoolId, String searchTerm) {
        return (nameLike(searchTerm).or(shortNameLike(searchTerm)));
    }

    public static Specification<Subject> search(String searchTerm) {
        return nameLike(searchTerm).or(shortNameLike(searchTerm)).or(descriptionLike(searchTerm));
    }
}
