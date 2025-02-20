package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.security.UserContextService;
import com.kachinga.eschool.dto.LoggedInUserDto;
import com.kachinga.eschool.entity.AcademicYear;
import com.kachinga.eschool.entity.School;
import com.kachinga.eschool.repository.AcademicYearRepository;
import com.kachinga.eschool.repository.specs.AcademicYearSpecification;
import com.kachinga.eschool.service.AcademicYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcademicYearServiceImpl implements AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final UserContextService userContextService;


    @Override
    public Page<AcademicYear> findAll(String searchTerm, Pageable pageable) {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        Long schoolId = currentUser.getSchool().getId();
        Specification<AcademicYear> spec = Specification.where(AcademicYearSpecification.bySchoolId(schoolId));
        if (searchTerm != null && !searchTerm.isEmpty()) {
            pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            spec = spec.and(AcademicYearSpecification.nameLike(searchTerm));
        }
        return academicYearRepository.findAll(spec, pageable);
    }

    @Override
    public AcademicYear save(AcademicYear academicYear) {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        School school = currentUser.getSchool();
        academicYear.setSchool(school);
        return academicYearRepository.save(academicYear);
    }

    @Override
    public Optional<AcademicYear> findById(Long id) {
        return academicYearRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        academicYearRepository.deleteById(id);
    }
}
