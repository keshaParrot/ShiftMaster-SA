package ivan.denysiuk.domain.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CargoBusDTO extends VehicleDTO{

    private String loadCapacity;
    @Override
    public String toString() {
        return super.toString() +
                ", loadCapacity='" + loadCapacity + '\'' +
                '}';
    }
}
