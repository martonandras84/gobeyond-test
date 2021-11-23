package com.gobeyond.usertaskservice.infrastructure.validation;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import com.gobeyond.usertaskservice.infrastructure.validation.exception.UserTaskServiceException;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test class for {@link TaskParamValidator}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskParamValidatorTest {

  @Mock
  JoinPoint joinPoint;

  @InjectMocks
  TaskParamValidator validator;

  @Test(expected = NullPointerException.class)
  public void testNullParam() {
    //given
    Object[] nullArray = new Object[]{1L, null, null};
    Mockito.when(joinPoint.getArgs()).thenReturn(nullArray);

    //when
    validator.validateTaskParams(joinPoint);
  }

  @Test
  public void testEmptyParam() {
    //given
    Object[] nullArray = new Object[]{};
    Mockito.when(joinPoint.getArgs()).thenReturn(nullArray);

    //when
    boolean actual = validator.validateTaskParams(joinPoint);

    //then
    Assert.assertTrue(actual);
  }

  @Test
  public void testNoUserRequestDto() {
    //given
    Object[] args = new Object[]{1L};
    Mockito.when(joinPoint.getArgs()).thenReturn(args);

    //when
    boolean actual = validator.validateTaskParams(joinPoint);

    //then
    Assert.assertTrue(actual);
  }

  @Test(expected = UserTaskServiceException.class)
  public void testEmptyTaskName() {
    //given
    TaskRequestDto dto = TaskRequestDto.builder().id(1L).name("").description("description")
        .dateTime(LocalDateTime.now()).status(TaskStatus.PENDING).build();
    Object[] args = new Object[]{dto};
    Mockito.when(joinPoint.getArgs()).thenReturn(args);

    //when
    validator.validateTaskParams(joinPoint);
  }

  @Test
  public void testValidUserName() {
    //given
    TaskRequestDto dto = TaskRequestDto.builder().id(1L).name("taskName").description("description")
        .dateTime(LocalDateTime.now()).status(TaskStatus.PENDING).build();
    Object[] args = new Object[]{dto};
    Mockito.when(joinPoint.getArgs()).thenReturn(args);

    //when
    boolean actual = validator.validateTaskParams(joinPoint);

    //then
    Assert.assertTrue(actual);
  }
}
