package com.gobeyond.usertaskservice.infrastructure.validation;

import com.gobeyond.usertaskservice.infrastructure.validation.exception.UserTaskServiceException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler using {@link ControllerAdvice}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserTaskServiceException.class)
  public ResponseEntity<Object> handleUserTaskServiceException(UserTaskServiceException ex) {

    Map<String, Object> responseBody = new LinkedHashMap<>();
    responseBody.put("message", ex.getMessage());

    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
  }
}
