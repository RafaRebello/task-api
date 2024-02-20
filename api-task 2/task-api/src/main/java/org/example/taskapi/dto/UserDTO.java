package org.example.taskapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class UserDTO {

    private String name;
    private String team;
    private List<TaskDTO> taskDTO;
}
