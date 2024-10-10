package ivan.denysiuk.domain.dto;

import ivan.denysiuk.domain.entity.BusLocation;
import ivan.denysiuk.domain.entity.Reserved;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.domain.enumeration.VehicleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {


    private Long id;
    private String serialNumber;
    private String registrationNumber;
    private String brand;
    private LocalDate deployDate;
    private LocalDate lastAnnualMaintenance;
    private LocalDate lastQuarterlyMaintenance;
    private VehicleStatus status;

    private List<Reserved> whenReserved;
    private BusLocation busLocation;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{ id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", deployDate=" + deployDate +
                ", lastAnnualMaintenance=" + lastAnnualMaintenance +
                ", lastQuarterlyMaintenance=" + lastQuarterlyMaintenance +
                ", busLocation=" + busLocation +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDTO vehicleDTO = (VehicleDTO) o;
        return Objects.equals(serialNumber, vehicleDTO.serialNumber) && Objects.equals(registrationNumber, vehicleDTO.registrationNumber) && Objects.equals(brand, vehicleDTO.brand);
    }
}
