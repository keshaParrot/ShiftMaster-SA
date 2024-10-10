package ivan.denysiuk.employeesservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BusDriverDTO extends EmployeeDTO {

    @NotBlank(message = "driver License cannot be empty")
    private DriverLicenseDTO driverLicence;
}
