package ivan.denysiuk.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BusLocation {

    private int hangar;
    private int platform;

    @Override
    public String toString() {
        return " {hangar=" + hangar +
                ", platform=" + platform +
                '}';
    }
}
