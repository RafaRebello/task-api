package org.example.taskapi.repository;

import org.example.taskapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskByStatus(String status);
}
