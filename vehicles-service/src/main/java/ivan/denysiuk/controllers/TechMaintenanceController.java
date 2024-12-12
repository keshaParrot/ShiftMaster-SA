package ivan.denysiuk.controllers;

import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.dto.RequestCreateVehicle;
import ivan.denysiuk.domain.dto.VehicleDTO;
import ivan.denysiuk.domain.entity.MaintenanceMessage;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.domain.enumeration.MessageStatus;
import ivan.denysiuk.domain.enumeration.VehicleType;
import ivan.denysiuk.service.interfaces.VehicleMaintenanceService;
import ivan.denysiuk.service.interfaces.VehicleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class TechMaintenanceController {
    public final static String VEHICLE_URL_PATH = "/api/v1/maintenance/vehicles";
    public final static String MESSAGE_URL_PATH = "/api/v1/maintenance/message";
    private final VehicleService vehicleService;
    private final VehicleMaintenanceService vehicleMaintenanceService;


    @GetMapping(MESSAGE_URL_PATH + "/getAll")
    public ResponseEntity<List<MaintenanceMessage>> getAllMaintenanceMessages(@RequestParam(value = "status", required = false) MessageStatus status) {
        Optional<List<MaintenanceMessage>> messages = vehicleMaintenanceService.getAllBy(status);

        return messages.map(maintenanceMessages
                -> new ResponseEntity<>(maintenanceMessages, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND));
    }

    @GetMapping(MESSAGE_URL_PATH + "/{id}/get")
    public ResponseEntity<Result<MaintenanceMessage>> getMaintenanceMessages(@PathVariable("id") long messageId) {
        Result<MaintenanceMessage> result = vehicleMaintenanceService.getById(messageId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @PutMapping(MESSAGE_URL_PATH + "/{id}/close")
    public ResponseEntity<Result<Boolean>> closeMessage(@PathVariable("id") long messageId,
                                                        @RequestParam(value = "employeeId") Long empId){
        Result<Boolean> resultOfClosing = vehicleMaintenanceService.close(messageId,empId);
        if (resultOfClosing.isSuccess()) {
            return new ResponseEntity<>(resultOfClosing, HttpStatus.OK);
        }
        return new ResponseEntity<>(resultOfClosing, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(VEHICLE_URL_PATH + "/getAllBy")
    public ResponseEntity<List<Vehicle>> getAllBy(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                  @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                                  @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
                                                  @RequestParam(value = "availability", required = false) Boolean availability,
                                                  @RequestParam(value = "hangar", required = false) Integer hangar,
                                                  @RequestParam(value = "needInspection", required = false) Integer needInspection,
                                                  @RequestParam(value = "type", required = false) VehicleType type,
                                                  @RequestParam(value = "vehiclesIds", required = false) Long[] vehiclesIds) {

        Optional<List<Vehicle>> vehicles = vehicleService.getAllBy(
                date,
                startTime,
                endTime,
                availability,
                hangar,
                needInspection,
                type,
                vehiclesIds
        );

        return vehicles
                .map(v -> ResponseEntity.ok(v))
                .orElseGet(() -> ResponseEntity.ok(Collections.emptyList()));
    }
    @PostMapping(VEHICLE_URL_PATH + "/create")
    public ResponseEntity<Result<Vehicle>> create(@Valid @RequestBody RequestCreateVehicle request){
        Result<Vehicle> result = vehicleService.saveToSystem(request);

        if (result.hasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }
    @PutMapping(VEHICLE_URL_PATH + "/{id}/update")
    public ResponseEntity<Result<Vehicle>> updateById(@PathVariable("id") long vehicleId,
                                                      @Valid @RequestBody VehicleDTO request){
        Result<Vehicle> result = vehicleService.updateById(request,vehicleId);

        if (result.hasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }
    @PatchMapping(VEHICLE_URL_PATH + "/{id}/patch")
    public ResponseEntity<?> patchById(@PathVariable("id") long vehicleId,
                                       @Valid @RequestBody VehicleDTO request){
        Result<Vehicle> result = vehicleService.patchById(request,vehicleId);

        if (result.hasError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }
    @DeleteMapping(VEHICLE_URL_PATH + "/{id}/delete")
    public ResponseEntity<String> deleteById(@PathVariable("id") long vehicleId){
        boolean result = vehicleService.deleteById(vehicleId);
        if (result){
            return ResponseEntity.status(HttpStatus.OK).body("Vehicle with id " + vehicleId + " deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vehicle with id " + vehicleId + " does not exist");
    }

}
