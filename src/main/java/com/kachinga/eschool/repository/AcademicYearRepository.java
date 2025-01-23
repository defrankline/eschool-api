package com.kachinga.eschool.repository;

import com.kachinga.eschool.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long>, JpaSpecificationExecutor<AcademicYear> {

}
