package com.kachinga.eschool.controller;

import com.kachinga.eschool.config.ApiConfig;
import com.kachinga.eschool.entity.Stream;
import com.kachinga.eschool.service.StreamService;
import com.kachinga.eschool.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(ApiConfig.API + "/streams")
@RestController
@RequiredArgsConstructor
public class StreamController {
    private final StreamService streamService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "gradeLevelId") Long gradeLevelId) {
        List<Stream> data = streamService.findAll(gradeLevelId);
        return ApiResponse.list(data, data.size(), 1, "Streams");
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Stream stream) {
        Stream created = streamService.save(stream);
        return ApiResponse.create(created, created.getId(), "Stream Created Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Stream stream) {
        Optional<Stream> row = streamService.findById(id);
        if (row.isPresent()) {
            Stream updated = streamService.save(stream);
            return ApiResponse.update(updated, "Stream Updated Successfully");
        }
        return ApiResponse.notFound(id, "Stream Not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        Optional<Stream> row = streamService.findById(id);
        if (row.isPresent()) {
            Stream stream = row.get();
            return ApiResponse.get(stream, "Stream");
        }
        return ApiResponse.notFound(id, "Stream Not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Stream> row = streamService.findById(id);
        if (row.isPresent()) {
            streamService.delete(id);
            return ApiResponse.deleteMessage("Stream deleted successfully");
        }
        return ApiResponse.notFound(id, "Stream Not found");
    }
}
