package com.gobeyond.usertaskservice.application.user;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import com.gobeyond.usertaskservice.domain.model.user.User;
import com.gobeyond.usertaskservice.domain.model.user.UserRepository;
import com.gobeyond.usertaskservice.infrastructure.validation.ValidateUserParams;
import com.gobeyond.usertaskservice.infrastructure.validation.exception.UserTaskServiceException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation of {@link UserService}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  @ValidateUserParams
  public Optional<UserRequestDto> createUser(UserRequestDto requestDto) {

    Optional<User> existingUserOpt = userRepository.findByUserName(requestDto.getUserName());
    if (existingUserOpt.isPresent()) {
      return Optional.of(UserRequestDto.of(existingUserOpt.get()));
    }

    User newUser = User.of(requestDto);
    userRepository.save(newUser);
    return Optional.of(UserRequestDto.of(newUser));
  }

  @Override
  @ValidateUserParams
  @Transactional
  public Optional<UserRequestDto> updateUser(UserRequestDto requestDto, Long userId) {

    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserTaskServiceException(String.format(UserTaskServiceException.USER_NOT_FOUND, userId));
    }

    User existingUser = userOptional.get();
    existingUser.merge(User.of(requestDto));

    return Optional.of(UserRequestDto.of(existingUser));
  }

  @Override
  @ValidateUserParams
  public Optional<UserRequestDto> getUserInfo(Long userId) {

    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserTaskServiceException(String.format(UserTaskServiceException.USER_NOT_FOUND, userId));
    }

    return Optional.of(UserRequestDto.of(userOptional.get()));
  }

  @Override
  public Page<UserRequestDto> listAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(UserRequestDto::of);
  }
}