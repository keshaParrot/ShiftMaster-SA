package ivan.denysiuk.employeesservice.domain.entity;



import ivan.denysiuk.employeesservice.domain.enumerations.TypeOfContract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private Date birthDay;

    private String department;
    private double rate;

    private TypeOfContract typeOfContract;
    private String email;
    private String passcode;
    private String password;

    public String getFullName(){
        return firstName+" "+lastName;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", birthDay=" + birthDay +
                ", department='" + department + '\'' +
                ", rate=" + rate +
                ", typeOfContract=" + typeOfContract +
                ", passcode='" + passcode + '\'' +
                '}';
    }
}
