package com.felix.accounts.mapper;


import com.felix.accounts.dto.AccountDTO;
import com.felix.accounts.entity.Account;
import com.felix.accounts.entity.Customer;

public class AccountMapper {

  public AccountDTO mapEntityToResponse(Account account, Customer customer) {
    return new AccountDTO(
      account.getAccountNumber(),
      account.getAccountType(),
      account.getBranchAddress(),
      customer.getName(),
      customer.getEmail(),
      customer.getMobileNumber()
    );
  }

}
