package com.felix.accounts.service;

import com.felix.accounts.dto.AccountDTO;

public interface iAccountService {

  void createAccounts(AccountDTO accountDTO);

  AccountDTO getAccountByMobileNumber(String mobileNumber);

  AccountDTO getAccountById(Long id);

  void updateAccount(Long id, AccountDTO accountDTO);
}
