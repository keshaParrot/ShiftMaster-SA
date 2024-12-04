package ivan.denysiuk.domain.mapper;

import ivan.denysiuk.domain.dto.PassengerBusDTO;
import ivan.denysiuk.domain.entity.PassengerBus;
import ivan.denysiuk.domain.entity.Reserved;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-17T23:17:01+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class PassengerBusMapperImpl implements PassengerBusMapper {

    @Override
    public PassengerBusDTO toDTO(PassengerBus passengerBus) {
        if ( passengerBus == null ) {
            return null;
        }

        PassengerBusDTO.PassengerBusDTOBuilder<?, ?> passengerBusDTO = PassengerBusDTO.builder();

        passengerBusDTO.id( passengerBus.getId() );
        passengerBusDTO.serialNumber( passengerBus.getSerialNumber() );
        passengerBusDTO.registrationNumber( passengerBus.getRegistrationNumber() );
        passengerBusDTO.brand( passengerBus.getBrand() );
        passengerBusDTO.deployDate( passengerBus.getDeployDate() );
        passengerBusDTO.lastAnnualMaintenance( passengerBus.getLastAnnualMaintenance() );
        passengerBusDTO.lastQuarterlyMaintenance( passengerBus.getLastQuarterlyMaintenance() );
        passengerBusDTO.status( passengerBus.getStatus() );
        List<Reserved> list = passengerBus.getWhenReserved();
        if ( list != null ) {
            passengerBusDTO.whenReserved( new ArrayList<Reserved>( list ) );
        }
        passengerBusDTO.busLocation( passengerBus.getBusLocation() );
        passengerBusDTO.numberOfSeats( passengerBus.getNumberOfSeats() );

        return passengerBusDTO.build();
    }

    @Override
    public PassengerBus toEntity(PassengerBusDTO passengerBusDTO) {
        if ( passengerBusDTO == null ) {
            return null;
        }

        PassengerBus.PassengerBusBuilder<?, ?> passengerBus = PassengerBus.builder();

        passengerBus.id( passengerBusDTO.getId() );
        passengerBus.serialNumber( passengerBusDTO.getSerialNumber() );
        passengerBus.registrationNumber( passengerBusDTO.getRegistrationNumber() );
        passengerBus.brand( passengerBusDTO.getBrand() );
        passengerBus.deployDate( passengerBusDTO.getDeployDate() );
        passengerBus.lastAnnualMaintenance( passengerBusDTO.getLastAnnualMaintenance() );
        passengerBus.lastQuarterlyMaintenance( passengerBusDTO.getLastQuarterlyMaintenance() );
        passengerBus.status( passengerBusDTO.getStatus() );
        List<Reserved> list = passengerBusDTO.getWhenReserved();
        if ( list != null ) {
            passengerBus.whenReserved( new ArrayList<Reserved>( list ) );
        }
        passengerBus.busLocation( passengerBusDTO.getBusLocation() );
        passengerBus.numberOfSeats( passengerBusDTO.getNumberOfSeats() );

        return passengerBus.build();
    }
}
