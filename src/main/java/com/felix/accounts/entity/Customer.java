package com.felix.accounts.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Customer extends BaseEntity {

  private Long customerId;
  private String name;
  private String email;
  private String mobileNumber;


}
