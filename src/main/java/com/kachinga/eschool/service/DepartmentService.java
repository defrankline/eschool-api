package com.kachinga.eschool.service;

import com.kachinga.eschool.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface DepartmentService {
    Page<Department> findAll(String searchTerm, Pageable pageable);

    Department save(Department department);

    Optional<Department> findById(Long id);

    void delete(Long id);
}
