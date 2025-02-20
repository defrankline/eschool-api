package com.kachinga.eschool.controller;

import com.kachinga.eschool.config.ApiConfig;
import com.kachinga.eschool.entity.Subject;
import com.kachinga.eschool.service.SubjectService;
import com.kachinga.eschool.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(ApiConfig.API + "/subjects")
@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "20") int size,
                                    @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<Subject> data = subjectService.findAll(filter, PageRequest.of(page <= 0 ? 0 : page, size <= 0 ? 20 : size, Sort.by("name").descending()));
        return ApiResponse.list(data.getContent(), data.getTotalElements(), data.getTotalPages(), "Subjects");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Subject subject) {
        Subject created = subjectService.save(subject);
        return ApiResponse.create(created, created.getId(), "Subject Created Successfully");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Subject subject) {
        Optional<Subject> row = subjectService.findById(id);
        if (row.isPresent()) {
            Subject updated = subjectService.save(subject);
            return ApiResponse.update(updated, "Subject Updated Successfully");
        }
        return ApiResponse.notFound(id, "Subject Not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Optional<Subject> row = subjectService.findById(id);
        if (row.isPresent()) {
            Subject subject = row.get();
            return ApiResponse.get(subject, "Subject");
        }
        return ApiResponse.notFound(id, "Subject Not found");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Subject> row = subjectService.findById(id);
        if (row.isPresent()) {
            subjectService.delete(id);
            return ApiResponse.deleteMessage("Subject deleted successfully");
        }
        return ApiResponse.notFound(id, "Subject Not found");
    }
}
