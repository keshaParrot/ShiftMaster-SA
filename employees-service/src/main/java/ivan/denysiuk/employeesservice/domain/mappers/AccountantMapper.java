package ivan.denysiuk.employeesservice.domain.mappers;

import ivan.denysiuk.employeesservice.domain.dto.AccountantDTO;
import ivan.denysiuk.employeesservice.domain.entity.Accountant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountantMapper {
    AccountantMapper INSTANCE = Mappers.getMapper(AccountantMapper.class);

    AccountantDTO toDTO(Accountant accountant);

    Accountant toEntity(AccountantDTO accountantDTO);
}
