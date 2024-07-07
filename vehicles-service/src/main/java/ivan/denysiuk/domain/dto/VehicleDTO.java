package ivan.denysiuk.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleDTO {

    private Long id;
    private String serialNumber;
    private String registrationNumber;
    private String brand;
    private String location;
    private boolean availability;
    private LocalDateTime deployDate;
    private LocalDateTime lastTimeMaintenance;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{ id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", location='" + location + '\'' +
                ", availability=" + availability +
                ", deployDate=" + deployDate +
                ", lastTimeMaintenance=" + lastTimeMaintenance +
                '}';
    }
}
