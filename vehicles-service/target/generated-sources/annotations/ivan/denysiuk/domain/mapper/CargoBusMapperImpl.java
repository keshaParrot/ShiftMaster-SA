package ivan.denysiuk.domain.mapper;

import ivan.denysiuk.domain.dto.CargoBusDTO;
import ivan.denysiuk.domain.entity.CargoBus;
import ivan.denysiuk.domain.entity.Reserved;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-17T23:17:01+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class CargoBusMapperImpl implements CargoBusMapper {

    @Override
    public CargoBusDTO toDTO(CargoBus cargoBus) {
        if ( cargoBus == null ) {
            return null;
        }

        CargoBusDTO.CargoBusDTOBuilder<?, ?> cargoBusDTO = CargoBusDTO.builder();

        cargoBusDTO.id( cargoBus.getId() );
        cargoBusDTO.serialNumber( cargoBus.getSerialNumber() );
        cargoBusDTO.registrationNumber( cargoBus.getRegistrationNumber() );
        cargoBusDTO.brand( cargoBus.getBrand() );
        cargoBusDTO.deployDate( cargoBus.getDeployDate() );
        cargoBusDTO.lastAnnualMaintenance( cargoBus.getLastAnnualMaintenance() );
        cargoBusDTO.lastQuarterlyMaintenance( cargoBus.getLastQuarterlyMaintenance() );
        cargoBusDTO.status( cargoBus.getStatus() );
        List<Reserved> list = cargoBus.getWhenReserved();
        if ( list != null ) {
            cargoBusDTO.whenReserved( new ArrayList<Reserved>( list ) );
        }
        cargoBusDTO.busLocation( cargoBus.getBusLocation() );
        cargoBusDTO.loadCapacity( cargoBus.getLoadCapacity() );

        return cargoBusDTO.build();
    }

    @Override
    public CargoBus toEntity(CargoBusDTO cargoBusDTO) {
        if ( cargoBusDTO == null ) {
            return null;
        }

        CargoBus.CargoBusBuilder<?, ?> cargoBus = CargoBus.builder();

        cargoBus.id( cargoBusDTO.getId() );
        cargoBus.serialNumber( cargoBusDTO.getSerialNumber() );
        cargoBus.registrationNumber( cargoBusDTO.getRegistrationNumber() );
        cargoBus.brand( cargoBusDTO.getBrand() );
        cargoBus.deployDate( cargoBusDTO.getDeployDate() );
        cargoBus.lastAnnualMaintenance( cargoBusDTO.getLastAnnualMaintenance() );
        cargoBus.lastQuarterlyMaintenance( cargoBusDTO.getLastQuarterlyMaintenance() );
        cargoBus.status( cargoBusDTO.getStatus() );
        List<Reserved> list = cargoBusDTO.getWhenReserved();
        if ( list != null ) {
            cargoBus.whenReserved( new ArrayList<Reserved>( list ) );
        }
        cargoBus.busLocation( cargoBusDTO.getBusLocation() );
        cargoBus.loadCapacity( cargoBusDTO.getLoadCapacity() );

        return cargoBus.build();
    }
}
