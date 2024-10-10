package ivan.denysiuk.employeesservice.domain.mappers;

import ivan.denysiuk.employeesservice.domain.dto.BusDriverDTO;
import ivan.denysiuk.employeesservice.domain.entity.BusDriver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusDriverMapper {
    BusDriverMapper INSTANCE = Mappers.getMapper(BusDriverMapper.class);
    BusDriverDTO toDTO(BusDriver busDriver);
    BusDriver toEntity(BusDriverDTO busDriverDTO);
}
