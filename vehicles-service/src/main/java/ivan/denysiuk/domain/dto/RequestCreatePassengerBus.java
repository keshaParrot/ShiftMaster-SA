package ivan.denysiuk.domain.dto;

import com.github.javaparser.quality.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RequestCreatePassengerBus extends RequestCreateVehicle{
    @NotBlank(message = "Number of seats cannot be empty")
    private Integer numberOfSeats;
}
