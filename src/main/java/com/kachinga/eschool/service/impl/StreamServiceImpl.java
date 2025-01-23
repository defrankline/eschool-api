package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.entity.Stream;
import com.kachinga.eschool.repository.StreamRepository;
import com.kachinga.eschool.repository.specs.StreamSpecification;
import com.kachinga.eschool.service.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl implements StreamService {
    private final StreamRepository streamRepository;

    @Override
    public List<Stream> findAll(Long gradeLevelId) {
        Specification<Stream> spec = Specification.where(StreamSpecification.byGradeLevelId(gradeLevelId));
        return streamRepository.findAll(spec);
    }

    @Override
    public Stream save(Stream stream) {
        return streamRepository.save(stream);
    }

    @Override
    public Optional<Stream> findById(Long id) {
        return streamRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        streamRepository.deleteById(id);
    }
}
