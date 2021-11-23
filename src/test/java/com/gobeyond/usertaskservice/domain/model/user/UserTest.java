package com.gobeyond.usertaskservice.domain.model.user;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test class for {@link User}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public class UserTest {

  @Test
  public void testOf() {
    //given
    UserRequestDto dto = createUserDto();
    User userExpected = createUser();

    //when
    User userActual = User.of(dto);

    //then
    SoftAssertions softAssert = new SoftAssertions();
    softAssert.assertThat(userActual.getId()).isNull();
    softAssert.assertThat(userActual.getUserName()).isEqualTo(userExpected.getUserName());
    softAssert.assertThat(userActual.getFirstName()).isEqualTo(userExpected.getFirstName());
    softAssert.assertThat(userActual.getLastName()).isEqualTo(userExpected.getLastName());
    softAssert.assertAll();
  }

  @Test
  public void testMergeOtherNull() {
    //given
    User user = createUser();

    //when
    boolean actual = user.merge(null);

    //then
    Assert.assertFalse(actual);
  }

  @Test
  public void testMergeWithSameValues() {
    //given
    User userOrig = createUser();
    User userNew = createUser();

    //when
    boolean actual = userOrig.merge(userNew);

    //then
    Assert.assertFalse(actual);
  }

  @Test
  public void testMergeWithDiffFirstName() {
    //given
    User userOrig = createUser();
    User userDiffFirstName = User.builder().userName("userName").firstName("diffFirstName").lastName("lastName")
        .build();

    //when
    boolean actual = userOrig.merge(userDiffFirstName);

    //then
    Assert.assertTrue(actual);
  }

  @Test
  public void testMergeWithDiffLastName() {
    //given
    User userOrig = createUser();
    User userDiffFirstName = User.builder().userName("userName").firstName("firstName").lastName("diffLastName")
        .build();

    //when
    boolean actual = userOrig.merge(userDiffFirstName);

    //then
    Assert.assertTrue(actual);
  }

  @Test
  public void testMergeWithDiffNames() {
    //given
    User userOrig = createUser();
    User userDiffFirstName = User.builder().userName("userName").firstName("diffFirstName").lastName("diffLastName")
        .build();

    //when
    boolean actual = userOrig.merge(userDiffFirstName);

    //then
    Assert.assertTrue(actual);
  }

  private User createUser() {
    return User.builder().userName("userName").firstName("firstName").lastName("lastName").build();
  }

  private UserRequestDto createUserDto() {
    return new UserRequestDto("userName", "firstName", "lastName");
  }
}
