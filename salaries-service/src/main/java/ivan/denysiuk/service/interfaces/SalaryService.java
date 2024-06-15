package ivan.denysiuk.service.interfaces;

import java.util.Map;

public interface SalaryService {

    double getMonthSalary(Long id, int month);
    Map<String, Double> getMonthTax(Long id, int month);
    double getMonthRevenue(Long id, int month);
}
