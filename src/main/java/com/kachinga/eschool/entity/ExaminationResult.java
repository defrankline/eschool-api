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
@Table(name = "examination_results")
public class ExaminationResult extends BaseModel {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examination_subject_id", nullable = false)
    private ExaminationSubject examinationSubject;

    @Column(name = "marks", nullable = false)
    private Double marks;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExamResultStatus status;
}
