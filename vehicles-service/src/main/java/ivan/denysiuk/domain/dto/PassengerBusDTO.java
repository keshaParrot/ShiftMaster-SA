package ivan.denysiuk.domain.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerBusDTO extends VehicleDTO{

    private int numberOfSeats;
    @Override
    public String toString() {
        return super.toString() +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
