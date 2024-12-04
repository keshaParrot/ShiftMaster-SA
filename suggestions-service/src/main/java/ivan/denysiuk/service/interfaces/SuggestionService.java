package ivan.denysiuk.service.interfaces;

import ivan.denysiuk.domain.entity.ShiftSuggestion;

import java.time.LocalDate;
import java.util.List;

public interface SuggestionService {

    //TODO we need to integrate here Result<> into service methods
    public ShiftSuggestion getById(Long id);
    //TODO maybe we need to make central enum "Departments" and change here string to enum
    public List<ShiftSuggestion> getByDepartment(String department, LocalDate date);
    public List<ShiftSuggestion> getByEmployeeId(Long empId, LocalDate date);
}
