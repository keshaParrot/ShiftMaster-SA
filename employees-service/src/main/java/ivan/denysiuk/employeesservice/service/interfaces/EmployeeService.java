package ivan.denysiuk.employeesservice.service.interfaces;





import ivan.denysiuk.employeesservice.domain.dto.EmployeeDTO;
import ivan.denysiuk.employeesservice.domain.entity.Employee;
import ivan.denysiuk.customClasses.Result;

import java.util.List;


public interface EmployeeService {

    <T extends EmployeeDTO> T saveToSystem(T employee);
    <T extends EmployeeDTO> T updateById(Long id, T employee);
    <T extends EmployeeDTO> T patchById(Long id, T employee);
    <T extends Employee> Result<T> getById(Long id,String department, Class<T> expectedType);
    <T extends Employee> Result<T> getByPesel(String pesel,String department, Class<T> expectedType);
    List<Employee> searchByFullName(String fullName,String department);
    Boolean deleteById(Long id);
    <T extends Employee> List<T> getAll(String department);
    boolean existById(Long employeeId);
    long countAll(String department);

}
