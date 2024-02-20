package org.example.taskapi.domain;
import jakarta.persistence.*;
import lombok.*;
import org.example.taskapi.domain.enums.TaskStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}