package com.gobeyond.usertaskservice.application.task;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for Task related endpoints
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public interface TaskService {

  /**
   * Create new task
   *
   * @param dto {@link TaskRequestDto} structure of input json
   * @param userId user id
   * @return {@link Optional} of {@link TaskRequestDto} that is created
   */
  Optional<TaskRequestDto> createTask(TaskRequestDto dto, Long userId);

  /**
   * Update task
   *
   * @param dto {@link TaskRequestDto} structure of input json
   * @param userId user id
   * @param taskId task id
   * @return {@link Optional} of {@link TaskRequestDto} that is updated
   */
  Optional<TaskRequestDto> updateTask(TaskRequestDto dto, Long userId, Long taskId);

  /**
   * Delete task
   *
   * @param userId user id
   * @param taskId task id
   * @return {@link Optional} of {@link TaskRequestDto} that is deleted
   */
  Optional<TaskRequestDto> deleteTask(Long userId, Long taskId);

  /**
   * Get task details
   *
   * @param userId user id
   * @param taskId task id
   * @return {@link Optional} of {@link TaskRequestDto} that is requested
   */
  Optional<TaskRequestDto> getTaskInfo(Long userId, Long taskId);

  /**
   * Get details of all tasks of a user
   *
   * @param userId user id
   * @return {@link Page} of {@link TaskRequestDto}s that are requested
   */
  Page<TaskRequestDto> listAllTasksForAUser(Long userId, Pageable pageable);
}
