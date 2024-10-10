package ivan.denysiuk.employeesservice.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class DriverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String LicenseNumber;
    private Date releaseDate;
    private Date expiryDate;
    @OneToOne
    @JoinColumn(name = "bus_driver_id")
    BusDriver busDriver;

    @Override
    public String toString() {
        return "DriverLicense{" +
                "id=" + id +
                ", LicenseNumber='" + LicenseNumber + '\'' +
                ", releaseDate=" + releaseDate +
                ", expiryDate=" + expiryDate +
                ", busDriver=" + busDriver +
                '}';
    }
}
