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
@Table(name = "streams", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "grade_level_id"})})
public class Stream extends BaseModel {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_level_id", nullable = false, insertable = false, updatable = false)
    private GradeLevel gradeLevel;

    @Column(name = "grade_level_id", nullable = false)
    private Long gradeLevelId;
}
