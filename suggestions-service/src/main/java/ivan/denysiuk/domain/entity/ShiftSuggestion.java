package ivan.denysiuk.domain.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShiftSuggestion {

    private Long id;
    private Long empId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime creationDate;
    private String department;

}
