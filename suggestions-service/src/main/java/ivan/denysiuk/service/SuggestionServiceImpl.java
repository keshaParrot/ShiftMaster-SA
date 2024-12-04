package ivan.denysiuk.service;

import ivan.denysiuk.domain.entity.ShiftSuggestion;
import ivan.denysiuk.service.interfaces.SuggestionService;

import java.time.LocalDate;
import java.util.List;

public class SuggestionServiceImpl implements SuggestionService {


    @Override
    public ShiftSuggestion getById(Long id) {
        return null;
    }

    @Override
    public List<ShiftSuggestion> getByDepartment(String department, LocalDate date) {
        return List.of();
    }

    @Override
    public List<ShiftSuggestion> getByEmployeeId(Long empId, LocalDate date) {
        return List.of();
    }
}
