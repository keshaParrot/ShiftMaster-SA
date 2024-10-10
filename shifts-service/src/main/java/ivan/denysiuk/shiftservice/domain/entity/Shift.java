package ivan.denysiuk.shiftservice.domain.entity;

import ivan.denysiuk.shiftservice.domain.ShiftDayTime;
import ivan.denysiuk.customClasses.HoursClass;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String department;
    private ShiftDayTime dayTime;
    private Date date;
    private LocalTime startTime;
    private LocalTime actualStartTime;
    private LocalTime endTime;
    private LocalTime actualEndTime;
    private String breakTime;

    private Long employeeId;

    private Long lastModifierManager;
    private MonthDay lastTimeModified;

    /**
     * Calculate actual worked time of this shift
     *
     * @return Worked time represented of Hours class
     */
    public HoursClass getWorkedTime() {
        if (this.actualStartTime == null || this.actualEndTime == null) {
            return null;
        }

        LocalTime localStartTime = this.actualStartTime;
        LocalTime localEndTime = this.actualEndTime;
        Duration duration;

        if (localEndTime.isBefore(localStartTime)) {
            duration = Duration.between(localStartTime, LocalTime.MAX).plus(Duration.between(LocalTime.MIN, localEndTime));
        } else {
            duration = Duration.between(localStartTime, localEndTime);
        }

        long totalMinutes = duration.toMinutes();
        int hours = (int) (totalMinutes / 60);
        int minutes = (int) (totalMinutes % 60);

        return new HoursClass(hours, minutes);
    }
    /**
     * Calculate actual break time of this shift
     *
     * @return break time represented of Hours class
     */
    public HoursClass getBreakTime(){
        if (this.breakTime == null || this.breakTime.isEmpty()) {
            return new HoursClass();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localBreakTime = LocalTime.parse(this.breakTime, formatter);

        int hours = localBreakTime.getHour();
        int minutes = localBreakTime.getMinute();
        return new HoursClass(hours, minutes);
    }
}
