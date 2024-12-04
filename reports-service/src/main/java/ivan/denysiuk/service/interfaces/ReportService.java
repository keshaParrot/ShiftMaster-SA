package ivan.denysiuk.service.interfaces;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    String getReportById(Long Id); //return report
    List<String> getReportsByEmpId(Long empId); //return all dto reports
    List<String> getReportsByDepartment(Long empId); //return all dto reports
    void generateReportByEmpId(Long empId);
    void generateReportByDepartment(LocalDate date);
    Long checkReportExist(); //return id of this report
}
