package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.security.UserContextService;
import com.kachinga.eschool.dto.LoggedInUserDto;
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
    private final UserContextService userContextService;

    @Override
    public List<Stream> findAll(Long gradeLevelId) {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        Long schoolId = currentUser.getSchool().getId();
        Specification<Stream> spec = Specification.where(StreamSpecification.findAll(schoolId,gradeLevelId));
        return streamRepository.findAll(spec);
    }

    @Override
    public Stream save(Stream stream) {
        LoggedInUserDto currentUser = userContextService.getCurrentUser();
        Long schoolId = currentUser.getSchool().getId();
        stream.setSchoolId(schoolId);
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
