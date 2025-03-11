package com.felix.accounts.service.impl;

import com.felix.accounts.dto.AccountDTO;
import com.felix.accounts.entity.Account;
import com.felix.accounts.entity.Customer;
import com.felix.accounts.exception.DuplicateCustomerIDException;
import com.felix.accounts.exception.ResourceNotFoundException;
import com.felix.accounts.repository.AccountRepository;
import com.felix.accounts.repository.CustomerRepository;
import com.felix.accounts.service.iAccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements iAccountService {

  private AccountRepository accountRepository;
  private CustomerRepository customerRepository;

  @Override
  public void createAccounts(AccountDTO accountDTO) {

    // check if the phone number is already exists within the database
    if (customerRepository.findByMobileNumber(accountDTO.getMobileNumber()).isPresent()) {
      throw new DuplicateCustomerIDException("User with Mobile Number : " + accountDTO.getMobileNumber() + " is already exists");
    }

    // creating new customer instance
    Customer customer = new Customer();
    customer.setName(accountDTO.getName());
    customer.setMobileNumber(accountDTO.getMobileNumber());
    customer.setEmail(accountDTO.getEmail());

    customer.setCreatedAt(LocalDateTime.now());
    customer.setCreatedBy("Anonymous");
    Customer savedCustomer = customerRepository.save(customer);

    // generate random number for account number property
    SecureRandom secureRandom = new SecureRandom();
    long randomAccNumber = 1000000000L + (long) (secureRandom.nextDouble() * 9000000000L);

    // creating new accounts instance
    Account account = new Account();
    account.setCustomerId(savedCustomer.getCustomerId());
    account.setAccountNumber(randomAccNumber);
    account.setAccountType(accountDTO.getAccountType());
    account.setBranchAddress(accountDTO.getBranchAddress());

    account.setCreatedAt(LocalDateTime.now());
    account.setCreatedBy("Anonymous");
    accountRepository.save(account);

  }

  @Override
  public AccountDTO getAccountByMobileNumber(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
      () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
    );

    Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
      () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
    );

    return new AccountDTO(
      account.getAccountNumber(),
      account.getAccountType(),
      account.getBranchAddress(),
      customer.getName(),
      customer.getEmail(),
      customer.getMobileNumber()
    );
  }

  @Override
  public AccountDTO getAccountById(Long id) {
    Customer customer = customerRepository.findById(id).orElseThrow(
      () -> new ResourceNotFoundException("Customer", "uid", id.toString())
    );

    Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
      () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
    );

    return new AccountDTO(
      account.getAccountNumber(),
      account.getAccountType(),
      account.getBranchAddress(),
      customer.getName(),
      customer.getEmail(),
      customer.getMobileNumber()
    );
  }

  @Transactional
  public void updateAccount(Long id, AccountDTO accountDTO) {

    Customer customer = customerRepository.findById(id)
      .map(
        updatedCustomer -> {
          updatedCustomer.setName(accountDTO.getName());
          updatedCustomer.setEmail(accountDTO.getEmail());
          return customerRepository.save(updatedCustomer);
        }
      )
      .orElseThrow(
        () -> new ResourceNotFoundException("Customer", "uid : ", id.toString())
      );

    Account account = accountRepository.findByCustomerId(customer.getCustomerId())
      .map(
        newAccount -> {
          // the only field that can be updated within Account entity is AccountType and BranchAddress
          newAccount.setAccountType(accountDTO.getAccountType());
          newAccount.setBranchAddress(accountDTO.getBranchAddress());
          return accountRepository.save(newAccount);
        }
      )
      .orElseThrow(
        () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
      );

  }

  @Override
  @Transactional
  public boolean deleteAccount(Long customerId) {
    if (customerRepository.existsById(customerId)) {
      customerRepository.deleteById(customerId);
      accountRepository.deleteByCustomerId(customerId);
      return true;
    }
    return false;
  }

}
