package com.gobeyond.usertaskservice.application.user;

import static org.mockito.Mockito.when;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test class for {@link UserControllerImpl}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  @Mock
  UserServiceImpl userService;

  @InjectMocks
  UserControllerImpl userController;

  @Test
  public void testCreateUserStatusOk() {
    //given
    UserRequestDto dto = createUserDto();

    when(userService.createUser(Mockito.any())).thenReturn(Optional.of(dto));

    //when
    ResponseEntity<UserRequestDto> actual = userController.createUser(dto);

    //then
    Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  @Test
  public void testUpdateUserStatusOk() {
    //given
    UserRequestDto dto = createUserDto();
    when(userService.updateUser(Mockito.any(), Mockito.any())).thenReturn(Optional.of(dto));

    //when
    ResponseEntity<UserRequestDto> actual = userController.updateUser(1L, dto);

    //then
    Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  @Test
  public void testGetUserInfoStatusOk() {
    //given
    UserRequestDto dto = createUserDto();
    when(userService.getUserInfo(Mockito.any())).thenReturn(Optional.of(dto));

    //when
    ResponseEntity<UserRequestDto> actual = userController.getUserInfo(1L);

    //then
    Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  private UserRequestDto createUserDto() {
    return new UserRequestDto("userName", "firstName", "lastName");
  }
}
