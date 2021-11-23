package com.gobeyond.usertaskservice.application.task;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import com.gobeyond.usertaskservice.domain.model.task.Task;
import com.gobeyond.usertaskservice.domain.model.task.TaskRepository;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import com.gobeyond.usertaskservice.infrastructure.validation.exception.UserTaskServiceException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test class for {@link TaskServiceImpl}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

  @Mock
  TaskRepository taskRepository;

  @InjectMocks
  TaskServiceImpl taskService;

  @Test(expected = NullPointerException.class)
  public void testCreateTaskParamNull() {
    taskService.createTask(null, null);
  }

  @Test
  public void testCreateTaskSave() {
    //given
    TaskRequestDto taskDto = createTaskDto();
    when(taskRepository.save(Mockito.any())).then(param -> param.getArgument(0));

    //when
    taskService.createTask(taskDto, 1L);

    //then
    verify(taskRepository, times(1)).save(any(Task.class));
  }

  @Test(expected = UserTaskServiceException.class)
  public void testUpdateTask_taskNotFound() {
    //given
    TaskRequestDto taskDto = createTaskDto();
    when(taskRepository.findById(Mockito.any())).thenReturn(Optional.empty());

    //when
    taskService.updateTask(taskDto, 1L, 1L);
  }

  @Test
  public void testUpdateTaskMerged() {
    //given
    Task existingTask = createTask();
    TaskRequestDto taskDto = TaskRequestDto.builder().name("updatedTaskName").description("updatedDescription")
        .dateTime(LocalDateTime.now())
        .status(TaskStatus.PENDING).build();

    when(taskRepository.findById(Mockito.any())).thenReturn(Optional.of(existingTask));

    //when
    Optional<TaskRequestDto> actual = taskService.updateTask(taskDto, 1L, 1L);

    //then
    Assert.assertTrue(actual.isPresent());
    Assert.assertEquals(taskDto.getName(), actual.get().getName());
    Assert.assertEquals(taskDto.getDescription(), actual.get().getDescription());
    Assert.assertEquals(taskDto.getDateTime(), actual.get().getDateTime());
    Assert.assertEquals(TaskStatus.PENDING, actual.get().getStatus());
  }

  @Test
  public void testDeleteTaskSuccess() {
    //given
    Task existingTask = createTask();
    when(taskRepository.findById(Mockito.any())).thenReturn(Optional.of(existingTask));

    //when
    Optional<TaskRequestDto> actual = taskService.deleteTask(1L, 1L);

    //then
    verify(taskRepository, times(1)).delete(any(Task.class));
    Assert.assertTrue(actual.isPresent());
  }


  @Test(expected = UserTaskServiceException.class)
  public void testValidateTask_optionalEmpty() {
    //when
    taskService.validateTask(Optional.empty(), 1L, 1L);
  }

  @Test(expected = UserTaskServiceException.class)
  public void testValidateTask_taskNotBelongToUser() {
    //given
    Task task = createTask();

    //when
    taskService.validateTask(Optional.of(task), 2L, 1L);
  }

  @Test
  public void testValidateTaskPassed() {
    //given
    Task task = createTask();

    //when
    boolean actual = taskService.validateTask(Optional.of(task), 1L, 1L);

    //then
    Assert.assertTrue(actual);
  }

  private Task createTask() {
    return Task.builder().userId(1L).taskName("taskName").description("description").dateTime(LocalDateTime.now()).
        status(TaskStatus.PENDING).build();
  }

  private TaskRequestDto createTaskDto() {
    return TaskRequestDto.builder().id(1L).name("taskName").description("description").dateTime(LocalDateTime.now())
        .status(TaskStatus.PENDING).build();
  }
}
