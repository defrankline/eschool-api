package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.entity.Subject;
import com.kachinga.eschool.repository.SubjectRepository;
import com.kachinga.eschool.repository.specs.SubjectSpecification;
import com.kachinga.eschool.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;


    @Override
    public Page<Subject> findAll(String searchTerm, Pageable pageable) {
        Specification<Subject> spec = Specification.where(null);
        if (searchTerm != null && !searchTerm.isEmpty()) {
            pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            spec = spec.and(SubjectSpecification.search(searchTerm));
        }
        return subjectRepository.findAll(spec, pageable);
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}
