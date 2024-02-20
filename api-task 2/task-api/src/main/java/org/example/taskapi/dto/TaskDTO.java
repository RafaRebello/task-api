package org.example.taskapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.taskapi.domain.enums.TaskStatus;
@Getter
@Setter
@Builder
public class TaskDTO {

    private String title;
    private String description;
    private TaskStatus status;
    private Long userId;
}
