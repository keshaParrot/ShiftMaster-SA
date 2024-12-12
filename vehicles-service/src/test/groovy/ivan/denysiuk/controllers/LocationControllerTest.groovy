package ivan.denysiuk.controllers

import ivan.denysiuk.customClasses.Result
import ivan.denysiuk.domain.entity.BusLocation
import ivan.denysiuk.service.interfaces.LocationService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

class LocationControllerTest extends Specification {

    def locationService = Mock(LocationService)
    def locationController = new LocationController(locationService)
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(locationController).build()
    public final static String URL_PATH = LocationController.URL_PATH;

    def "getAvailableParkingSpot - should return available parking spots from service"() {

    }

    def "getLocationByVehicleId - should return 200 OK with location data for valid busId"() {
        given: "A valid busId"
        Long busId = 1L
        BusLocation busLocation = new BusLocation(1, 1)
        Result<BusLocation> result = Result.success(busLocation)

        locationService.getLocationByVehicleId(busId) >> result

        when: "The getLocationByVehicleId endpoint is called"
        def response = mockMvc.perform(get(URL_PATH + "/getLocation")
                .param("busId", busId as String))
                .andReturn().response

        then: "The response should be 200 OK with the correct location data"
        response.status == HttpStatus.OK.value()
        response.contentAsString.contains("1")
    }

    def "getLocationByVehicleId - should return 404 NOT FOUND when busId does not exist"() {
        given: "A non-existing busId"
        Long busId = 2L
        Result<BusLocation> result = Result.failure("Bus not found")

        locationService.getLocationByVehicleId(busId) >> result

        when: "The getLocationByVehicleId endpoint is called"
        def response = mockMvc.perform(get(URL_PATH + "/getLocation")
                .param("busId", busId as String))
                .andReturn().response

        then: "The response should be 404 NOT FOUND with an error message"
        response.status == HttpStatus.NOT_FOUND.value()
        response.contentAsString.contains("Bus not found")
    }

    def "getLocationByVehicleId - should return 500 INTERNAL SERVER ERROR on unexpected exception"() {
        given: "A busId that triggers an exception"
        Long busId = 3L

        locationService.getLocationByVehicleId(busId) >> { throw new RuntimeException("Database error") }

        when: "The getLocationByVehicleId endpoint is called"
        def response = mockMvc.perform(get(URL_PATH + "/getLocation")
                .param("busId", busId as String))
                .andReturn().response

        then: "The response should be 500 INTERNAL SERVER ERROR with the error message"
        response.status == HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.contentAsString.contains("An unexpected error occurred: Database error")
    }

    def "ParkVehicle - should return 200 OK when vehicle is successfully parked"() {
        given: "A valid busId, hangar, and platform"
        Long busId = 1L
        int hangar = 2
        int platform = 3
        Result<Boolean> result = Result.success(true)

        locationService.parkVehicleOnHangar(busId, hangar, platform) >> result

        when: "The parkVehicle endpoint is called"
        def response = mockMvc.perform(put(URL_PATH + "/park")
                .param("busId", busId as String)
                .param("hangar", hangar as String)
                .param("platform", platform as String))
                .andReturn().response

        then: "The response should be 200 OK with a success message"
        response.status == HttpStatus.OK.value()
        response.contentAsString.contains('"value":true')
    }

    def "ParkVehicle - should return 400 BAD REQUEST when vehicle parking fails"() {
        given: "A valid busId, hangar, and platform but an error occurs"
        Long busId = 2L
        int hangar = 5
        int platform = 10
        Result<Boolean> result = Result.failure("Invalid hangar or platform")

        locationService.parkVehicleOnHangar(busId, hangar, platform) >> result

        when: "The parkVehicle endpoint is called"
        def response = mockMvc.perform(put(URL_PATH + "/park")
                .param("busId", busId as String)
                .param("hangar", hangar as String)
                .param("platform", platform as String))
                .andReturn().response

        then: "The response should be 400 BAD REQUEST with an error message"
        response.status == HttpStatus.BAD_REQUEST.value()
        response.contentAsString.contains("Invalid hangar or platform")
    }

    def "ParkVehicle - should return 500 INTERNAL SERVER ERROR on unexpected exception"() {
        given: "A busId that triggers an unexpected exception"
        Long busId = 3L
        int hangar = 1
        int platform = 1

        locationService.parkVehicleOnHangar(busId, hangar, platform) >> { throw new RuntimeException("Database error") }

        when: "The parkVehicle endpoint is called"
        def response = mockMvc.perform(put(URL_PATH + "/park")
                .param("busId", busId as String)
                .param("hangar", hangar as String)
                .param("platform", platform as String))
                .andReturn().response

        then: "The response should be 500 INTERNAL SERVER ERROR with the error message"
        response.status == HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.contentAsString.contains("An unexpected error occurred: Database error")
    }

    def "should return 200 OK when spot is available"() {
        given: "A valid hangar and platform"
        int hangar = 1
        int platform = 2
        boolean locationExists = true

        locationService.isSpotAvailable(hangar, platform) >> locationExists

        when: "The isSpotAvailable endpoint is called"
        def response = mockMvc.perform(get(URL_PATH + "/isAvailable")
                .param("hangar", hangar as String)
                .param("platform", platform as String))
                .andReturn().response

        then: "The response should be 200 OK with a success message"
        response.status == HttpStatus.OK.value()
        response.contentAsString == "Location with provided hangar and platform exist"
    }

    def "should return 404 NOT FOUND when spot is not available"() {
        given: "A valid hangar and platform but the spot is either occupied or does not exist"
        int hangar = 3
        int platform = 4
        boolean locationExists = false

        locationService.isSpotAvailable(hangar, platform) >> locationExists

        when: "The isSpotAvailable endpoint is called"
        def response = mockMvc.perform(get(URL_PATH + "/isAvailable")
                .param("hangar", hangar as String)
                .param("platform", platform as String))
                .andReturn().response

        then: "The response should be 404 NOT FOUND with an error message"
        response.status == HttpStatus.NOT_FOUND.value()
        response.contentAsString == "Location with provided hangar and platform is either occupied or does not exist."
    }
}
