package com.gobeyond.usertaskservice.infrastructure.validation.exception;

/**
 * Custom runtime exception thrown by validation errors.
 * Static error messages included.
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public class UserTaskServiceException extends RuntimeException {

  public final static String USER_NOT_FOUND = "User not found with id %s";
  public final static String USER_NAME_MISSING = "User name missing";

  public final static String TASK_NOT_FOUND = "Task not found with id %s";
  public final static String TASK_NOT_BELONG_TO_USER = "User (id %s) not have such task (id %s)";
  public final static String TASK_NAME_MISSING = "Task name missing";

  public UserTaskServiceException(String errorMessage) {
    super(errorMessage);
  }
}
