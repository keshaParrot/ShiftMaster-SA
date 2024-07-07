package ivan.denysiuk.domain.mapper;

import ivan.denysiuk.domain.entity.PassengerBus;
import ivan.denysiuk.domain.dto.PassengerBusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassengerBusMapper {
    PassengerBusMapper INSTANCE = Mappers.getMapper(PassengerBusMapper.class);

    PassengerBusDTO toDTO(PassengerBus passengerBus);
    PassengerBus toEntity(PassengerBusDTO passengerBusDTO);
}
