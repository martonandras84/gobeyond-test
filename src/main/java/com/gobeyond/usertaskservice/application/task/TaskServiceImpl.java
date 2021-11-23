package com.gobeyond.usertaskservice.application.task;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import com.gobeyond.usertaskservice.domain.model.user.User;
import com.gobeyond.usertaskservice.domain.model.user.UserRepository;
import com.gobeyond.usertaskservice.infrastructure.validation.ValidateTaskParams;
import com.gobeyond.usertaskservice.domain.model.task.Task;
import com.gobeyond.usertaskservice.domain.model.task.TaskRepository;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import com.gobeyond.usertaskservice.infrastructure.validation.exception.UserTaskServiceException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation of{@link TaskService}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskServiceImpl implements TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  @ValidateTaskParams
  public Optional<TaskRequestDto> createTask(TaskRequestDto dto, Long userId) {

    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserTaskServiceException(String.format(UserTaskServiceException.USER_NOT_FOUND, userId));
    }

    Task task = Task.of(userId, dto, TaskStatus.PENDING);
    taskRepository.save(task);
    return Optional.of(TaskRequestDto.of(task));
  }

  @Override
  @ValidateTaskParams
  @Transactional
  public Optional<TaskRequestDto> updateTask(TaskRequestDto dto, Long userId, Long taskId) {

    Optional<Task> taskOptional = taskRepository.findById(taskId);
    validateTask(taskOptional, userId, taskId);

    Task existingTask = taskOptional.get();
    existingTask.merge(Task.of(userId, dto, TaskStatus.PENDING));

    return Optional.of(TaskRequestDto.of(existingTask));
  }

  @Override
  @ValidateTaskParams
  public Optional<TaskRequestDto> deleteTask(Long userId, Long taskId) {

    Optional<Task> taskOptional = taskRepository.findById(taskId);

    validateTask(taskOptional, userId, taskId);

    taskRepository.delete(taskOptional.get());
    return taskOptional.map(TaskRequestDto::of);
  }

  @Override
  @ValidateTaskParams
  public Optional<TaskRequestDto> getTaskInfo(Long userId, Long taskId) {

    Optional<Task> taskOptional = taskRepository.findById(taskId);

    validateTask(taskOptional, userId, taskId);

    return taskOptional.map(TaskRequestDto::of);
  }

  @Override
  @ValidateTaskParams
  public Page<TaskRequestDto> listAllTasksForAUser(Long userId, Pageable pageable) {
    return taskRepository.findAllByUserId(userId, pageable).map(TaskRequestDto::of);
  }

  protected boolean validateTask(Optional<Task> taskOptional, Long userId, Long taskId) {
    if (taskOptional.isEmpty()) {
      throw new UserTaskServiceException(String.format(UserTaskServiceException.TASK_NOT_FOUND, taskId));
    }

    if (!userId.equals(taskOptional.get().getUserId())) {
      throw new UserTaskServiceException(String.format(UserTaskServiceException.TASK_NOT_BELONG_TO_USER, userId, taskId));
    }

    return true;
  }
}
