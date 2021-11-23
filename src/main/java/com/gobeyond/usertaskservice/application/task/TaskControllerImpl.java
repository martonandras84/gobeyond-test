package com.gobeyond.usertaskservice.application.task;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of interface {@link TaskController}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RestController
@RequestMapping("/api/user/{user_id}/task")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TaskControllerImpl implements TaskController {

  private TaskService taskService;

  @Override
  @PostMapping()
  public ResponseEntity<TaskRequestDto> createTask(@PathVariable("user_id") Long userId,
      @RequestBody TaskRequestDto dto) {
    return ResponseEntity.of(taskService.createTask(dto, userId));
  }

  @Override
  @PutMapping("/{task_id}")
  public ResponseEntity<TaskRequestDto> updateTask(@PathVariable("user_id") Long userId,
      @PathVariable("task_id") Long taskId,
      @RequestBody TaskRequestDto dto) {
    return ResponseEntity.of(taskService.updateTask(dto, userId, taskId));
  }

  @Override
  @DeleteMapping("/{task_id}")
  public ResponseEntity<TaskRequestDto> deleteTask(@PathVariable("user_id") Long userId,
      @PathVariable("task_id") Long taskId) {
    return ResponseEntity.of(taskService.deleteTask(userId, taskId));
  }

  @Override
  @GetMapping("/{task_id}")
  public ResponseEntity<TaskRequestDto> getTaskInfo(@PathVariable("user_id") Long userId,
      @PathVariable("task_id") Long taskId) {
    return ResponseEntity.of(taskService.getTaskInfo(userId, taskId));
  }

  @Override
  @GetMapping()
  public ResponseEntity<Page<TaskRequestDto>> listAllTasksForAUser(@PathVariable("user_id") Long userId,
      Pageable pageable) {
    return ResponseEntity.ok(taskService.listAllTasksForAUser(userId, pageable));
  }
}