package com.kachinga.eschool.controller;

import com.kachinga.eschool.config.ApiConfig;
import com.kachinga.eschool.entity.GradeLevel;
import com.kachinga.eschool.service.GradeLevelService;
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

@RequestMapping(ApiConfig.API + "/grade-levels")
@RestController
@RequiredArgsConstructor
public class GradeLevelController {
    private final GradeLevelService gradeLevelService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "20") int size,
                                    @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<GradeLevel> data = gradeLevelService.findAll(filter, PageRequest.of(page <= 0 ? 0 : page, size <= 0 ? 20 : size, Sort.by("name").descending()));
        return ApiResponse.list(data.getContent(), data.getTotalElements(), data.getTotalPages(), "Grade Levels");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody GradeLevel gradeLevel) {
        GradeLevel created = gradeLevelService.save(gradeLevel);
        return ApiResponse.create(created, created.getId(), "Grade Level Created Successfully");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody GradeLevel gradeLevel) {
        Optional<GradeLevel> row = gradeLevelService.findById(id);
        if (row.isPresent()) {
            GradeLevel updated = gradeLevelService.save(gradeLevel);
            return ApiResponse.update(updated, "Grade Level Updated Successfully");
        }
        return ApiResponse.notFound(id, "Grade Level Not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Optional<GradeLevel> row = gradeLevelService.findById(id);
        if (row.isPresent()) {
            GradeLevel gradeLevel = row.get();
            return ApiResponse.get(gradeLevel, "Grade Level");
        }
        return ApiResponse.notFound(id, "Grade Level Not found");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<GradeLevel> row = gradeLevelService.findById(id);
        if (row.isPresent()) {
            gradeLevelService.delete(id);
            return ApiResponse.deleteMessage("Grade Level deleted successfully");
        }
        return ApiResponse.notFound(id, "Grade Level Not found");
    }
}
