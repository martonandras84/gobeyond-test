package com.gobeyond.usertaskservice.application.user;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of interface {@link UserController}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RestController
@RequestMapping(path = "/api/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @Override
  @PostMapping()
  public ResponseEntity<UserRequestDto> createUser(@RequestBody UserRequestDto requestDto) {
    return ResponseEntity.of(userService.createUser(requestDto));
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<UserRequestDto> updateUser(@PathVariable("id") Long userId, @RequestBody UserRequestDto requestDto) {
    return ResponseEntity.of(userService.updateUser(requestDto, userId));
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<UserRequestDto> getUserInfo(@PathVariable("id") Long userId) {
    return ResponseEntity.of(userService.getUserInfo(userId));
  }

  @Override
  @GetMapping()
  public ResponseEntity<Page<UserRequestDto>> listAllUsers(Pageable pageable) {
    return ResponseEntity.ok(userService.listAllUsers(pageable));
  }
}