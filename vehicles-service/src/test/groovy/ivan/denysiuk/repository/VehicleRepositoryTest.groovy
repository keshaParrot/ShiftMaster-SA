package ivan.denysiuk.repository


import ivan.denysiuk.domain.entity.BusLocation
import ivan.denysiuk.domain.entity.CargoBus
import ivan.denysiuk.domain.entity.PassengerBus
import ivan.denysiuk.domain.entity.Reserved
import ivan.denysiuk.domain.entity.Vehicle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

@DataJpaTest
@EnableJpaRepositories
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleRepositoryTest extends Specification {

    @Autowired VehicleRepository vehicleRepository

    @Shared CargoBus cargoBus
    @Shared PassengerBus passengerBus

    def setup() {
        initCargoBus()
        initPassengerBus()
    }
    def "initCargoBus"(){
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

        vehicleRepository.save(cargoBus)
        cargoBus.id = vehicleRepository.findAll().stream()
                .filter(v -> v instanceof CargoBus)
                .map(v -> v.getId())
                .findFirst()
                .orElse(null)
        return cargoBus
    }
    def "initPassengerBus"(){
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

        vehicleRepository.save(passengerBus)
        passengerBus.id = vehicleRepository.findAll().stream()
                .filter(v -> v instanceof PassengerBus)
                .map(v -> v.getId())
                .findFirst()
                .orElse(null)
        return passengerBus
    }

    def cleanup() {
        vehicleRepository.deleteAll()
    }

    def "getVehicleById"() {
        when: "Records of Cargo and passenger bus exist in the DB"
            def foundCargoBus = vehicleRepository.getVehicleById(cargoBus.id).orElseThrow()
            def foundPassengerBus = vehicleRepository.getVehicleById(passengerBus.id).orElseThrow()

        then:
            foundCargoBus == cargoBus
            foundPassengerBus == passengerBus

        when: "DB is empty after cleanup"
            cleanup()
            def optionalCargoBus = vehicleRepository.getVehicleById(1L)
            def optionalPassengerBus = vehicleRepository.getVehicleById(2L)

        then:
            !optionalCargoBus.isPresent()
            !optionalPassengerBus.isPresent()
    }
    def "getVehicleByIds"() {
        given:
            List<Long> givenIds = List.of(passengerBus.getId(),cargoBus.getId())

        when: "Records of Cargo and passenger bus exist in the DB"
            List<Vehicle> foundedVehicles = vehicleRepository.findByIdIn(givenIds)

        then:
            foundedVehicles.contains(cargoBus) && foundedVehicles.contains(passengerBus)

        when: "DB is empty after cleanup"
            cleanup()
            List<Vehicle> unfoundedVehicles = vehicleRepository.findByIdIn(givenIds)

        then:
            unfoundedVehicles.isEmpty()

    }

    def "getByRegistrationNumber"() {
        when: "Records of Cargo and passenger bus exist in the DB"
            def foundCargoBus = vehicleRepository.getByRegistrationNumber("AB2020B").orElseThrow()
            def foundPassengerBus = vehicleRepository.getByRegistrationNumber("AB1934GH").orElseThrow()

        then:
            foundCargoBus == cargoBus
            foundPassengerBus == passengerBus

        when: "DB is empty after cleanup"
            cleanup()
            def optionalCargoBus = vehicleRepository.getByRegistrationNumber("AB2020B")
            def optionalPassengerBus = vehicleRepository.getByRegistrationNumber("AB1934GH")

        then:
            !optionalCargoBus.isPresent()
            !optionalPassengerBus.isPresent()
    }

    def "getBySerialNumber"() {
        when: "Records of Cargo and passenger bus exist in the DB"
            def foundCargoBus = vehicleRepository.getBySerialNumber("3AF21B3").orElseThrow()
            def foundPassengerBus = vehicleRepository.getBySerialNumber("18AD63G5").orElseThrow()

        then:
            foundCargoBus == cargoBus
            foundPassengerBus == passengerBus

        when: "DB is empty after cleanup"
            cleanup()
            def optionalCargoBus = vehicleRepository.getBySerialNumber("3AF21B3")
            def optionalPassengerBus = vehicleRepository.getBySerialNumber("18AD63G5")

        then:
            !optionalCargoBus.isPresent()
            !optionalPassengerBus.isPresent()
    }

    def "isLocationOccupied"() {
        when: "the place is occupied"
            boolean fResult = vehicleRepository.isLocationOccupied(4,5)
        then:
            fResult
        when: "the place is not occupied"
            boolean sResult = vehicleRepository.isLocationOccupied(4,4)
        then:
            !sResult
    }
}
