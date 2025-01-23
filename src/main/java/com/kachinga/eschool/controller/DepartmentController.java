package com.kachinga.eschool.controller;

import com.kachinga.eschool.config.ApiConfig;
import com.kachinga.eschool.entity.Department;
import com.kachinga.eschool.service.DepartmentService;
import com.kachinga.eschool.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(ApiConfig.API + "/departments")
@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "20") int size,
                                    @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<Department> data = departmentService.findAll(filter, PageRequest.of(page <= 0 ? 0 : page, size <= 0 ? 20 : size, Sort.by("name").descending()));
        return ApiResponse.list(data.getContent(), data.getTotalElements(), data.getTotalPages(), "Departments");
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Department department) {
        Department created = departmentService.save(department);
        return ApiResponse.create(created, created.getId(), "Department Created Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Department department) {
        Optional<Department> row = departmentService.findById(id);
        if (row.isPresent()) {
            Department updated = departmentService.save(department);
            return ApiResponse.update(updated, "Department Updated Successfully");
        }
        return ApiResponse.notFound(id, "Department Not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Optional<Department> row = departmentService.findById(id);
        if (row.isPresent()) {
            Department department = row.get();
            return ApiResponse.get(department, "Department");
        }
        return ApiResponse.notFound(id, "Department Not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Department> row = departmentService.findById(id);
        if (row.isPresent()) {
            departmentService.delete(id);
            return ApiResponse.deleteMessage("Department deleted successfully");
        }
        return ApiResponse.notFound(id, "Department Not found");
    }
}
