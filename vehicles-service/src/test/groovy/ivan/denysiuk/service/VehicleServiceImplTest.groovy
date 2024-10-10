package ivan.denysiuk.service

import ivan.denysiuk.customClasses.Result
import ivan.denysiuk.domain.dto.CargoBusDTO
import ivan.denysiuk.domain.dto.PassengerBusDTO
import ivan.denysiuk.domain.dto.VehicleDTO
import ivan.denysiuk.domain.entity.BusLocation
import ivan.denysiuk.domain.entity.CargoBus
import ivan.denysiuk.domain.entity.PassengerBus
import ivan.denysiuk.domain.entity.Reserved
import ivan.denysiuk.domain.entity.Vehicle
import ivan.denysiuk.domain.enumeration.VehicleType
import ivan.denysiuk.domain.mapper.VehicleMapperManager
import ivan.denysiuk.repository.VehicleRepository
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors
import java.time.LocalTime

class VehicleServiceImplTest extends Specification {

    def vehicleRepository = Mock(VehicleRepository)
    def vehicleService = new VehicleServiceImpl(vehicleRepository)

    @Shared List<Vehicle> listOfEntity = []
    @Shared List<VehicleDTO> listOfDto = []

    def setupSpec() {
        initializeCargoBus()
        initializePassengerBus()
    }

    void cleanup() {
        listOfEntity.clear()
        listOfDto.clear()
    }
    private void initializeCargoBus(){
        CargoBus cargoBus = CargoBus.builder()
                .id(1L)
                .serialNumber("3AF21B3")
                .registrationNumber("AB2020B")
                .brand("BOGDAN")
                .deployDate(LocalDate.of(2022, 2, 2))
                .lastQuarterlyMaintenance(LocalDate.of(2022, 2, 3))
                .lastAnnualMaintenance(LocalDate.of(2022, 2, 3))
                .loadCapacity("25")
                .whenReserved(List.of(
                        new Reserved(
                            LocalDate.of(2022, 5, 2),
                            LocalTime.of(14, 40),
                            LocalTime.of(19, 40))))
                .busLocation(new BusLocation(4, 5))
                .build()

        listOfEntity << cargoBus
        listOfDto << convertEntityToDto(cargoBus)
    }
    private void initializePassengerBus(){
        PassengerBus passengerBus = PassengerBus.builder()
                .id(2L)
                .serialNumber("18AD63G5")
                .registrationNumber("AB1934GH")
                .brand("AORUS")
                .deployDate(LocalDate.of(2023, 2, 2))
                .lastQuarterlyMaintenance(LocalDate.of(2025, 2, 2))
                .lastAnnualMaintenance(LocalDate.of(2025, 2, 2))
                .numberOfSeats(12)
                .whenReserved(List.of(
                        new Reserved(
                            LocalDate.of(2023, 5, 2),
                            LocalTime.of(14, 40),
                            LocalTime.of(16, 40))))
                .busLocation(new BusLocation(5, 4))
                .build()

        listOfEntity << passengerBus
        listOfDto << convertEntityToDto(passengerBus)
    }

    def "SaveToSystem"() {
        given:
            CargoBusDTO cargoBusToSave = (CargoBusDTO)listOfDto.getFirst()
            PassengerBusDTO passengerBusToSave = (PassengerBusDTO)listOfDto.getLast()

        when: "when user save cargo bus to system"
        Result<Vehicle> savedCargoBus = vehicleService.saveToSystem(cargoBusToSave)
        then:
            1 * vehicleRepository.save(listOfEntity.getFirst()) >> listOfEntity.getFirst()
            savedCargoBus.isPresent() && savedCargoBus.getValue() == convertDtoToEntity(cargoBusToSave)

        when: "when user save passenger bus to system"
            Result<Vehicle> savedPassengerBus = vehicleService.saveToSystem(passengerBusToSave)
        then:
            1 * vehicleRepository.save(listOfEntity.getLast()) >> listOfEntity.getLast()
            savedPassengerBus.isPresent() && savedPassengerBus.getValue() == convertDtoToEntity(passengerBusToSave)
    }

    def "deleteById"() {
        given:
            vehicleRepository.existsById(_ as Long) >> { Long id -> id == 1L }
        when:
            boolean resultOfDeleting = vehicleService.deleteById(vehicleId)
        then:
            resultOfDeleting == result
        where:
            vehicleId | result
            1L      | true
            2L      | false

    }

    def "GetById"() {
        given:
            initializeCargoBus()
            initializePassengerBus()
            Result expectedBus
        when:
            expectedBus = vehicleService.getById(id,expectedType)

        then:
            1 * vehicleRepository.getVehicleById(id) >> entity
            expectedBus.isSuccess() == status
            expectedBus.getValue() == entity

        where:
            id | entity             | expectedType          | status
            1L | listOfEntity.get(0)| CargoBus.class        | true
            2L | listOfEntity.get(1)| PassengerBus.class    | true
            2L | listOfEntity.get(1)| CargoBus.class        | false
    }
    def "GetByRegistrationNumber"() {
        given:
            initializeCargoBus()
            initializePassengerBus()
            Result expectedBus
        when:
            expectedBus = vehicleService.getByRegistrationNumber(RN,expectedType)

        then:
            1 * vehicleRepository.getByRegistrationNumber(RN) >> entity
            expectedBus.isSuccess() == status
            expectedBus.getValue() == entity

        where:
            RN          | entity             | expectedType          | status
            "AB2020B"   | listOfEntity.get(0)| CargoBus.class        | true
            "AB1934GH"  | listOfEntity.get(1)| PassengerBus.class    | true
            "AB1934GH"  | listOfEntity.get(1)| CargoBus.class        | false
    }
    def "GetBySerialNumber"() {
        given:
            initializeCargoBus()
            initializePassengerBus()
            Result expectedBus
        when:
            expectedBus = vehicleService.getBySerialNumber(SN,expectedType)

        then:
            1 * vehicleRepository.getBySerialNumber(SN) >> entity
            expectedBus.isSuccess() == status
            expectedBus.getValue() == entity

        where:
            SN          | entity             | expectedType          | status
            "3AF21B3"   | listOfEntity.get(0)| CargoBus.class        | true
            "18AD63G5"  | listOfEntity.get(1)| PassengerBus.class    | true
            "3AF2h"     | listOfEntity.get(1)| CargoBus.class        | false
    }
    def "addReservationToVehicle"(){
        given:
            Reserved reserved = new Reserved(LocalDate.of(2024,9,8),LocalTime.of(9,0),LocalTime.of(18,0))

        when: "Successfully adding a reservation"
            Result successResult = vehicleService.addReservationToVehicle(1L,reserved)

        then:
            1 * vehicleRepository.getVehicleById(1L) >> listOfEntity.getFirst()
            !successResult.hasError()

        when: "Failure to successfully add a reservation"
            Result failureResult = vehicleService.addReservationToVehicle(1L,reserved)

        then:
            1 * vehicleRepository.getVehicleById(1L) >> listOfEntity.getFirst()
            failureResult.hasError()
    }
    def "deleteReservationInVehicle"(){
        given:
            Reserved reserved = new Reserved(
                    LocalDate.of(2022, 5, 2),
                    LocalTime.of(14, 40),
                    LocalTime.of(19, 40))

        when: "Successfully adding a reservation"
            Result successResult = vehicleService.deleteReservationInVehicle(1L,reserved)

        then:
            1 * vehicleRepository.getVehicleById(1L) >> listOfEntity.getFirst()
            !successResult.hasError()

        when: "Failure to successfully add a reservation"
            Result failureResult = vehicleService.deleteReservationInVehicle(2L,reserved)

        then:
            1 * vehicleRepository.getVehicleById(2L) >> listOfEntity.getLast()
            failureResult.hasError()
    }
    def "addReservationsToVehicle"(){
        given:
        List<Reserved> reservations = List.of(
                new Reserved(
                        LocalDate.of(2022, 5, 2),
                        LocalTime.of(14, 40),
                        LocalTime.of(19, 40)))

        when: "Successfully adding a reservations"
            Result successResult = vehicleService.deleteReservationsInVehicle(1L,reservations)

        then:
            1 * vehicleRepository.getVehicleById(1L) >> listOfEntity.getFirst()
            !successResult.hasError()

        when: "Successfully adding a reservations"
            Result failureResult = vehicleService.deleteReservationsInVehicle(2L,reservations)

        then:
            1 * vehicleRepository.getVehicleById(2L) >> listOfEntity.getLast()
            failureResult.hasError()
    }
    def "addReservationsToVehicle"(){
        given:
        List<Reserved> reservations = List.of(
                new Reserved(
                        LocalDate.of(2024,9,8),
                        LocalTime.of(9,0),
                        LocalTime.of(16,0)),
                new Reserved(
                        LocalDate.of(2024,9,8),
                        LocalTime.of(16,30),
                        LocalTime.of(22,0)))

        when: "Successfully adding a reservations"
        Result successResult = vehicleService.addReservationsToVehicle(1L,reservations)

        then:
        1 * vehicleRepository.getVehicleById(1L) >> listOfEntity.getFirst()
        !successResult.hasError()

        when: "Successfully adding a reservations"
        Result failureResult = vehicleService.addReservationsToVehicle(1L,reservations)

        then:
        1 * vehicleRepository.getVehicleById(1L) >> listOfEntity.getFirst()
        failureResult.hasError()
    }
    def "filterByAvailability should filter vehicles based on availability"() {
        given:
            def stream = listOfEntity.stream()

        when:
            def result = vehicleService.filterByAvailability(
                    stream,
                    LocalDate.of(2022, 5, 2),
                    LocalTime.of(14, 40),
                    LocalTime.of(19, 40)).collect(Collectors.toList())

        then:
            result.size() == 1
            result.getFirst() == listOfEntity.get(1)
    }

    def "filterByLocation should filter vehicles based on hangar location"() {
        given:
            def stream = listOfEntity.stream()

        when:
            def result = vehicleService.filterByLocation(stream, 4).collect(Collectors.toList())

        then:
            result.size() == 1
            result.getFirst() == listOfEntity.get(0)
    }

    def "filterByInspection should filter vehicles based on inspection need"() {
        given:
        def stream = listOfEntity.stream()

        when:
        LocalDate.now() >> LocalDate.of(2022, 3, 3)
        def result = vehicleService.filterByInspection(stream, 1).collect(Collectors.toList())

        then:
        result.size() == 0
    }

    def "filterByType should filter vehicles based on type"() {
        given:
        def stream = listOfEntity.stream()

        when:
        def result = vehicleService.filterByType(stream, VehicleType.CARGO).collect(Collectors.toList())

        then:
        result.size() == 1
        result.getFirst() == listOfEntity.get(0)
    }
    def "getAllBy should filter vehicles with various criteria"() {
        given:
        vehicleRepository.findAll() >> listOfEntity

        when:
        def result = vehicleService.getAllBy(
                LocalDate.of(2022, 5, 2),
                LocalTime.of(14, 40),
                LocalTime.of(19, 40),
                true,
                5,
                0,
                null,
                [] as Long[]).get()

        then:
        result.size() == 1
        result.contains(listOfEntity.find { it.serialNumber == "3AF21B3" })
    }
    def "getAllBy should return all vehicles when no criteria are provided"() {
        given:
        vehicleRepository.findAll() >> listOfEntity

        when:
        def result = vehicleService.getAllBy(null, null, null, null, null, null, null, [] as Long[]).get()

        then:
        result.size() == 2
        result.containsAll(listOfEntity)
    }
    def "getLocationByBusId"() {
        given:
            initializeCargoBus()
            initializePassengerBus()
            Result queriedLocation
        when:
            queriedLocation = vehicleService.getLocationByBusId(id)

        then:
            vehicleRepository.getVehicleById(id) >> entity
            LocalDate.now() >> LocalDateTime.of(2023, 5, 2,15,0)
            queriedLocation.hasError() == hasError
            queriedLocation.getValue() == location

        where:
            id | entity             | hasError  | location
            1L | listOfEntity.get(0)| false     | listOfEntity.get(0).getBusLocation()
            2L | listOfEntity.get(1)| true      | null
    }

    /*def "changeBusLocation"() {
        given:
            BusLocation newLocation = new BusLocation(1,1)

        when:


        then:
    }
    def "changeBusLocation"() {

    }*/
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

    def "CountAll"() {

    }
    def "CountAllServiceable"() {

    }
    def "CountAllNotServiceable"() {

    }

    private <T extends Vehicle, E extends VehicleDTO> E convertEntityToDto(T entity) {
        return  VehicleMapperManager.convertEntityToDto(entity)
    }
    private <T extends VehicleDTO,E extends Vehicle> E convertDtoToEntity(T dto) {
        return VehicleMapperManager.convertDtoToEntity(dto)
    }
}
