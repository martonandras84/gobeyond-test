package com.gobeyond.usertaskservice.application.task;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * RestController interface for User related endpoints
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public interface TaskController {

  /**
   * Rest controller to create new task
   *
   * @param userId user id
   * @param dto {@link TaskRequestDto} structure of input json
   * @return {@link ResponseEntity} of {@link TaskRequestDto} that is created
   */
  ResponseEntity<TaskRequestDto> createTask(Long userId, TaskRequestDto dto);

  /**
   * Rest controller to update task
   *
   * @param userId user id
   * @param taskId task id
   * @param dto {@link TaskRequestDto} structure of input json
   * @return {@link ResponseEntity} of {@link TaskRequestDto} that is updated
   */
  ResponseEntity<TaskRequestDto> updateTask(Long userId, Long taskId, TaskRequestDto dto);

  /**
   * Rest controller to delete task
   *
   * @param userId user id
   * @param taskId task id
   * @return {@link ResponseEntity} of {@link TaskRequestDto} that is deleted from database
   */
  ResponseEntity<TaskRequestDto> deleteTask(Long userId, Long taskId);

  /**
   * Rest controller to get details of a specific task
   *
   * @param userId user id
   * @param taskId task id
   * @return {@link ResponseEntity} of {@link TaskRequestDto} to be queried
   */
  ResponseEntity<TaskRequestDto> getTaskInfo(Long userId, Long taskId);

  /**
   * Rest controller to list details all tasks of a user
   *
   * @param userId user id
   * @param pageable pageable
   * @return {@link ResponseEntity} of {@link TaskRequestDto}s that belong to the specified user
   */
  ResponseEntity<Page<TaskRequestDto>> listAllTasksForAUser(Long userId, Pageable pageable);
}
