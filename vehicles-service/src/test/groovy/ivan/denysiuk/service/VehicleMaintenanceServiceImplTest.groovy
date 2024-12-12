package ivan.denysiuk.service

import ivan.denysiuk.repository.MaintenanceMessageRepository
import ivan.denysiuk.domain.entity.Vehicle
import ivan.denysiuk.repository.VehicleRepository
import ivan.denysiuk.domain.entity.MaintenanceMessage
import ivan.denysiuk.domain.enumeration.MessageStatus
import spock.lang.Specification

class VehicleMaintenanceServiceImplTest extends Specification {

    def vehicleRepository = Mock(VehicleRepository)
    def maintenanceMessageRepository = Mock(MaintenanceMessageRepository)
    def vehicleMaintenanceService = new VehicleMaintenanceServiceImpl(vehicleRepository,maintenanceMessageRepository)


    def "test checkVehiclesForMaintenance"() {
        given:
        def vehicle1 = Mock(Vehicle)
        def vehicle2 = Mock(Vehicle)
        vehicle1.isNeedInspection() >> 1
        vehicle1.getRegistrationNumber() >> "ABC123"
        vehicle2.isNeedInspection() >> 2
        vehicle2.getRegistrationNumber() >> "XYZ456"
        vehicleRepository.findAll() >> [vehicle1, vehicle2]

        when:
        vehicleMaintenanceService.checkVehiclesForMaintenance()

        then:
        2 * maintenanceMessageRepository.save(_)
    }

    def "getAllBy - should return all messages when status is null"() {
        given:
        def messages = [
                new MaintenanceMessage(id: 1, status: MessageStatus.OPEN, message: "Check engine"),
                new MaintenanceMessage(id: 2, status: MessageStatus.CLOSED, message: "Oil changed")
        ]
        maintenanceMessageRepository.findAll() >> messages

        when:
        def result = vehicleMaintenanceService.getAllBy(null)

        then:
        result.isPresent()
        result.get() == messages
    }

    def "getAllBy - should return only OPEN messages when status is OPEN"() {
        given:
        def messages = [
                new MaintenanceMessage(id: 1, status: MessageStatus.OPEN, message: "Check engine"),
                new MaintenanceMessage(id: 2, status: MessageStatus.CLOSED, message: "Oil changed"),
                new MaintenanceMessage(id: 3, status: MessageStatus.OPEN, message: "Brake inspection")
        ]
        maintenanceMessageRepository.findAll() >> messages

        when:
        def result = vehicleMaintenanceService.getAllBy(MessageStatus.OPEN)

        then:
        result.isPresent()
        result.get().size() == 2
        result.get().every { it.status == MessageStatus.OPEN }
    }

    def "getAllBy - should return only CLOSED messages when status is CLOSED"() {
        given:
        def messages = [
                new MaintenanceMessage(id: 1, status: MessageStatus.OPEN, message: "Check engine"),
                new MaintenanceMessage(id: 2, status: MessageStatus.CLOSED, message: "Oil changed"),
                new MaintenanceMessage(id: 3, status: MessageStatus.CLOSED, message: "Tire replacement")
        ]
        maintenanceMessageRepository.findAll() >> messages

        when:
        def result = vehicleMaintenanceService.getAllBy(MessageStatus.CLOSED)

        then:
        result.isPresent()
        result.get().size() == 2
        result.get().every { it.status == MessageStatus.CLOSED }
    }

    def "getAllBy - should return empty optional when no messages match the status"() {
        given:
        def messages = [
                new MaintenanceMessage(id: 1, status: MessageStatus.OPEN, message: "Check engine")
        ]
        maintenanceMessageRepository.findAll() >> messages

        when:
        def result = vehicleMaintenanceService.getAllBy(MessageStatus.CLOSED)

        then:
        !result.isPresent()

    }

    def "getById - should return success result when message exists"() {
        given:
        def message = new MaintenanceMessage(id: 1, status: MessageStatus.OPEN, message: "Check engine")
        maintenanceMessageRepository.findById(1L) >> Optional.of(message)

        when:
        def result = vehicleMaintenanceService.getById(1L)

        then:
        result.isSuccess()
        result.value == message
    }

    def "getById - should return failure result when message does not exist"() {
        given:
        maintenanceMessageRepository.findById(2L) >> Optional.empty()

        when:
        def result = vehicleMaintenanceService.getById(2L)

        then:
        result.hasError()
        result.getMessage() == "Message with id: 2 not found"
    }

    def "test closeMessage when message does not exist"() {
        given:
        Long id = 1L
        Long empId = 100L
        maintenanceMessageRepository.findById(id) >> Optional.empty()

        when:
        def result = vehicleMaintenanceService.close(id, empId)

        then:
        result.hasError()
        result.message == "Message with provided id: 1 does not exist"
    }

    def "test closeMessage when message is already closed"() {
        given:
        Long id = 1L
        Long empId = 100L
        def message = Mock(MaintenanceMessage)
        message.getStatus() >> MessageStatus.CLOSED
        maintenanceMessageRepository.findById(id) >> Optional.of(message)

        when:
        def result = vehicleMaintenanceService.close(id, empId)

        then:
        result.hasError()
        result.message == "Message with provided id: 1 already closed"
    }

    def "test closeMessage successfully"() {
        given:
        Long id = 1L
        Long empId = 100L
        def message = Mock(MaintenanceMessage)
        message.getStatus() >> MessageStatus.OPEN
        maintenanceMessageRepository.findById(id) >> Optional.of(message)

        when:
        def result = vehicleMaintenanceService.close(id, empId)

        then:
        1 * message.setStatus(MessageStatus.CLOSED)
        1 * message.setMessageOwner(empId)
        1 * maintenanceMessageRepository.save(message)
        result.success
        result.message == "Message with provided id: 1 is closed"
    }
}
