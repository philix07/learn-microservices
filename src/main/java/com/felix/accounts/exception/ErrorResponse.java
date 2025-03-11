package com.felix.accounts.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

  private int status;
  private LocalDateTime timestamp;
  private String error;
  private String message;
  private String path;

}
