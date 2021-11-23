package com.gobeyond.usertaskservice.application.scheduler;

import com.gobeyond.usertaskservice.domain.model.task.Task;
import com.gobeyond.usertaskservice.domain.model.task.TaskRepository;
import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled service to maintain Task entities: update PENDING task to DONE if cutoff time is passed.
 * Frequency is stored as property (usertaskservice.maintenance.job.frequency).
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskMaintenanceJob {

  private final TaskRepository taskRepository;

  @Scheduled(fixedDelayString = "${usertaskservice.maintenance.job.frequency}",
      initialDelayString = "${random.long(${usertaskservice.maintenance.job.frequency})}")
  public void scheduledJob() {
    maintainPendingPassedTasks();
  }

  List<Task> maintainPendingPassedTasks() {
    List<Task> tasksToDone = taskRepository.findAllToMaintain(LocalDateTime.now(), TaskStatus.PENDING);

    tasksToDone.forEach(task -> {

      task.setStatus(TaskStatus.DONE);
      taskRepository.save(task);

      log.info("Task status set to DONE: {}", task);
    });

    return tasksToDone;
  }
}
