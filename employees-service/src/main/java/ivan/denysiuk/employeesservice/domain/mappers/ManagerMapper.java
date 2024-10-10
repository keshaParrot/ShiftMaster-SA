package ivan.denysiuk.employeesservice.domain.mappers;

import ivan.denysiuk.employeesservice.domain.dto.ManagerDTO;
import ivan.denysiuk.employeesservice.domain.entity.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ManagerMapper {
    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);
    ManagerDTO toDTO(Manager manager);
    Manager toEntity(ManagerDTO dispatcherDTO);
}
