package com.felix.accounts.controller;

import com.felix.accounts.dto.AccountDTO;
import com.felix.accounts.dto.ResponseDTO;
import com.felix.accounts.service.iAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private iAccountService accountService;

  @GetMapping("account")
  ResponseEntity<AccountDTO> getByMobileNumber(@RequestParam String mobileNumber) {
    return ResponseEntity.ok(accountService.getAccountByMobileNumber(mobileNumber));
  }

  @GetMapping("account")
  ResponseEntity<AccountDTO> getByMobileNumber(@RequestParam Long id) {
    return ResponseEntity.ok(accountService.getAccountById(id));
  }

  @PostMapping("account")
  ResponseEntity<ResponseDTO> createAccount(@RequestBody AccountDTO accountDTO) {
    accountService.createAccounts(accountDTO);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(new ResponseDTO(
        HttpStatus.CREATED.value(),
        "New Account Successfully Created"
      ));
  }

  @PatchMapping("account/{id}")
  ResponseEntity<ResponseDTO> updateAccount(@RequestParam Long id, @RequestBody AccountDTO accountDTO) {
    accountService.updateAccount(id, accountDTO);
    return ResponseEntity.ok(new ResponseDTO(
      HttpStatus.OK.value(),
      "Account with id " + id + " updated successfully"
    ));
  }
}
