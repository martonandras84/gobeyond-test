package com.gobeyond.usertaskservice.domain.model.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link User} entity
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public interface UserRepository extends CrudRepository<User, Long> {

  /**
   * Query a {@link User} record by id
   *
   * @param id user id
   * @return {@link Optional} of {@link User}
   */
  Optional<User> findById(Long id);

  /**
   * Query a {@link User} record by user name
   *
   * @param userName user name
   * @return {@link Optional} of {@link User}
   */
  Optional<User> findByUserName(String userName);

  /**
   * Query all {@link User} records
   *
   * @return {@link Page} of {@link User}s
   */
  Page<User> findAll(Pageable pageable);
}
