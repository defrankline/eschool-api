package com.kachinga.eschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "grade_levels")
public class GradeLevel extends BaseModel {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "education_level_id", nullable = false,insertable = false,updatable = false)
    private EducationLevel educationLevel;

    @Column(name = "education_level_id", nullable = false)
    private Long educationLevelId;
}
