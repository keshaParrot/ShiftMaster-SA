package ivan.denysiuk.controllers;

import ivan.denysiuk.service.interfaces.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class TechMaintenanceController {
    private final VehicleService vehicleService;

    /*
    public ResponseEntity<?> getAllByNeedInspection(){

    }
    public ResponseEntity<?> updateById(){

    }
    public ResponseEntity<?> patchById(){

    }
    public ResponseEntity<?> createById(){

    }
    public ResponseEntity<?> deleteById(){

    }*/

}
