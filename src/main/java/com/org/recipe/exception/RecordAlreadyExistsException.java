package com.org.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecordAlreadyExistsException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
 
  public RecordAlreadyExistsException(String message) {
        super(message);
    }
}
