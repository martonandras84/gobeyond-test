package com.gobeyond.usertaskservice.domain.model.user;

import com.gobeyond.usertaskservice.application.user.dto.UserRequestDto;
import com.gobeyond.usertaskservice.domain.shared.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Entity of user object
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@Getter
@Table(name = "USER",
    indexes = {@Index(name = "USER_U1", columnList = "USER_ID", unique = true),
    @Index(name = "USER_U2", columnList = "USER_NAME", unique = true)})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends AbstractEntity {

  @Id
  @Column(name = "USER_ID", nullable = false, unique = true)
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @Column(name = "USER_NAME", nullable = false, unique = true, length = 100)
  private String userName;

  @Column(name = "FIRST_NAME", length = 100)
  private String firstName;

  @Column(name = "LAST_NAME", length = 100)
  private String lastName;

  public static User of(UserRequestDto dto) {
    UserBuilder builder = builder();
    builder.userName(dto.getUserName()).firstName(dto.getFirstName()).lastName(dto.getLastName());
    return builder.build();
  }

  public boolean merge(User other) {
    if (other == null) {
      return false;
    }

    if (!new EqualsBuilder()
        .append(this.firstName, other.firstName)
        .append(this.lastName, other.lastName)
        .isEquals()) {

      this.firstName = other.firstName;
      this.lastName = other.lastName;

      return true;
    }

    return false;
  }
}
