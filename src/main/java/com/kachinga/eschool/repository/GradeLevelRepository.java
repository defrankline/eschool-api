package com.kachinga.eschool.repository;

import com.kachinga.eschool.entity.GradeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeLevelRepository extends JpaRepository<GradeLevel, Long>,
        JpaSpecificationExecutor<GradeLevel> {

}
