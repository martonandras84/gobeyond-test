package com.gobeyond.usertaskservice.domain.model.task;

import com.gobeyond.usertaskservice.domain.shared.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link Task} entity
 *
 * @author Andras Marton (martonandras84@gmail.com)
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

  /**
   * Query a {@link Task} record by id
   *
   * @param id task id
   * @return {@link Optional} of {@link Task}
   */
  Optional<Task> findById(Long id);

  /**
   * Query all {@link Task} records for a {@link com.gobeyond.usertaskservice.domain.model.user.User}
   *
   * @param userId user id
   * @return {@link Page} of {@link Task}s
   */
  Page<Task> findAllByUserId(Long userId, Pageable pageable);

  /**
   * Query all {@link Task}s to maintain by dateTime and status
   *
   * @param endDate end date
   * @param status status
   * @return {@link List} of {@link Task}
   */
  @Query("SELECT t FROM Task t WHERE t.dateTime < :endDate AND t.status = :status")
  List<Task> findAllToMaintain(LocalDateTime endDate, TaskStatus status);
}
