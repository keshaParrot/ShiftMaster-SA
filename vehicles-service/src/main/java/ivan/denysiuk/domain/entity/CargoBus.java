package ivan.denysiuk.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CargoBus extends Vehicle {
    private String loadCapacity;

    @Override
    public String toString() {
        return super.toString() +
                ", loadCapacity='" + loadCapacity + '\'' +
                '}';
    }
}
