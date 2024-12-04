package ivan.denysiuk.service.interfaces;

import java.time.LocalDate;
import java.util.List;

public interface ReportGenerator {
    void generateReportByEmpId(Long empId);
    void generateReportByDepartment(LocalDate date);
    Long checkReportExist(); //return id of this report
}
