package ivan.denysiuk.domain.mapper

import ivan.denysiuk.domain.dto.CargoBusDTO
import ivan.denysiuk.domain.dto.VehicleDTO
import ivan.denysiuk.domain.entity.BusLocation
import ivan.denysiuk.domain.entity.CargoBus
import ivan.denysiuk.domain.entity.Reserved
import ivan.denysiuk.domain.entity.Vehicle
import spock.lang.Specification

import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

class CargoBusMapperTest extends Specification {

    CargoBus cargoBus
    CargoBusDTO cargoBusDto

    void setup() {
        cargoBus = CargoBus.builder()
                .id(1L)
                .serialNumber("3AF21B3")
                .registrationNumber("AB2020B")
                .brand("BOGDAN")
                .deployDate(LocalDate.of(2022, 2, 2))
                .lastQuarterlyMaintenance(LocalDate.of(2022, 2, 3))
                .lastAnnualMaintenance(LocalDate.of(2022, 2, 3))
                .loadCapacity("25")
                .whenReserved(List.of(new Reserved(
                        LocalDate.of(2022, 5, 2),
                        LocalTime.of(14, 40),
                        LocalTime.of(19, 40))))
                .busLocation(new BusLocation(4, 5))
                .build()

        cargoBusDto = CargoBusDTO.builder()
                .id(1L)
                .serialNumber("3AF21B3")
                .registrationNumber("AB2020B")
                .brand("BOGDAN")
                .deployDate(LocalDate.of(2022, 2, 2))
                .lastQuarterlyMaintenance(LocalDate.of(2022, 2, 3))
                .lastAnnualMaintenance(LocalDate.of(2022, 2, 3))
                .loadCapacity("25")
                .whenReserved(List.of(new Reserved(
                        LocalDate.of(2022, 5, 2),
                        LocalTime.of(14, 40),
                        LocalTime.of(19, 40))))
                .busLocation(new BusLocation(4, 5))
                .build()
    }

    def "ToDTO"() {
        when: "converted vehicle by cargo bus mapper"
            def convVehicleByMapper = CargoBusMapper.INSTANCE.toDTO(cargoBus)
        then:
            convVehicleByMapper == cargoBusDto
        when: "converted vehicle by vehicle manager"
            def convVehicleByManager = VehicleMapperManager.convertEntityToDto(cargoBus)
        then:
            convVehicleByManager == cargoBusDto
    }

    def "ToEntity"() {
        when: "converted vehicle by cargo bus mapper"
            def convVehicleByMapper = CargoBusMapper.INSTANCE.toEntity(cargoBusDto)
        then:
            convVehicleByMapper == cargoBus
        when: "converted vehicle by vehicle manager"
            def convVehicleByManager = VehicleMapperManager.convertDtoToEntity(cargoBusDto)
        then:
            convVehicleByManager == cargoBus
    }
}
