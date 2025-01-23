package com.kachinga.eschool.service;

import com.kachinga.eschool.entity.AcademicYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AcademicYearService {
    Page<AcademicYear> findAll(String searchTerm, Pageable pageable);

    AcademicYear save(AcademicYear academicYear);

    Optional<AcademicYear> findById(Long id);

    void delete(Long id);
}
