package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.entity.EducationLevel;
import com.kachinga.eschool.repository.EducationLevelRepository;
import com.kachinga.eschool.repository.specs.EducationLevelSpecification;
import com.kachinga.eschool.service.EducationLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationLevelServiceImpl implements EducationLevelService {
    private final EducationLevelRepository educationLevelRepository;


    @Override
    public Page<EducationLevel> findAll(String searchTerm, Pageable pageable) {
        Specification<EducationLevel> spec = Specification.where(null);
        if (searchTerm != null && !searchTerm.isEmpty()) {
            pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            spec = spec.and(EducationLevelSpecification.nameLike(searchTerm));
        }
        return educationLevelRepository.findAll(spec, pageable);
    }

    @Override
    public EducationLevel save(EducationLevel educationLevel) {
        return educationLevelRepository.save(educationLevel);
    }

    @Override
    public Optional<EducationLevel> findById(Long id) {
        return educationLevelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        educationLevelRepository.deleteById(id);
    }
}
