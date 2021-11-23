package com.gobeyond.usertaskservice.application.scheduler;

import com.gobeyond.usertaskservice.domain.model.task.Task;
import com.gobeyond.usertaskservice.domain.model.task.TaskRepository;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test class for {@link TaskMaintenanceJob}
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskMaintenanceJobTest {

  @Mock
  TaskRepository taskRepository;

  @InjectMocks
  TaskMaintenanceJob job;

  @Test
  public void testMaintainPendingPassedTasks() {
    //given
    Mockito.when(taskRepository.findAllToMaintain(Mockito.any(), Mockito.any()))
        .thenReturn(List.of(createTask(), createTask()));

    //when
    List<Task> actual = job.maintainPendingPassedTasks();

    //then
    Assert.assertEquals(actual.size(), 2);
    actual.forEach(TaskMaintenanceJobTest::accept);
  }

  private static void accept(Task task) {
    Assert.assertEquals(task.getStatus(), TaskStatus.DONE);
  }

  private Task createTask() {
    return Task.builder().userId(1L).taskName("taskName").description("description").dateTime(LocalDateTime.now()).
        status(TaskStatus.PENDING).build();
  }
}
