package ivan.denysiuk.service;

import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.entity.MaintenanceMessage;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.domain.enumeration.MessageStatus;
import ivan.denysiuk.repository.MaintenanceMessageRepository;
import ivan.denysiuk.repository.VehicleRepository;
import ivan.denysiuk.service.interfaces.VehicleMaintenanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class VehicleMaintenanceServiceImpl implements VehicleMaintenanceService {
    private final VehicleRepository vehicleRepository;
    private final MaintenanceMessageRepository maintenanceMessageRepository;

    @Override
    public void checkVehiclesForMaintenance() {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        for (Vehicle vehicle : vehicles) {
            int inspectionStatus = vehicle.isNeedInspection();

            if (inspectionStatus == 1) {
                String message = "Vehicle " + vehicle.getRegistrationNumber() + " needs quarterly maintenance.";
                save(message);
            } else if (inspectionStatus == 2) {
                String message = "Vehicle " + vehicle.getRegistrationNumber() + " needs annual maintenance.";
                save(message);
            }
        }
    }
    @Override
    public Result<Boolean> close(Long id, Long empId){
        MaintenanceMessage message = maintenanceMessageRepository.findById(id).orElse(null);
        //TODO make check is employee exist on DB
        if(message == null){
            return Result.failure("Message with provided id: "+id +" does not exist");
        }
        else if(message.getStatus() == MessageStatus.CLOSED){
            return Result.failure("Message with provided id: "+id +" already closed");
        }
        message.setStatus(MessageStatus.CLOSED);
        message.setMessageOwner(empId);
        maintenanceMessageRepository.save(message);
        return Result.success(true, "Message with provided id: "+id +" is closed");
    }
    @Override
    public Result<MaintenanceMessage> getById(long id){
        return null;
    }
    private void save(String message) {
        MaintenanceMessage maintenanceMessage = new MaintenanceMessage();
        maintenanceMessage.setMessage(message);
        maintenanceMessage.setStatus(MessageStatus.OPEN);
        maintenanceMessageRepository.save(maintenanceMessage);
    }
}
