package ivan.denysiuk.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reserved {

    private LocalDate date;
    @Column(name = "reservation_from")
    private LocalTime from;
    @Column(name = "reservation_to")
    private LocalTime to;

    @Override
    public String toString() {
        return " {date=" + date +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
