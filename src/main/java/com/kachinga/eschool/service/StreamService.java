package com.kachinga.eschool.service;

import com.kachinga.eschool.entity.Stream;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StreamService {
    List<Stream> findAll(Long gradeLevelId);

    Stream save(Stream stream);

    Optional<Stream> findById(Long id);

    void delete(Long id);
}
