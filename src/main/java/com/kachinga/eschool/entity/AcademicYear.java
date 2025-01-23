package com.kachinga.eschool.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "academic_years")
public class AcademicYear extends BaseModel {
    @NotNull(message = "name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "end date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull(message = "whether current or not required is required")
    @Column(name = "current", nullable = false)
    private Boolean current;

    @NotNull(message = "whether closed or not required is required")
    @Column(name = "closed", nullable = false)
    private Boolean closed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "school_id", nullable = false)
    @JsonIgnore
    private School school;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "previous_year_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"previous"})
    private AcademicYear previous;


    @Column(name = "previous_year_id")
    private Long previousYearId;
}
