package com.felix.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDTO {

  private String accountType;

  private String branchAddress;

  private String customerName;

  private String email;

  private String mobileNumber;


}
