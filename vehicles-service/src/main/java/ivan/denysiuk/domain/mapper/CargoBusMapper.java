package ivan.denysiuk.domain.mapper;

import ivan.denysiuk.domain.entity.CargoBus;
import ivan.denysiuk.domain.dto.CargoBusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CargoBusMapper {
    CargoBusMapper INSTANCE = Mappers.getMapper(CargoBusMapper.class);

    CargoBusDTO toDTO(CargoBus cargoBus);
    CargoBus toEntity(CargoBusDTO cargoBusDTO);
}
