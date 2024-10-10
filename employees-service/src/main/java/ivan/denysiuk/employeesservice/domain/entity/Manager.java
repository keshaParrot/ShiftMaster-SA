package ivan.denysiuk.employeesservice.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Manager extends Employee{

    private String specialization;


    @Override
    public String toString() {
        return  super.toString() +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
