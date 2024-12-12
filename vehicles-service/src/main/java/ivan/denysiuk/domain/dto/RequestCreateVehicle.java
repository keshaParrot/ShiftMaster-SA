package ivan.denysiuk.domain.dto;

import ivan.denysiuk.Annotation.ValidBusLocation;
import ivan.denysiuk.domain.entity.BusLocation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class RequestCreateVehicle {
    @NotBlank(message = "Serial number cannot be empty")
    private String serialNumber;

    @NotBlank(message = "Registration number cannot be empty")
    private String registrationNumber;

    @NotBlank(message = "brand cannot be empty")
    private String brand;

    @ValidBusLocation
    @NotNull(message = "Bus need to be parked")
    private BusLocation busLocation;

}
