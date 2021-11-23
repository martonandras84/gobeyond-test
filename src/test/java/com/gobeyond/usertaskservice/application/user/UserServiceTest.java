package com.gobeyond.usertaskservice.application.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import com.gobeyond.usertaskservice.domain.model.user.User;
import com.gobeyond.usertaskservice.domain.model.user.UserRepository;
import com.gobeyond.usertaskservice.infrastructure.validation.exception.UserTaskServiceException;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test class for {@link UserServiceImpl}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImpl userService;

  @Test(expected = NullPointerException.class)
  public void testCreateUserParamNull() {
    userService.createUser(null);
  }

  @Test
  public void testCreateUserSave() {
    //given
    UserRequestDto userDto = createUserDto();
    when(userRepository.findByUserName(Mockito.any())).thenReturn(Optional.empty());
    when(userRepository.save(Mockito.any())).then(param -> param.getArgument(0));

    //when
    userService.createUser(userDto);

    //then
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  public void testCreateUser_userAlreadyExists() {
    //given
    User user = createUser();
    UserRequestDto userDto = createUserDto();
    when(userRepository.findByUserName(Mockito.any())).thenReturn(Optional.of(user));

    //when
    Optional<UserRequestDto> actual = userService.createUser(userDto);

    //then
    verify(userRepository, times(0)).save(any(User.class));
    Assert.assertTrue(actual.isPresent());
    Assert.assertEquals(user.getUserName(), actual.get().getUserName());
    Assert.assertEquals(user.getFirstName(), actual.get().getFirstName());
    Assert.assertEquals(user.getLastName(), actual.get().getLastName());
  }

  @Test(expected = UserTaskServiceException.class)
  public void testUpdateUser_userNotFound() {
    //given
    UserRequestDto userDto = createUserDto();
    when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

    //when
    userService.updateUser(userDto, 1L);
  }

  @Test
  public void testUpdateUserMerged() {
    //given
    User existingUser = createUser();
    UserRequestDto userDto = new UserRequestDto("userName", "updatedFirstName", "updatedLastName");

    when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(existingUser));

    //when
    Optional<UserRequestDto> actual = userService.updateUser(userDto, 1L);

    //then
    Assert.assertTrue(actual.isPresent());
    Assert.assertEquals(userDto.getFirstName(), actual.get().getFirstName());
    Assert.assertEquals(userDto.getLastName(), actual.get().getLastName());
  }

  @Test(expected = UserTaskServiceException.class)
  public void testGetUserInfo_userNotFound() {
    //given
    when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

    //when
    userService.getUserInfo(1L);
  }

  @Test
  public void testGetUserInfoSuccess() {
    //given
    User existingUser = createUser();

    when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(existingUser));

    //when
    Optional<UserRequestDto> actual = userService.getUserInfo(1L);

    //then
    Assert.assertTrue(actual.isPresent());
    Assert.assertEquals(existingUser.getFirstName(), actual.get().getFirstName());
    Assert.assertEquals(existingUser.getLastName(), actual.get().getLastName());
  }



  private User createUser() {
    return User.builder().userName("userName").firstName("firstName").lastName("lastName").build();
  }

  private UserRequestDto createUserDto() {
    return new UserRequestDto("userName", "firstName", "lastName");
  }
}
