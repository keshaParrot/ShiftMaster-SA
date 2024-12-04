package ivan.denysiuk.service

import ivan.denysiuk.customClasses.Result
import ivan.denysiuk.domain.entity.BusLocation
import ivan.denysiuk.domain.entity.PassengerBus
import ivan.denysiuk.domain.entity.Vehicle
import ivan.denysiuk.repository.VehicleRepository
import spock.lang.Specification


class LocationServiceImplTest extends Specification {

    def vehicleRepository = Mock(VehicleRepository)
    def locationService = new LocationServiceImpl(vehicleRepository)

    def "getAvailableParkingSpots - returns correct available spots"() {
        given:

        vehicleRepository.findAllOccupiedLocations() >> [
                new BusLocation(hangar: 1, platform: 3),
                new BusLocation(hangar: 2, platform: 5)
        ]

        when:
        def result = locationService.getAvailableParkingSpots()

        then:
        result.size() == 2
        result[1] == [1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        result[2] == [1, 2, 3, 4, 6, 7, 8]
    }

    def "parkVehicleOnHangar - successful parking"() {
        given:
        def vehicle = new PassengerBus(id: 1L)

        vehicleRepository.getVehicleById(1L) >> Optional.of(vehicle)
        vehicleRepository.isLocationOccupied(1, 3) >> false

        when:
        Result<Boolean> result = locationService.parkVehicleOnHangar(1L, 1, 3)

        then:
        result.isSuccess()
        result.getMessage() == "Bus was parked successfully on hangar: 1 platform: 3"
        1 * vehicleRepository.save(_)
    }

    def "parkVehicleOnHangar - spot not available"() {
        given:
        def vehicle = new PassengerBus(id: 1L)

        vehicleRepository.getVehicleById(1L) >> Optional.of(vehicle)
        vehicleRepository.isLocationOccupied(1, 3) >> true

        when:
        Result<Boolean> result = locationService.parkVehicleOnHangar(1L, 1, 3)

        then:
        !result.isSuccess()
        result.getMessage() == "Park spot: 3 on hangar: 1 is not available"
        0 * vehicleRepository.save(_)
    }

    def "getLocationByVehicleId - returns location if present"() {
        given:
        def busLocation = new BusLocation(hangar: 1, platform: 2)
        def vehicle = new PassengerBus(id: 1L, busLocation: busLocation)

        vehicleRepository.getVehicleById(1L) >> Optional.of(vehicle)

        when:
        Result<BusLocation> result = locationService.getLocationByVehicleId(1L)

        then:
        result.isSuccess()
        result.getValue() == busLocation
    }

    def "getLocationByVehicleId - returns failure if location is null"() {
        given:
        def vehicle = new PassengerBus(id: 1L, busLocation: null)

        vehicleRepository.getVehicleById(1L) >> Optional.of(vehicle)

        when:
        Result<BusLocation> result = locationService.getLocationByVehicleId(1L)

        then:
        !result.isSuccess()
        result.getMessage() == "Error while getting location for vehicle. This vehicle can be on trip"
    }

    def "isSpotAvailable - spot is available"() {
        given:

        vehicleRepository.isLocationOccupied(1, 2) >> false

        when:
        boolean result = locationService.isSpotAvailable(1, 2)

        then:
        result
    }

    def "isSpotAvailable - spot is occupied"() {
        given:

        vehicleRepository.isLocationOccupied(1, 2) >> true

        when:
        boolean result = locationService.isSpotAvailable(1, 2)

        then:
        !result
    }

    def "isSpotExist returns true when the spot exists"() {
        expect:
        locationService.isSpotExist(hangar, platform) == result

        where:
        hangar | platform || result
        1      | 5        || true
        2      | 8        || true
    }

    def "isSpotExist returns false when the platform exceeds the available spots"() {
        expect:
        locationService.isSpotExist(hangar, platform) == result

        where:
        hangar | platform || result
        1      | 13       || false
        2      | 9        || false
    }

    def "isSpotExist returns false when the hangar does not exist"() {
        expect:
        locationService.isSpotExist(hangar, platform) == result

        where:
        hangar | platform || result
        3      | 1        || false
    }

    def "isSpotExist returns false for invalid platform numbers"() {
        expect:
        locationService.isSpotExist(hangar, platform) == result

        where:
        hangar | platform || result
        1      | 0        || false
        1      | -1       || false
    }
}
