package ivan.denysiuk.service

import ivan.denysiuk.domain.dto.RequestCreateCargoBus
import ivan.denysiuk.domain.dto.RequestCreateVehicle
import ivan.denysiuk.domain.dto.RequestCreatePassengerBus
import ivan.denysiuk.domain.entity.CargoBus
import ivan.denysiuk.domain.entity.PassengerBus
import spock.lang.Specification

class VehicleFactoryTest extends Specification {

    def "should create PassengerBus with correct fields"() {
        given:
        def request = new RequestCreatePassengerBus(
                serialNumber: "12345",
                registrationNumber: "AB-123-CD",
                brand: "Mercedes",
                numberOfSeats: 50
        )

        when:
        def result = VehicleFactory.createVehicle(request)

        then:
        result instanceof PassengerBus
        result.serialNumber == "12345"
        result.registrationNumber == "AB-123-CD"
        result.brand == "Mercedes"
        ((PassengerBus) result).numberOfSeats == 50
    }

    def "should create CargoBus with correct fields"() {
        given:
        def request = new RequestCreateCargoBus(
                serialNumber: "67890",
                registrationNumber: "XY-456-ZW",
                brand: "Volvo",
                loadCapacity: "10 tons"
        )

        when:
        def result = VehicleFactory.createVehicle(request)

        then:
        result instanceof CargoBus
        result.serialNumber == "67890"
        result.registrationNumber == "XY-456-ZW"
        result.brand == "Volvo"
        ((CargoBus) result).loadCapacity == "10 tons"
    }

    def "should return null for unknown RequestCreateVehicle subtype"() {
        given:
        def request = Mock(RequestCreateVehicle)

        when:
        def result = VehicleFactory.createVehicle(request)

        then:
        result == null
    }


}
