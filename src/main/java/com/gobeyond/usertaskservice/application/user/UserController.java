package com.gobeyond.usertaskservice.application.user;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * RestController interface for User related endpoints
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public interface UserController {

  /**
   * Rest controller to create new user
   *
   * @param requestDto {@link UserRequestDto} structure of input json
   * @return {@link ResponseEntity} of {@link UserRequestDto} that is created
   */
  ResponseEntity<UserRequestDto> createUser(UserRequestDto requestDto);

  /**
   * Rest controller to update user
   *
   * @param userId user id to be updated
   * @param requestDto {@link UserRequestDto} structure of input json
   * @return {@link ResponseEntity} of {@link UserRequestDto} that is updated
   */
  ResponseEntity<UserRequestDto> updateUser(Long userId, UserRequestDto requestDto);

  /**
   * Rest controller to get details of a specific user
   *
   * @param userId user id to be queried
   * @return {@link ResponseEntity} of {@link UserRequestDto} that is requested
   */
  ResponseEntity<UserRequestDto> getUserInfo(Long userId);

  /**
   * Rest controller to list details of all users
   *
   * @param pageable pageable
   * @return {@link ResponseEntity} of all {@link UserRequestDto}s
   */
  ResponseEntity<Page<UserRequestDto>> listAllUsers(Pageable pageable);
}
