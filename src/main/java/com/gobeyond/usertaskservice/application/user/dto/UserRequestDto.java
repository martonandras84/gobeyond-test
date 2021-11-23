package com.gobeyond.usertaskservice.application.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gobeyond.usertaskservice.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for User related REST endpoints
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserRequestDto {

  @JsonProperty(value = "username")
  private String userName;

  @JsonProperty(value = "first_name")
  private String firstName;

  @JsonProperty(value = "last_name")
  private String lastName;

  public static UserRequestDto of(User user) {
    return UserRequestDto.builder().userName(user.getUserName()).firstName(user.getFirstName()).lastName(
        user.getLastName()).build();
  }
}
