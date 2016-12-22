package com.savorgames.api.v1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 404
 * @author Haozi
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
  
  private static final long serialVersionUID = -1331019910026642588L;
  
  private String message;

  public NotFoundException() {

  }

  public NotFoundException(String message) {
      this.message = message;
  }

  @Override
  public String getMessage() {
      return message;
  }
}
