package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.config.UserContextService;
import com.kachinga.eschool.dto.LoggedInUserDto;
import com.kachinga.eschool.entity.GradeLevel;
import com.kachinga.eschool.entity.School;
import com.kachinga.eschool.repository.GradeLevelRepository;
import com.kachinga.eschool.repository.specs.GradeLevelSpecification;
import com.kachinga.eschool.service.GradeLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeLevelServiceImpl implements GradeLevelService {
    private final GradeLevelRepository gradeLevelRepository;
    private final UserContextService userContextService;


    @Override
    public Page<GradeLevel> findAll(String searchTerm, Pageable pageable) {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        Long schoolId = currentUser.getSchool().getId();
        Specification<GradeLevel> spec = Specification.where(GradeLevelSpecification.bySchoolId(schoolId));
        if (searchTerm != null && !searchTerm.isEmpty()) {
            pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            spec = spec.and(GradeLevelSpecification.nameLike(searchTerm));
        }
        return gradeLevelRepository.findAll(spec, pageable);
    }

    @Override
    public GradeLevel save(GradeLevel gradeLevel) {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        School school = currentUser.getSchool();
        gradeLevel.setSchool(school);
        return gradeLevelRepository.save(gradeLevel);
    }

    @Override
    public Optional<GradeLevel> findById(Long id) {
        return gradeLevelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        gradeLevelRepository.deleteById(id);
    }
}
