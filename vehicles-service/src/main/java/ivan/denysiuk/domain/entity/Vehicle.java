package ivan.denysiuk.domain.entity;

import ivan.denysiuk.domain.enumeration.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serialNumber;
    private String registrationNumber;
    private String brand;
    private LocalDate deployDate;
    private LocalDate lastAnnualMaintenance;
    private LocalDate lastQuarterlyMaintenance;
    private VehicleStatus status;

    @ElementCollection
    @CollectionTable(name = "vehicle_reservations", joinColumns = @JoinColumn(name = "vehicle_id"))
    private List<Reserved> whenReserved;
    @Embedded
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
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(serialNumber, vehicle.serialNumber) && Objects.equals(registrationNumber, vehicle.registrationNumber) && Objects.equals(brand, vehicle.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, registrationNumber, brand);
    }

    /*public boolean isAvailable(LocalDate date, LocalTime from, LocalTime to) {
        if(status == VehicleStatus.INACTIVE) return false;

        for (Reserved reserved : whenReserved) {
            if (reserved.getDate().equals(date)) {
                if (!from.isBefore(reserved.getFrom()) && !to.isAfter(reserved.getTo())) {
                    return false;
                }
            }
        }
        return true;
    }*/
    /** TODO refactor this method
     * TODO треба подумати чи потрібно шось таке в випадках, коли водій змінюється на ходу, хоча це не комунікація мєйська, тому такі випадки рідкість
     Method for checking whether the vehicle is available

     @return
     0 if Vehicle is available
     1 if Vehicle is not available
     2 if Vehicle don't have between shifts 15 minutes break
     */
    public int isAvailable(LocalDate date, LocalTime from, LocalTime to) {
        if (status == VehicleStatus.INACTIVE) return 1;

        for (Reserved reserved : whenReserved) {
            if (reserved.getDate().equals(date)) {
                if (!from.isBefore(reserved.getFrom()) && !to.isAfter(reserved.getTo())) {
                    return 1;
                }
                if (from.isBefore(reserved.getFrom()) && to.plusMinutes(15).isAfter(reserved.getFrom())) {
                    return 2;
                }
                if (to.isAfter(reserved.getTo()) && from.minusMinutes(15).isBefore(reserved.getTo())) {
                    return 2;
                }
                if (from.equals(reserved.getTo()) || from.isBefore(reserved.getTo().plusMinutes(15))) {
                    return 2;
                }
            }
        }
        return 0;
    }

    LocalDate getNextAnnualMaintenance(){
        return lastAnnualMaintenance.plusYears(1);
    }
    LocalDate getNextQuarterlyMaintenance(){
        return lastQuarterlyMaintenance.plusMonths(3);
    }
    /**
        Method for checking whether the car has to undergo technical inspection

        @return
            0 if Vehicle not need Maintenance
            1 if Vehicle need Quarterly Maintenance
            2 if Vehicle need Annual Maintenance
     */
    public int isNeedInspection() {
        if (status == VehicleStatus.INACTIVE) return 0;

        LocalDate now = LocalDate.now();
        LocalDate nextQuarterlyMaintenance = getNextQuarterlyMaintenance();
        LocalDate nextAnnualMaintenance = getNextAnnualMaintenance();

        if (now.isAfter(nextAnnualMaintenance) || now.isEqual(nextAnnualMaintenance)) {
            return 2;
        }
        if (now.isAfter(nextQuarterlyMaintenance) || now.isEqual(nextQuarterlyMaintenance)) {
            return 1;
        }
        return 0;
    }


}
