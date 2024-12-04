package ivan.denysiuk.controllers;

import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.entity.MaintenanceMessage;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.repository.MaintenanceMessageRepository;
import ivan.denysiuk.service.interfaces.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class TechMaintenanceController {
    public final static String URL_PATH = "api/v1/vehicles/maintenance";
    public final static String ID_URL_PATH = URL_PATH + "/{id}";
    private final VehicleService vehicleService;
    private final MaintenanceMessageRepository maintenanceMessageRepository;


    public ResponseEntity<Result<Vehicle>> getAllByNeedInspection(){
        return ResponseEntity.ok(null);

    }
    @GetMapping(URL_PATH + "/messages/getAll")
    public ResponseEntity<List<MaintenanceMessage>> getAllMaintenanceMessages() {
        return ResponseEntity.ok(null);
    }
    @GetMapping(URL_PATH + "/messages/get/{id}")
    public ResponseEntity<Result<MaintenanceMessage>> getMaintenanceMessages(@PathVariable("id") long messageId) {
        return ResponseEntity.ok(null);
    }
    @PutMapping(URL_PATH + "/messages/{id}/close")
    public ResponseEntity<Result<Boolean>> closeMessage(){
        return ResponseEntity.ok(null);

    }
    @PutMapping(ID_URL_PATH + "/update")
    public ResponseEntity<?> updateById(){
        return ResponseEntity.ok(null);

    }
    @PutMapping(ID_URL_PATH + "/patch")
    public ResponseEntity<?> patchById(){
        return ResponseEntity.ok(null);

    }
    @PostMapping(ID_URL_PATH + "/create")
    public ResponseEntity<?> createById(){
        return ResponseEntity.ok(null);

    }
    @DeleteMapping(ID_URL_PATH + "/delete")
    public ResponseEntity<?> deleteById(){
        return ResponseEntity.ok(null);

    }

}
