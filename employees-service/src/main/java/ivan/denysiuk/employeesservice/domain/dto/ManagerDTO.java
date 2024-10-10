package ivan.denysiuk.employeesservice.domain.dto;

import ivan.denysiuk.employeesservice.domain.enumeration.ManagerSpecialization;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO extends EmployeeDTO{

    @NotNull(message = "Specialization type cannot be null")
    private ManagerSpecialization specialization;
}
