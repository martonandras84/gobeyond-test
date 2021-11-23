package com.gobeyond.usertaskservice.domain.model.task;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import com.gobeyond.usertaskservice.domain.shared.AbstractEntity;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Entity of task object
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@Builder
@Getter
@Setter
@Table(name = "TASK", indexes = {@Index(name = "TASK_U1", columnList = "TASK_ID", unique = true),
    @Index(name = "TASK_N1", columnList = "DATE_TIME, STATUS"),
    @Index(name = "TASK_N2", columnList = "USER_ID")})
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends AbstractEntity {

  @Id
  @Column(name = "TASK_ID", nullable = false, unique = true)
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @Column(name = "USER_ID", nullable = false)
  private Long userId;

  @Column(name = "TASK_NAME", nullable = false, length = 100)
  private String taskName;

  @Column(name = "DESCRIPTION", length = 2000)
  private String description;

  @Column(name = "DATE_TIME")
  private LocalDateTime dateTime;

  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  private TaskStatus status;

  public static Task of(Long userId, TaskRequestDto dto, TaskStatus status) {
    return Task.builder().userId(userId).taskName(dto.getName()).description(dto.getDescription())
        .dateTime(dto.getDateTime()).status(status).build();
  }

  public boolean merge(Task other) {
    if (other == null) {
      return false;
    }

    if (!new EqualsBuilder()
        .append(this.taskName, other.taskName)
        .append(this.description, other.description)
        .append(this.dateTime, other.dateTime)
        .isEquals()) {

      this.taskName = other.taskName;
      this.description = other.description;
      this.dateTime = other.dateTime;
      this.status = other.status;

      return true;
    }

    return false;
  }
}
