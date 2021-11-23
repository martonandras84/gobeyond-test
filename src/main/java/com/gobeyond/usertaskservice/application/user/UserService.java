package com.gobeyond.usertaskservice.application.user;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for User related endpoints
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public interface UserService {

  /**
   * Create new user
   *
   * @param requestDto {@link UserRequestDto} structure of input json
   * @return {@link Optional} of {@link UserRequestDto} that is created
   */
  Optional<UserRequestDto> createUser(UserRequestDto requestDto);

  /**
   * Update user
   *
   * @param requestDto {@link UserRequestDto} structure of input json
   * @param userId user id
   * @return {@link Optional} of {@link UserRequestDto} that is updated
   */
  Optional<UserRequestDto> updateUser(UserRequestDto requestDto, Long userId);

  /**
   * Get user details
   *
   * @param userId user id
   * @return {@link Optional} of {@link UserRequestDto} that is requested
   */
  Optional<UserRequestDto> getUserInfo(Long userId);

  /**
   * Get details of all users
   *
   * @return {@link Page} of {@link UserRequestDto}s that are requested
   */
  Page<UserRequestDto> listAllUsers(Pageable pageable);
}
