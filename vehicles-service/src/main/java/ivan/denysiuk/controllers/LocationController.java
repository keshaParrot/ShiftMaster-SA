package ivan.denysiuk.controllers;

import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.entity.BusLocation;
import ivan.denysiuk.service.interfaces.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class LocationController {
    public final static String URL_PATH = "api/v1/vehicles/Location";
    private final LocationService locationService;

    @GetMapping(URL_PATH + "/getAllAvailable")
    public ResponseEntity<Map<Integer, List<Integer>>> getAvailableParkingSpot() {
        Map<Integer, List<Integer>> availableSpots = locationService.getAvailableParkingSpots();
        return ResponseEntity.ok(availableSpots);
    }
    @GetMapping("/getLocation")
    public ResponseEntity<Result<BusLocation>> getLocationByVehicleId(@RequestParam Long busId) {
        try {
            Result<BusLocation> result = locationService.getLocationByVehicleId(busId);

            if (result.hasError()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.failure("An unexpected error occurred: " + e.getMessage()));
        }
    }
    @PutMapping(URL_PATH + "/park")
    public ResponseEntity<Result<Boolean>> parkVehicle(@RequestParam Long busId ,
                                                       @RequestParam int hangar,
                                                       @RequestParam int platform) {
        try {
            Result<Boolean> result = locationService.parkVehicleOnHangar(busId, hangar, platform);

            if (result.hasError()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.failure("An unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/isAvailable")
    public ResponseEntity<String> isSpotAvailable(@RequestParam int hangar,
                                                    @RequestParam int platform) {

        boolean locationExistResult = locationService.isSpotAvailable(hangar,platform);

        if (!locationExistResult) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Location with provided hangar and platform is either occupied or does not exist.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Location with provided hangar and platform exist");
    }
}
