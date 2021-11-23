package com.gobeyond.usertaskservice.domain.shared;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Abstract super class of entities containing audit fields
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@Getter
@MappedSuperclass
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

  @CreationTimestamp
  @Column(name = "CREATION_DATE")
  private LocalDateTime creationDate;

  @UpdateTimestamp
  @Column(name = "LAST_UPDATE_DATE")
  private LocalDateTime lastUpdateDate;

  @Version
  @Column(name = "VERSION")
  private Long version;
}
