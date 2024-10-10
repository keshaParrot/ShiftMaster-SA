package ivan.denysiuk.employeesservice.domain.mappers;

import ivan.denysiuk.employeesservice.domain.dto.EmployeeDTO;
import ivan.denysiuk.employeesservice.domain.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    EmployeeDTO toDTO(Employee employee);
    Employee toEntity(EmployeeDTO employeeDTO);
}
