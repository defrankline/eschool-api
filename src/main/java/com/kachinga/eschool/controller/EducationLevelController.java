package com.kachinga.eschool.controller;

import com.kachinga.eschool.config.ApiConfig;
import com.kachinga.eschool.entity.EducationLevel;
import com.kachinga.eschool.service.EducationLevelService;
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

@RequestMapping(ApiConfig.API + "/education-levels")
@RestController
@RequiredArgsConstructor
public class EducationLevelController {
    private final EducationLevelService educationLevelService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "20") int size,
                                    @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<EducationLevel> data = educationLevelService.findAll(filter, PageRequest.of(page <= 0 ? 0 : page, size <= 0 ? 20 : size, Sort.by("name").descending()));
        return ApiResponse.list(data.getContent(), data.getTotalElements(), data.getTotalPages(), "Education Levels");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPERVISOR')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody EducationLevel educationLevel) {
        EducationLevel created = educationLevelService.save(educationLevel);
        return ApiResponse.create(created, created.getId(), "Education Level Created Successfully");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPERVISOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody EducationLevel educationLevel) {
        Optional<EducationLevel> row = educationLevelService.findById(id);
        if (row.isPresent()) {
            EducationLevel updated = educationLevelService.save(educationLevel);
            return ApiResponse.update(updated, "Education Level Updated Successfully");
        }
        return ApiResponse.notFound(id, "Education Level Not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Optional<EducationLevel> row = educationLevelService.findById(id);
        if (row.isPresent()) {
            EducationLevel educationLevel = row.get();
            return ApiResponse.get(educationLevel, "Education Level");
        }
        return ApiResponse.notFound(id, "Education Level Not found");
    }

    @PreAuthorize("hasRole('ROLE_ESCHOOL_SUPERVISOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<EducationLevel> row = educationLevelService.findById(id);
        if (row.isPresent()) {
            educationLevelService.delete(id);
            return ApiResponse.deleteMessage("Education Level deleted successfully");
        }
        return ApiResponse.notFound(id, "Education Level Not found");
    }
}
