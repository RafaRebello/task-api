package org.example.taskapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "User_Task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String team;
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
