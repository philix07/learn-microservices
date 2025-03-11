package com.felix.accounts.controller;

import com.felix.accounts.dto.AccountDTO;
import com.felix.accounts.dto.ResponseDTO;
import com.felix.accounts.service.iAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private iAccountService accountService;

  @GetMapping("account")
  ResponseEntity<AccountDTO> getByMobileNumber(@RequestParam String mobileNumber) {
    return ResponseEntity.ok(accountService.getAccountByMobileNumber(mobileNumber));
  }

  @GetMapping("account/{id}")
  ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(accountService.getAccountById(id));
  }

  @PostMapping("account")
  ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
    accountService.createAccounts(accountDTO);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(new ResponseDTO(
        HttpStatus.CREATED.value(),
        "New Account Successfully Created"
      ));
  }

  @PatchMapping("account/{id}")
  ResponseEntity<ResponseDTO> updateAccountById(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {
    accountService.updateAccount(id, accountDTO);
    return ResponseEntity.ok(new ResponseDTO(
      HttpStatus.OK.value(),
      "Account with id " + id + " updated successfully"
    ));
  }

  @DeleteMapping("account/{id}")
  ResponseEntity<ResponseDTO> deleteAccountById(@PathVariable Long id) {
    boolean isDeleted = accountService.deleteAccount(id);

    if (isDeleted) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(
        HttpStatus.OK.value(),
        "Account with id : " + id + " successfully deleted"
      ));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(
        HttpStatus.NOT_FOUND.value(),
        "Account with id : " + id + " not found"
      ));
    }
  }
}
