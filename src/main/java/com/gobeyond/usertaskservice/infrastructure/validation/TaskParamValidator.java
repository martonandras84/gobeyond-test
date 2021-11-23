package com.gobeyond.usertaskservice.infrastructure.validation;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import com.gobeyond.usertaskservice.infrastructure.validation.exception.UserTaskServiceException;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Aspect oriented class to validate {@link @ValidateTaskParams} annotated methods.
 * Checks if attributes are null.
 * Checks if first attribute is {@link TaskRequestDto} and is so then checks if task name is given.
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */

@Component
@Aspect
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskParamValidator {

  /**
   * Checks if attributes are null.
   * Checks if first attribute is {@link TaskRequestDto} and is so then checks if task name is given.
   *
   * @throws NullPointerException if any parameter is null
   * @throws UserTaskServiceException if task name is empty
   * @param joinPoint joinPoint
   * @return true if all validations passed
   */
  @Before(value = "@annotation(com.gobeyond.usertaskservice.infrastructure.validation.ValidateTaskParams)")
  public boolean validateTaskParams(JoinPoint joinPoint) {
    if (Arrays.stream(joinPoint.getArgs()).anyMatch(Objects::isNull)) {
      throw new NullPointerException("Parameter is null");
    }

    if (joinPoint.getArgs().length > 0
        && joinPoint.getArgs()[0] instanceof TaskRequestDto) {

      TaskRequestDto dto = (TaskRequestDto) joinPoint.getArgs()[0];
      if (StringUtils.isEmpty(dto.getName())) {
        throw new UserTaskServiceException(UserTaskServiceException.TASK_NAME_MISSING);
      }

    }

    return true;
  }
}
