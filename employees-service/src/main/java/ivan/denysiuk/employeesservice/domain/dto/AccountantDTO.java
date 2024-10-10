package ivan.denysiuk.employeesservice.domain.dto;

import ivan.denysiuk.employeesservice.domain.enumeration.AccountantQualification;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountantDTO extends EmployeeDTO {

    @NotNull(message = "Qualification cannot be null")
    private AccountantQualification qualification;
}
