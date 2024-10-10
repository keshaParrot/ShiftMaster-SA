package ivan.denysiuk.employeesservice.domain.dto;

import ivan.denysiuk.employeesservice.domain.enumeration.TypeOfContract;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

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
    private LocalDate birthDay;
    @NotBlank(message = "Department cannot be empty")
    private String department;
    @Min(value = 1, message = "Rate must be at least 1")
    private double rate;
    @NotBlank(message = "password cannot be empty")
    private String phoneNumber;
    @NotNull(message = "Contract type cannot be null")
    private TypeOfContract typeOfContract;
}
