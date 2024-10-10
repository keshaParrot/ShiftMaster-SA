package ivan.denysiuk.employeesservice.domain.mappers;

import ivan.denysiuk.employeesservice.domain.dto.AccountantDTO;
import ivan.denysiuk.employeesservice.domain.dto.BusDriverDTO;
import ivan.denysiuk.employeesservice.domain.dto.EmployeeDTO;
import ivan.denysiuk.employeesservice.domain.dto.ManagerDTO;
import ivan.denysiuk.employeesservice.domain.entity.Accountant;
import ivan.denysiuk.employeesservice.domain.entity.BusDriver;
import ivan.denysiuk.employeesservice.domain.entity.Employee;
import ivan.denysiuk.employeesservice.domain.entity.Manager;

public class EmployeeMapperManager {
    public static <T extends Employee> EmployeeDTO convertEntityToDto(T entity) {
        return switch (entity) {
            case Accountant accountant -> AccountantMapper.INSTANCE.toDTO(accountant);
            case BusDriver busDriver -> BusDriverMapper.INSTANCE.toDTO(busDriver);
            case Manager manager -> ManagerMapper.INSTANCE.toDTO(manager);
            default -> EmployeeMapper.INSTANCE.toDTO(entity);
        };
    }

    public static <T extends EmployeeDTO> Employee convertDtoToEntity(T dto) {
        return switch (dto) {
            case AccountantDTO accountantDTO -> AccountantMapper.INSTANCE.toEntity(accountantDTO);
            case BusDriverDTO busDriverDTO -> BusDriverMapper.INSTANCE.toEntity(busDriverDTO);
            case ManagerDTO managerDTO -> ManagerMapper.INSTANCE.toEntity(managerDTO);
            default -> EmployeeMapper.INSTANCE.toEntity(dto);
        };
    }
}
