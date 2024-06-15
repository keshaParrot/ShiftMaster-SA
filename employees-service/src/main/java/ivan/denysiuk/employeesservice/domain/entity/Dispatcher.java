package ivan.denysiuk.employeesservice.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dispatcher extends Employee{

    private String specialization;


    @Override
    public String toString() {
        return  super.toString() +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
