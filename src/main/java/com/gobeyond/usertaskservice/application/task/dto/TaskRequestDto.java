package com.gobeyond.usertaskservice.application.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gobeyond.usertaskservice.domain.model.task.Task;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for Task related REST endpoints
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskRequestDto {

  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "description")
  private String description;

  @JsonProperty(value = "date_time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dateTime;

  @JsonProperty(value = "status")
  private TaskStatus status;

  public static TaskRequestDto of(Task task) {
    return TaskRequestDto.builder().id(task.getId()).name(task.getTaskName()).description(task.getDescription())
        .dateTime(task.getDateTime()).status(task.getStatus()).build();
  }
}
