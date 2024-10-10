package ivan.denysiuk.employeesservice.domain.entity;


import jakarta.persistence.OneToOne;
import lombok.*;

import jakarta.persistence.Entity;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BusDriver extends Employee{
    @OneToOne(mappedBy = "busDriver")
    private DriverLicense driverLicense;

    @Override
    public String toString() {
        return super.toString() +
                " , driverLicense='" + driverLicense + '\'' +
                '}';
    }
}
