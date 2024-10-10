package ivan.denysiuk.domain.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {

    private Long id;
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "password cannot be empty")
    private String password;
    @NotBlank(message = "password cannot be empty")
    private String phoneNumber;


}
