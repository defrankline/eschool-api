package com.kachinga.eschool.service;

import com.kachinga.eschool.entity.EducationLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface EducationLevelService {
    Page<EducationLevel> findAll(String searchTerm, Pageable pageable);

    EducationLevel save(EducationLevel educationLevel);

    Optional<EducationLevel> findById(Long id);

    void delete(Long id);
}
