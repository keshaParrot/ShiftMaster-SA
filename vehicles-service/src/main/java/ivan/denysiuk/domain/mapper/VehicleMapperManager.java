package ivan.denysiuk.domain.mapper;


import ivan.denysiuk.domain.entity.CargoBus;
import ivan.denysiuk.domain.entity.PassengerBus;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.domain.dto.CargoBusDTO;
import ivan.denysiuk.domain.dto.PassengerBusDTO;
import ivan.denysiuk.domain.dto.VehicleDTO;

public class VehicleMapperManager {

    public static <T extends Vehicle, E extends VehicleDTO> E convertEntityToDto(T entity) {
        return switch (entity) {
            case PassengerBus passengerBus -> (E) PassengerBusMapper.INSTANCE.toDTO(passengerBus);
            case CargoBus cargoBus -> (E) CargoBusMapper.INSTANCE.toDTO(cargoBus);
            default -> throw new IllegalArgumentException("Unknown entity type: " + entity.getClass().getName());
        };
    }


    public static <T extends VehicleDTO,E extends Vehicle> E convertDtoToEntity(T dto) {
        return switch (dto) {
            case PassengerBusDTO passengerBusDTO -> (E)PassengerBusMapper.INSTANCE.toEntity(passengerBusDTO);
            case CargoBusDTO cargoBusDTO -> (E)CargoBusMapper.INSTANCE.toEntity(cargoBusDTO);
            default -> throw new IllegalArgumentException("Unknown DTO type: " + dto.getClass().getName());
        };
    }

}
