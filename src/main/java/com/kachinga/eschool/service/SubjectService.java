package com.kachinga.eschool.service;

import com.kachinga.eschool.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SubjectService {
    Page<Subject> findAll(String searchTerm, Pageable pageable);

    Subject save(Subject subject);

    Optional<Subject> findById(Long id);

    void delete(Long id);
}
