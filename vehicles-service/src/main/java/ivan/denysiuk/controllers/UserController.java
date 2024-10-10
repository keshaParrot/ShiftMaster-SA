package ivan.denysiuk.controllers;

import ivan.denysiuk.service.interfaces.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UserController{
    private final VehicleService vehicleService;

    /*public ResponseEntity<?> getBySerialNumber(){

    }
    public ResponseEntity<?> getByRegistrationNumber(){

    }
    public ResponseEntity<?> getByAvailability(){

    }
    public ResponseEntity<?> getByLocation() {

    }
    public ResponseEntity<?> getById(){

    }*/
}
