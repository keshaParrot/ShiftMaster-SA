package ivan.denysiuk.employeesservice.service;


import java.util.function.Supplier;
import java.util.logging.Logger;

import ivan.denysiuk.employeesservice.domain.dto.EmployeeDTO;
import ivan.denysiuk.employeesservice.domain.mappers.*;
import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.employeesservice.domain.entity.Employee;
import ivan.denysiuk.employeesservice.repository.EmployeeRepository;
import ivan.denysiuk.employeesservice.service.interfaces.EmployeeService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = Logger.getLogger(EmployeeService.class.getName());
    private final EmployeeRepository employeeRepository;

    @Override
    public <T extends EmployeeDTO> T saveToSystem(T employeeDto) {
        try {
            Employee employee = convertDtoToEntity(employeeDto);
            Employee savedEmployee = employeeRepository.save(employee);
            return (T) convertEntityToDto(savedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T extends EmployeeDTO> T updateById(Long id, T employee) {
        return null;
    }

    @Override
    public <T extends EmployeeDTO> T patchById(Long id, T employee) {
        return null;
    }

    @Override
    public <T extends Employee> Result<T> getById(Long id, String department, Class<T> expectedType) {
        return getResult(() -> employeeRepository.getEmployeeById(id, department), expectedType);
    }

    @Override
    public List<Employee> searchByFullName(String fullName,String department) {
        return employeeRepository.searchByFullName(fullName, department);
    }

    @Override
    public <T extends Employee> Result<T> getByPesel(String pesel,String department, Class<T> expectedType) {
        return getResult(() -> employeeRepository.getByPesel(pesel, department), expectedType);
    }
    @Override
    public Boolean deleteById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public List<Employee> getAll(String department) {
        return employeeRepository.getAll(department);
    }
    @Override
    public boolean existById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
    @Override
    public long countAll(String department) {
        return employeeRepository.countAll(department);
    }
    private <T extends Employee> Result<T> getResult(Supplier<Employee> supplier, Class<T> expectedType) {
        Employee employee = supplier.get();

        if (expectedType.isInstance(employee)) {
            return  Result.success(expectedType.cast(employee));
        } else {
            String errorMessage = "Expected type " + expectedType.getSimpleName() + ", but received " + (employee != null ? employee.getClass().getSimpleName() : "null");
            logger.warning(errorMessage);
            return Result.failure(errorMessage);
        }
    }
    private <T extends Employee> EmployeeDTO convertEntityToDto(T entity) {
        return EmployeeMapperManager.convertEntityToDto(entity);
    }

    private <T extends EmployeeDTO> Employee convertDtoToEntity(T dto) {
        return EmployeeMapperManager.convertDtoToEntity(dto);
    }
}
