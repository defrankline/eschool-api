package com.kachinga.eschool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString
@MappedSuperclass
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, updatable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @JsonIgnore
    @Basic(optional = true)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @Basic(optional = true)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @CreatedBy
    @JsonIgnore
    @Basic(optional = true)
    @Column(name = "created_by")
    private Long createdBy = 1L;

    @LastModifiedBy
    @JsonIgnore
    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonIgnore
    @Column(name = "deleted_by")
    private Long deletedBy;

    @JsonIgnore
    @Column(name = "active")
    private Boolean active = true;
}
