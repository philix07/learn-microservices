package com.felix.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDTO {

  private Long accountNumber;

  @NotEmpty(message = "accountType must be filled")
  private String accountType;

  @NotEmpty(message = "branchAddress must be filled")
  private String branchAddress;

  @NotEmpty(message = "name must be filled")
  @Size(min = 3, message = "name must be at least 3 character long")
  private String name;

  @NotEmpty(message = "email must be filled")
  @Email(message = "Email must be in valid format")
  private String email;

  @NotEmpty(message = "email must be filled")
  @Pattern(regexp = "^0[0-9]{9,14}$", message = "Mobile number must start with 0 and be 10-15 digits long")
  private String mobileNumber;


}
