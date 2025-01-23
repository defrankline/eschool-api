package com.kachinga.eschool.service;

import com.kachinga.eschool.entity.GradeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface GradeLevelService {
    Page<GradeLevel> findAll(String searchTerm, Pageable pageable);

    GradeLevel save(GradeLevel gradeLevel);

    Optional<GradeLevel> findById(Long id);

    void delete(Long id);
}
