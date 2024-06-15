package ivan.denysiuk.employeesservice.domain.dto;

import ivan.denysiuk.employeesservice.domain.enumerations.DispatcherSpecialization;
import ivan.denysiuk.employeesservice.domain.enumerations.TypeOfContract;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispatcherDTO {
    private Long id;
    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    @NotBlank(message = "Last Name cannot be empty")
    private String lastName;
    @NotNull(message = "pesel cannot be null")
    @Pattern(regexp = "\\d{11}", message = "Invalid pesel number format")
    private String pesel;
    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private Date birthDay;
    @NotBlank(message = "Department cannot be empty")
    private String department;
    @Min(value = 1, message = "Rate must be at least 1")
    private double rate;
    @NotNull(message = "Contract type cannot be null")
    private TypeOfContract typeOfContract;
    @NotNull(message = "Specialization type cannot be null")
    private DispatcherSpecialization specialization;
}
