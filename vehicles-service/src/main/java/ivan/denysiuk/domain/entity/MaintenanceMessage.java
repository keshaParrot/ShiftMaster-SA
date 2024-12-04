package ivan.denysiuk.domain.entity;

import ivan.denysiuk.domain.enumeration.MessageStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Setter
    @Column(nullable = false)
    private Long messageOwner = null;
    @Setter
    @Column(nullable = false)
    private MessageStatus status;
}

