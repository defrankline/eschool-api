package com.kachinga.eschool.repository;

import com.kachinga.eschool.entity.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long>,
        JpaSpecificationExecutor<EducationLevel> {

}
