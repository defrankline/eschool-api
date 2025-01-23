package com.kachinga.eschool.controller;

import com.kachinga.eschool.config.ApiConfig;
import com.kachinga.eschool.entity.AcademicYear;
import com.kachinga.eschool.service.AcademicYearService;
import com.kachinga.eschool.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(ApiConfig.API + "/academic-years")
@RestController
@RequiredArgsConstructor
public class AcademicYearController {
    private final AcademicYearService academicYearService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "20") int size, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<AcademicYear> data = academicYearService.findAll(filter, PageRequest.of(page <= 0 ? 0 : page, size <= 0 ? 20 : size, Sort.by("startDate").descending()));
        return ApiResponse.list(data.getContent(), data.getTotalElements(), data.getTotalPages(), "Academic Years");
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AcademicYear academicYear) {
        AcademicYear created = academicYearService.save(academicYear);
        return ApiResponse.create(created, created.getId(), "Academic Year Created Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody AcademicYear academicYear) {
        Optional<AcademicYear> row = academicYearService.findById(id);
        if (row.isPresent()) {
            AcademicYear updated = academicYearService.save(academicYear);
            return ApiResponse.update(updated, "Academic Year Updated Successfully");
        }
        return ApiResponse.notFound(id, "Academic Year Not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Optional<AcademicYear> row = academicYearService.findById(id);
        if (row.isPresent()) {
            AcademicYear academicYear = row.get();
            return ApiResponse.get(academicYear, "Academic Year");
        }
        return ApiResponse.notFound(id, "Academic Year Not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<AcademicYear> row = academicYearService.findById(id);
        if (row.isPresent()) {
            academicYearService.delete(id);
            return ApiResponse.deleteMessage("Academic Year deleted successfully");
        }
        return ApiResponse.notFound(id, "Academic Year Not found");
    }
}
