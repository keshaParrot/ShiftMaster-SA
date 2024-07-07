package ivan.denysiuk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
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
