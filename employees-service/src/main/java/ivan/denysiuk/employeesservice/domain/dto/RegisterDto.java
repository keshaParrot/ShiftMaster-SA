package ivan.denysiuk.employeesservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "password cannot be empty")
    private String password;
}
