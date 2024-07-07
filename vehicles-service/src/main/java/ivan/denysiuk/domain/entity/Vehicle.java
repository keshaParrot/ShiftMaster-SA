package ivan.denysiuk.domain.entity;



import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serialNumber;
    private String registrationNumber;
    private String brand;
    private String location;
    private boolean availability;
    private LocalDateTime deployDate;
    private LocalDateTime lastTimeMaintenance;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{ id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", location='" + location + '\'' +
                ", availability=" + availability +
                ", deployDate=" + deployDate +
                ", lastTimeMaintenance=" + lastTimeMaintenance +
                '}';
    }
}
