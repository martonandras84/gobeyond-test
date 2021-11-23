package com.gobeyond.usertaskservice.application.task;

import static org.mockito.Mockito.when;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test class for {@link TaskControllerImpl}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

  @Mock
  TaskServiceImpl taskService;

  @InjectMocks
  TaskControllerImpl taskController;

  @Test
  public void testCreateTaskStatusOk() {
    //given
    TaskRequestDto dto = createTaskDto();
    when(taskService.createTask(Mockito.any(), Mockito.any())).thenReturn(Optional.of(dto));

    //when
    ResponseEntity<TaskRequestDto> actual = taskController.createTask(1L, dto);

    //then
    Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  @Test
  public void testUpdateTaskStatusOk() {
    //given
    TaskRequestDto dto = createTaskDto();
    when(taskService.updateTask(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.of(dto));

    //when
    ResponseEntity<TaskRequestDto> actual = taskController.updateTask(1L, 1L, dto);

    //then
    Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  @Test
  public void testGetTaskInfoStatusOk() {
    //given
    TaskRequestDto dto = createTaskDto();

    when(taskService.getTaskInfo(Mockito.any(), Mockito.any())).thenReturn(Optional.of(dto));

    //when
    ResponseEntity<TaskRequestDto> actual = taskController.getTaskInfo(1L, 1L);

    //then
    Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  @Test
  public void testDeleteTaskInfoStatusOk() {
    //given
    TaskRequestDto dto = createTaskDto();

    when(taskService.deleteTask(Mockito.any(), Mockito.any())).thenReturn(Optional.of(dto));

    //when
    ResponseEntity<TaskRequestDto> actual = taskController.deleteTask(1L, 1L);

    //then
    Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  private TaskRequestDto createTaskDto() {
    return TaskRequestDto.builder().name("taskName").description("description").dateTime(LocalDateTime.now())
        .status(TaskStatus.PENDING).build();
  }

}
