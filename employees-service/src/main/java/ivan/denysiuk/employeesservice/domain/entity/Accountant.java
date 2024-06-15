package ivan.denysiuk.employeesservice.domain.entity;

import ivan.denysiuk.employeesservice.domain.enumerations.AccountantQualification;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Accountant extends Employee{
    AccountantQualification Qualification;

    @Override
    public String toString() {
        return super.toString() +
                ", listOfQualification=" + Qualification +
                '}';
    }
}
