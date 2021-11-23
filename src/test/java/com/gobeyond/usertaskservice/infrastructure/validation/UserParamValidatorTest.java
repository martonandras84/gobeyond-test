package com.gobeyond.usertaskservice.infrastructure.validation;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import com.gobeyond.usertaskservice.infrastructure.validation.exception.UserTaskServiceException;
import org.aspectj.lang.JoinPoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test class for {@link UserParamValidator}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class UserParamValidatorTest {

  @Mock
  JoinPoint joinPoint;

  @InjectMocks
  UserParamValidator validator;

  @Test(expected = NullPointerException.class)
  public void testNullParam() {
    //given
    Object[] nullArray = new Object[]{1L, null, null};
    Mockito.when(joinPoint.getArgs()).thenReturn(nullArray);

    //when
    validator.validateUserParams(joinPoint);
  }

  @Test
  public void testEmptyParam() {
    //given
    Object[] nullArray = new Object[]{};
    Mockito.when(joinPoint.getArgs()).thenReturn(nullArray);

    //when
    boolean actual = validator.validateUserParams(joinPoint);

    //then
    Assert.assertTrue(actual);
  }

  @Test
  public void testNoUserRequestDto() {
    //given
    Object[] args = new Object[]{1L};
    Mockito.when(joinPoint.getArgs()).thenReturn(args);

    //when
    boolean actual = validator.validateUserParams(joinPoint);

    //then
    Assert.assertTrue(actual);
  }

  @Test(expected = UserTaskServiceException.class)
  public void testEmptyUserName() {
    //given
    UserRequestDto dto = new UserRequestDto("", "firstName", "lastName");
    Object[] args = new Object[]{dto};
    Mockito.when(joinPoint.getArgs()).thenReturn(args);

    //when
    validator.validateUserParams(joinPoint);
  }

  @Test
  public void testValidUserName() {
    //given
    UserRequestDto dto = new UserRequestDto("userName", "firstName", "lastName");
    Object[] args = new Object[]{dto};
    Mockito.when(joinPoint.getArgs()).thenReturn(args);

    //when
    boolean actual = validator.validateUserParams(joinPoint);

    //then
    Assert.assertTrue(actual);
  }
}