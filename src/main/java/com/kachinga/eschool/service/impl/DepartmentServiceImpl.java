package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.config.UserContextService;
import com.kachinga.eschool.dto.LoggedInUserDto;
import com.kachinga.eschool.entity.Department;
import com.kachinga.eschool.entity.School;
import com.kachinga.eschool.repository.DepartmentRepository;
import com.kachinga.eschool.repository.specs.DepartmentSpecification;
import com.kachinga.eschool.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final UserContextService userContextService;


    @Override
    public Page<Department> findAll(String searchTerm, Pageable pageable) {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        Long schoolId = currentUser.getSchool().getId();
        Specification<Department> spec = Specification.where(DepartmentSpecification.bySchoolId(schoolId));
        if (searchTerm != null && !searchTerm.isEmpty()) {
            pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            spec = spec.and(DepartmentSpecification.nameLike(searchTerm));
        }
        return departmentRepository.findAll(spec, pageable);
    }

    @Override
    public Department save(Department department) {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        School school = currentUser.getSchool();
        department.setSchool(school);
        return departmentRepository.save(department);
    }

    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
