package ivan.denysiuk.shiftservice.domain.entity;

import ivan.denysiuk.domain.BusStation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class BusDriverShift extends Shift{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long BusId;

    private Long Route;

}
