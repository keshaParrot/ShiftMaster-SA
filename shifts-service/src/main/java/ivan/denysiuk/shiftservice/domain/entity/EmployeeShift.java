package ivan.denysiuk.shiftservice.domain.entity;

import ivan.denysiuk.shiftservice.domain.TypeOfShift;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class EmployeeShift extends Shift{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TypeOfShift typeOfShift;

}
