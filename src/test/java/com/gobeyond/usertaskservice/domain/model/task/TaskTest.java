package com.gobeyond.usertaskservice.domain.model.task;

import com.gobeyond.usertaskservice.application.task.dto.TaskRequestDto;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test class for {@link Task}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public class TaskTest {

  @Test
  public void testOf() {
    //given
    TaskRequestDto dto = createTaskDto();
    Task taskExpected = createTask();

    //when
    Task taskActual = Task.of(1L, dto, TaskStatus.PENDING);

    //then
    SoftAssertions softAssert = new SoftAssertions();
    softAssert.assertThat(taskActual.getId()).isNull();
    softAssert.assertThat(taskActual.getUserId()).isEqualTo(taskExpected.getUserId());
    softAssert.assertThat(taskActual.getTaskName()).isEqualTo(taskExpected.getTaskName());
    softAssert.assertThat(taskActual.getDescription()).isEqualTo(taskExpected.getDescription());
    softAssert.assertThat(taskActual.getDateTime()).isBeforeOrEqualTo(taskExpected.getDateTime());
    softAssert.assertThat(taskActual.getStatus()).isEqualTo(taskExpected.getStatus());
    softAssert.assertAll();
  }

  @Test
  public void testMergeOtherNull() {
    //given
    Task task = createTask();

    //when
    boolean actual = task.merge(null);

    //then
    Assert.assertFalse(actual);
  }

  @Test
  public void testMergeWithSameValues() {
    //given
    LocalDateTime now = LocalDateTime.now();
    Task taskOrig = createTask();
    taskOrig.setDateTime(now);
    Task taskNew = createTask();
    taskNew.setDateTime(now);

    //when
    boolean actual = taskOrig.merge(taskNew);

    //then
    Assert.assertFalse(actual);
  }

  @Test
  public void testMergeWithDiffValues() {
    //given
    Task taskOrig = createTask();
    Task taskDiffValues = Task.builder().userId(1L).taskName("diffTaskName").description("diffDescription")
        .dateTime(LocalDateTime.now()).status(TaskStatus.DONE)
        .build();

    //when
    boolean actual = taskOrig.merge(taskDiffValues);

    //then
    Assert.assertTrue(actual);
    Assert.assertEquals(taskDiffValues.getStatus(), taskOrig.getStatus());
  }

  private Task createTask() {
    return Task.builder().userId(1L).taskName("taskName").description("description").dateTime(LocalDateTime.now()).
        status(TaskStatus.PENDING).build();
  }

  private TaskRequestDto createTaskDto() {
    return TaskRequestDto.builder().name("taskName").description("description").dateTime(LocalDateTime.now())
        .status(TaskStatus.PENDING).build();
  }
}
