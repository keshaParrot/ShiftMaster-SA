package ivan.denysiuk.service

import ivan.denysiuk.customClasses.OptionalCollector
import ivan.denysiuk.customClasses.Result
import ivan.denysiuk.domain.dto.CargoBusDTO
import ivan.denysiuk.domain.dto.PassengerBusDTO
import ivan.denysiuk.domain.dto.VehicleDTO
import ivan.denysiuk.domain.entity.BusLocation
import ivan.denysiuk.domain.entity.CargoBus
import ivan.denysiuk.domain.entity.PassengerBus
import ivan.denysiuk.domain.entity.Reserved
import ivan.denysiuk.domain.entity.Vehicle
import ivan.denysiuk.domain.entity.VehicleTest
import ivan.denysiuk.domain.enumeration.VehicleType
import ivan.denysiuk.repository.VehicleRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

class VehicleServiceImplTest extends Specification {

    def vehicleRepository = Mock(VehicleRepository)
    def vehicleService = new VehicleServiceImpl(vehicleRepository)

    def "SaveToSystem - successful addition Passenger bus"() {
        given:
        def passengerBusDTO = PassengerBusDTO.builder()
                .serialNumber("123456")
                .registrationNumber("AB1234CD")
                .brand("Volvo")
                .numberOfSeats(50)
                .build()

        vehicleRepository.getBySerialNumber("123456") >> Optional.empty()

        def savedVehicle = Mock(Vehicle) {
            getId() >> 1L
        }

        vehicleRepository.save(_ as Vehicle) >> savedVehicle

        when:
        Result<Vehicle> result = vehicleService.saveToSystem(passengerBusDTO)

        then:
        result.isSuccess()
        result.getValue() == savedVehicle
        result.getMessage() == "Vehicle was saved successfully"
    }
    def "SaveToSystem - successful addition Cargo bus"() {
        given:
        def cargoBusDTO = CargoBusDTO.builder()
                .serialNumber("789012")
                .registrationNumber("XY5678ZT")
                .brand("MAN")
                .loadCapacity("10 tons")
                .build()

        vehicleRepository.getBySerialNumber("789012") >> Optional.empty()

        def savedVehicle = Mock(Vehicle) {
            getId() >> 2L
        }

        vehicleRepository.save(_ as Vehicle) >> savedVehicle

        when:
        Result<Vehicle> result = vehicleService.saveToSystem(cargoBusDTO)

        then:
        result.isSuccess()
        result.getValue() == savedVehicle
        result.getMessage() == "Vehicle was saved successfully"
    }

    def "SaveToSystem - failure addition vehicles (Vehicle exist)"() {
        given:
        def passengerBusDTO = PassengerBusDTO.builder()
                .serialNumber("123456")
                .registrationNumber("AB1234CD")
                .brand("Volvo")
                .numberOfSeats(50)
                .build()

        def existingVehicle = Mock(Vehicle) {
            getId() >> 3L
        }

        vehicleRepository.getBySerialNumber("123456") >> Optional.of(existingVehicle)

        when:
        Result<Vehicle> result = vehicleService.saveToSystem(passengerBusDTO)

        then:
        !result.isSuccess()
        result.getMessage() == "Vehicle with registration number: '123456' already exists with ID: 3"
    }
    def "updateById - successful updating Passenger bus"(){
        given:
        def passengerBusDTO = PassengerBusDTO.builder()
                .serialNumber("123456")
                .registrationNumber("AB1234CD")
                .brand("Volvo")
                .numberOfSeats(50)
                .build()
        def existingVehicle = new PassengerBus(id: 1L, registrationNumber: "AB1234CD", brand: "Volvo", numberOfSeats: 50)
        vehicleRepository.existsById(1L) >> true
        vehicleRepository.save(_ as Vehicle) >> existingVehicle

        when:
        Result<PassengerBus> result = vehicleService.updateById(passengerBusDTO, 1L)

        then:
        result.isSuccess()
        result.getValue() == existingVehicle
        result.getMessage() == "Vehicle was updated successfully"
    }
    def "updateById - successful updating Cargo bus"(){
        given:
        def cargoBusDTO = CargoBusDTO.builder()
                .serialNumber("789012")
                .registrationNumber("XY5678ZT")
                .brand("MAN")
                .loadCapacity("10 tons")
                .build()

        def existingVehicle = new CargoBus(id: 2L, registrationNumber: "XY5678ZT", brand: "MAN", loadCapacity: "10 tons")
        vehicleRepository.existsById(2L) >> true
        vehicleRepository.save(_ as Vehicle) >> existingVehicle

        when:
        Result<CargoBus> result = vehicleService.updateById(cargoBusDTO, 2L)

        then:
        result.isSuccess()
        result.getValue() == existingVehicle
        result.getMessage() == "Vehicle was updated successfully"
    }
    def "updateById - failure updating Cargo bus (vehicle not found)"(){
        given:
        def passengerBusDTO = PassengerBusDTO.builder()
                .serialNumber("123456")
                .registrationNumber("AB1234CD")
                .brand("Volvo")
                .numberOfSeats(50)
                .build()

        vehicleRepository.existsById(999L) >> false

        when:
        Result<PassengerBus> result = vehicleService.updateById(passengerBusDTO, 999L)

        then:
        !result.isSuccess()
        result.getMessage() == "Vehicle with provided id: 999 does not exist"
    }
    def "addReservationToVehicle - successfully adding a reservation for an available vehicle"() {
        given:
        def reserved = new Reserved(date: LocalDate.now(), from: LocalTime.of(9, 0), to: LocalTime.of(11, 0))
        def vehicle = new PassengerBus(id: 1L, registrationNumber: "AB1234CD", brand: "Volvo", numberOfSeats: 50, whenReserved: [])
        vehicleRepository.getVehicleById(1L) >> Optional.of(vehicle)
        vehicle.isAvailable(_, _, _) >> 0
        vehicleRepository.save(_ as Vehicle) >> vehicle

        when:
        Result<Boolean> result = vehicleService.addReservationToVehicle(1L, reserved)

        then:
        result.isSuccess()
        result.getValue()
        result.getMessage() == "the reservation of the bus: AB1234CD, on date " + reserved.getDate() + ", in the hours " + reserved.getFrom()+"-"+reserved.getTo() +" was added successfully"
        vehicle.whenReserved.size() == 1
        vehicle.whenReserved.contains(reserved)
    }

    def "addReservationToVehicle - Failure to successfully add a reservation when the vehicle does not exist"() {
        given:
        def reserved = new Reserved(date: LocalDate.now(), from: LocalTime.of(9, 0), to: LocalTime.of(11, 0))
        vehicleRepository.getVehicleById(999L) >> Optional.empty()

        when:
        Result<Boolean> result = vehicleService.addReservationToVehicle(999L, reserved)

        then:
        !result.isSuccess()
        result.getMessage() == "Vehicle with provided id: 999 does not exist"
    }

    def "addReservationToVehicle - Failure to add a reservation when a break is needed between shifts"() {
        given:
        def reserved = new Reserved(date: LocalDate.now(), from: LocalTime.of(9, 0), to: LocalTime.of(11, 0))
        def reserved2 = new Reserved(date: LocalDate.now(), from: LocalTime.of(6, 0), to: LocalTime.of(9, 0))
        def vehicle = new PassengerBus(id: 2L, registrationNumber: "XY5678ZT", brand: "MAN", numberOfSeats: 50, whenReserved: [reserved2])
        vehicleRepository.getVehicleById(2L) >> Optional.of(vehicle)
        vehicle.isAvailable(_, _, _) >> 2

        when:
        Result<Boolean> result = vehicleService.addReservationToVehicle(2L, reserved)

        then:
        result.hasError()
        result.getMessage() == "Between shifts Vehicle must to have 15 minutes break"
    }

    def "addReservationToVehicle - unsuccessful addition of a reservation when the vehicle is already booked for that time"() {
        given:
        def reserved = new Reserved(date: LocalDate.now(), from: LocalTime.of(9, 0), to: LocalTime.of(11, 0))
        def existingReservation = new Reserved(date: LocalDate.now(), from: LocalTime.of(8, 0), to: LocalTime.of(10, 0))
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", numberOfSeats: 50, whenReserved: [existingReservation])
        vehicleRepository.getVehicleById(3L) >> Optional.of(vehicle)
        vehicle.isAvailable(_, _, _) >> 1

        when:
        Result<Boolean> result = vehicleService.addReservationToVehicle(3L, reserved)

        then:
        !result.isSuccess()
        result.getMessage() == "Between shifts Vehicle must to have 15 minutes break"
    }

    def "deleteById"() {
        given:
            vehicleRepository.existsById(_ as Long) >> { Long id -> id == 1L }
        when:
            boolean resultOfDeleting = vehicleService.deleteById(vehicleId)
        then:
            resultOfDeleting == result
        where:
            vehicleId   | result
            1L          | true
            2L          | false

    }
    def "deleteReservationInVehicle - successful deleting reservation"(){
        given:
        def existingReservation = new Reserved(date: LocalDate.of(2024,1,12), from: LocalTime.of(8, 0), to: LocalTime.of(10, 0))
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", numberOfSeats: 50, whenReserved: [existingReservation])
        vehicleRepository.getVehicleById(3L) >> Optional.of(vehicle)

        when:
        Result<Boolean> result = vehicleService.deleteReservationInVehicle (3L, existingReservation)

        then:
        result.isSuccess()
        result.getMessage() == "the reservation of the bus: CD9876EF, on date 2024-01-12, in the hours 08:00-10:00 was deleted successfully"
    }
    def "deleteReservationInVehicle - failure deleting, vehicle is not exist"(){
        given:
        def reservedToDelete = new Reserved(date: LocalDate.of(2024,1,12), from: LocalTime.of(8, 0), to: LocalTime.of(10, 0))
        vehicleRepository.getVehicleById(3L) >> Optional.empty()

        when:
        Result<Boolean> result = vehicleService.deleteReservationInVehicle (3L, reservedToDelete)

        then:
        !result.isSuccess()
        result.getMessage() == "Vehicle with provided id=3 does not exist"
    }
    def "deleteReservationInVehicle - failure deleting, vehicle dont have reservation on this time"(){
        given:
        def reservedToDelete = new Reserved(date: LocalDate.of(2024,1,12), from: LocalTime.of(8, 0), to: LocalTime.of(10, 0))
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", numberOfSeats: 50)
        vehicleRepository.getVehicleById(3L) >> Optional.of(vehicle)

        when:
        Result<Boolean> result = vehicleService.deleteReservationInVehicle (3L, reservedToDelete)

        then:
        !result.isSuccess()
        result.getMessage() == "the bus: CD9876EF, dont have reservation on date 2024-01-12, in the hours 08:00-10:00"
    }
    //TODO we need to implement theses methods
    def "addReservationsInVehicle"(){}
    def "deleteReservationsInVehicle"(){}

    def "GetById - successful getting Cargo Bus"() {
        given:
        def vehicle = new CargoBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", loadCapacity: 50)
        vehicleRepository.getVehicleById(3L) >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getById(3L, CargoBus.class)

        then:
        result.isSuccess()
        result.value == vehicle
    }
    def "GetById - successful getting Passenger Bus"() {
        given:
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", numberOfSeats: 50)
        vehicleRepository.getVehicleById(3L) >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getById(3L, PassengerBus.class)

        then:
        result.isSuccess()
        result.value == vehicle
    }
    def "GetById - failure getting Bus, the vehicle does not exist"() {
        given:
        vehicleRepository.getVehicleById(3L) >> Optional.empty()

        when:
        Result<Vehicle> result = vehicleService.getById(3L, CargoBus.class)

        then:
        !result.isSuccess()
        result.getMessage() == "Vehicle with provided id: 3 does not exist"
    }
    def "GetById - failure getting Bus, provided wrong expected type"() {
        given:
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", numberOfSeats: 50)
        vehicleRepository.getVehicleById(3L) >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getById(3L, CargoBus.class)

        then:
        result.hasError()
        result.getMessage() == "An error occurred when receiving a bus of the CargoBus, the bus with this identifier is a PassengerBus"
    }

    def "GetByRegistrationNumber - successful getting Cargo Bus"() {
        given:
        def vehicle = new CargoBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", loadCapacity: 50)
        vehicleRepository.getByRegistrationNumber("CD9876EF") >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getByRegistrationNumber("CD9876EF", CargoBus.class)

        then:
        result.isSuccess()
        result.value == vehicle
    }
    def "GetByRegistrationNumber - successful getting Passenger Bus"() {
        given:
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", numberOfSeats: 50)
        vehicleRepository.getByRegistrationNumber("CD9876EF") >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getByRegistrationNumber("CD9876EF", PassengerBus.class)

        then:
        result.isSuccess()
        result.value == vehicle
    }
    def "GetByRegistrationNumber - failure getting Bus, the vehicle does not exist"() {
        given:
        vehicleRepository.getByRegistrationNumber("CD9876EF") >> Optional.empty()

        when:
        Result<Vehicle> result = vehicleService.getByRegistrationNumber("CD9876EF", CargoBus.class)

        then:
        !result.isSuccess()
        result.getMessage() == "Vehicle with provided registration number: CD9876EF does not exist"
    }
    def "GetByRegistrationNumber - failure getting Bus, provided wrong expected type"() {
        given:
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", brand: "Mercedes", numberOfSeats: 50)
        vehicleRepository.getByRegistrationNumber("CD9876EF") >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getByRegistrationNumber("CD9876EF", CargoBus.class)

        then:
        result.hasError()
        result.getMessage() == "An error occurred when receiving a bus of the CargoBus, the bus with this identifier is a PassengerBus"
    }

    def "GetBySerialNumber - successful getting Cargo Bus"() {
        given:
        def vehicle = new CargoBus(id: 3L, registrationNumber: "CD9876EF", serialNumber: "1234", brand: "Mercedes", loadCapacity: 50)
        vehicleRepository.getBySerialNumber("1234") >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getBySerialNumber("1234", CargoBus.class)

        then:
        result.isSuccess()
        result.value == vehicle
    }
    def "GetBySerialNumber - successful getting Passenger Bus"() {
        given:
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", serialNumber: "1234",brand: "Mercedes", numberOfSeats: 50)
        vehicleRepository.getBySerialNumber("1234") >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getBySerialNumber("1234", PassengerBus.class)

        then:
        result.isSuccess()
        result.value == vehicle
    }
    def "GetBySerialNumber - failure getting Bus, the vehicle does not exist"() {
        given:
        vehicleRepository.getBySerialNumber("1234") >> Optional.empty()

        when:
        Result<Vehicle> result = vehicleService.getBySerialNumber("1234", CargoBus.class)

        then:
        !result.isSuccess()
        result.getMessage() == "Vehicle with provided serial number: 1234 does not exist"
    }
    def "GetBySerialNumber - failure getting Bus, provided wrong expected type"() {
        given:
        def vehicle = new PassengerBus(id: 3L, registrationNumber: "CD9876EF", serialNumber: "1234", brand: "Mercedes", numberOfSeats: 50)
        vehicleRepository.getBySerialNumber("1234") >> Optional.of(vehicle)

        when:
        Result<Vehicle> result = vehicleService.getBySerialNumber("1234", CargoBus.class)

        then:
        result.hasError()
        result.getMessage() == "An error occurred when receiving a bus of the CargoBus, the bus with this identifier is a PassengerBus"
    }

    def "filterByAvailability - filter all availability"() {
        given:
        def vehicle1 = new PassengerBus(id: 1L, registrationNumber: "AB1234CD", brand: "Volvo", numberOfSeats: 50, whenReserved: [])
        def vehicle2 = new PassengerBus(id: 2L, registrationNumber: "XY5678ZT", brand: "Mercedes", numberOfSeats: 50, whenReserved: [])
        def reserved = new Reserved(date: LocalDate.now(), from: LocalTime.of(9, 0), to: LocalTime.of(11, 0))
        vehicle2.whenReserved = [reserved]
        def vehicles = [vehicle1, vehicle2].stream()

        when:
        def result = vehicleService.filterByAvailability(vehicles, LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(10, 0)).collect(OptionalCollector.toOptionalList())

        then:
        result.get().size() == 1
        result.get().get(0) == vehicle1
    }

    def "filterByLocation - filter by location"() {
        given:
        def vehicle1 = new PassengerBus(id: 1L, registrationNumber: "AB1234CD", brand: "Volvo", numberOfSeats: 50, whenReserved: [], busLocation: new BusLocation(hangar: 1))
        def vehicle2 = new PassengerBus(id: 2L, registrationNumber: "XY5678ZT", brand: "Mercedes", numberOfSeats: 50, whenReserved: [], busLocation: new BusLocation(hangar: 2))
        def vehicles = [vehicle1, vehicle2].stream()

        when:
        def result = vehicleService.filterByLocation(vehicles, 1).collect(OptionalCollector.toOptionalList())

        then:
        result.get().size() == 1
        result.get().get(0) == vehicle1
    }

    def "filterByInspection - filter by technical inspection"() {
        given:
        def vehicle1 = new PassengerBus(id: 1L, registrationNumber: "AB1234CD", brand: "Volvo", numberOfSeats: 50, whenReserved: [],lastAnnualMaintenance: LocalDate.now(),lastQuarterlyMaintenance: LocalDate.now())
        def vehicle2 = new PassengerBus(id: 2L, registrationNumber: "XY5678ZT", brand: "Mercedes", numberOfSeats: 50, whenReserved: [],lastAnnualMaintenance: LocalDate.of(2022,1,1),lastQuarterlyMaintenance: LocalDate.of(2022,1,1))
        def vehicles = [vehicle1, vehicle2].stream()

        when:
        def result = vehicleService.filterByInspection(vehicles, 0).collect(OptionalCollector.toOptionalList())

        then:
        result.get().size() == 1
        result.get().get(0) == vehicle1
    }

    def "getAllBy - filter by all"() {
        given:
        def vehicle1 = new PassengerBus(id: 1L, registrationNumber: "AB1234CD", brand: "Volvo", numberOfSeats: 50, whenReserved: [], busLocation: new BusLocation(hangar: 1),lastAnnualMaintenance: LocalDate.now(),lastQuarterlyMaintenance: LocalDate.of(2022,1,1))
        def vehicle2 = new PassengerBus(id: 2L, registrationNumber: "XY5678ZT", brand: "Mercedes", numberOfSeats: 50, whenReserved: [],lastAnnualMaintenance: LocalDate.of(2022,1,1),lastQuarterlyMaintenance: LocalDate.of(2022,1,1))
        def reserved = new Reserved(date: LocalDate.now(), from: LocalTime.of(9, 0), to: LocalTime.of(11, 0))
        vehicle2.whenReserved = [reserved]
        def vehicles = [vehicle1, vehicle2]

        vehicleRepository.findAll() >> vehicles
        vehicleRepository.findByIdIn(_) >> vehicles

        when:
        def result = vehicleService.getAllBy(LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(10, 0), true, 1, 1, VehicleType.PASSENGER, [1L, 2L] as Long[])

        then:
        result.isPresent()
        result.get().size() == 1
        result.get().get(0) == vehicle1
    }


    def "CountAll"() {

    }
    def "CountAllServiceable"() {

    }
    def "CountAllNotServiceable"() {

    }

}
