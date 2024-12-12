package ivan.denysiuk.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RequestCreateCargoBus extends RequestCreateVehicle{
    @NotBlank(message = "Load capacity cannot be empty")
    private String loadCapacity;

}
