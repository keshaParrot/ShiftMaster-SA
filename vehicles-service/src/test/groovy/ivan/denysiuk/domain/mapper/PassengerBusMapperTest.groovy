package ivan.denysiuk.domain.mapper

import ivan.denysiuk.domain.dto.PassengerBusDTO
import ivan.denysiuk.domain.dto.VehicleDTO
import ivan.denysiuk.domain.entity.BusLocation
import ivan.denysiuk.domain.entity.PassengerBus
import ivan.denysiuk.domain.entity.Reserved
import ivan.denysiuk.domain.entity.Vehicle
import spock.lang.Specification

import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

class PassengerBusMapperTest extends Specification {

    PassengerBus passengerBus
    PassengerBusDTO passengerBusDTO

    void setup() {
        passengerBus = PassengerBus.builder()
                .id(2L)
                .serialNumber("18AD63G5")
                .registrationNumber("AB1934GH")
                .brand("AORUS")
                .deployDate(LocalDate.of(2023, 2, 2))
                .lastQuarterlyMaintenance(LocalDate.of(2025, 2, 2))
                .lastAnnualMaintenance(LocalDate.of(2025, 2, 2))
                .numberOfSeats(12)
                .whenReserved(List.of(new Reserved(
                        LocalDate.of(2023, 5, 2),
                        LocalTime.of(14, 40),
                        LocalTime.of(16, 40))))
                .busLocation(new BusLocation(5, 4))
                .build()
        passengerBusDTO = PassengerBusDTO.builder()
                .id(2L)
                .serialNumber("18AD63G5")
                .registrationNumber("AB1934GH")
                .brand("AORUS")
                .deployDate(LocalDate.of(2023, 2, 2))
                .lastQuarterlyMaintenance(LocalDate.of(2025, 2, 2))
                .lastAnnualMaintenance(LocalDate.of(2025, 2, 2))
                .numberOfSeats(12)
                .whenReserved(List.of(new Reserved(
                        LocalDate.of(2023, 5, 2),
                        LocalTime.of(14, 40),
                        LocalTime.of(16, 40))))
                .busLocation(new BusLocation(5, 4))
                .build()
    }

    def "ToDTO"() {
        when: "converted vehicle by Passenger bus mapper"
            def convVehicleByMapper = PassengerBusMapper.INSTANCE.toDTO(passengerBus)
        then:
            convVehicleByMapper == passengerBusDTO
        when: "converted vehicle by vehicle manager"
            def convVehicleByManager = VehicleMapperManager.convertEntityToDto(passengerBus)
        then:
            convVehicleByManager == passengerBusDTO
    }

    def "ToEntity"() {
        when: "converted vehicle by Passenger bus mapper"
            def convVehicleByMapper = PassengerBusMapper.INSTANCE.toEntity(passengerBusDTO)
        then:
            convVehicleByMapper == passengerBus
        when: "converted vehicle by vehicle manager"
            def convVehicleByManager = VehicleMapperManager.convertDtoToEntity(passengerBusDTO)
        then:
            convVehicleByManager == passengerBus
    }
}
