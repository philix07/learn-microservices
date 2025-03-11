package com.felix.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

// @MappedSuperclass ensures that its fields (createdAt, createdBy, etc.)
// are mapped to child entities but does not create a separate table.
@MappedSuperclass
@Data
public class BaseEntity {

  @Column(updatable = false)
  private LocalDateTime createdAt;

  @Column(updatable = false)
  private String createdBy;

  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @Column(insertable = false)
  private String updatedBy;

}
