package ivan.denysiuk.employeesservice.domain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLicenseDTO {
    private Long id;
    @NotBlank(message = "License number cannot be empty")
    private String licenseNumber;
    @NotNull(message = "Release date cannot be null")
    @Past(message = "Release date must be in the past")
    private Date releaseDate;
    @NotNull(message = "Release date cannot be null")
    @Future(message = "Expiry date must be in the past")
    private Date expiryDate;
}
