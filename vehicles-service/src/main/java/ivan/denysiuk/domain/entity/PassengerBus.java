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
public class PassengerBus extends Vehicle{


    private int numberOfSeats;

    @Override
    public String toString() {
        return super.toString() +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
