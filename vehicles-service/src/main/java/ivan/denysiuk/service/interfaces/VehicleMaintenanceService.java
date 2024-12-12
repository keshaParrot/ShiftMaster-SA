package ivan.denysiuk.service.interfaces;

import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.entity.MaintenanceMessage;
import ivan.denysiuk.domain.enumeration.MessageStatus;

import java.util.List;
import java.util.Optional;

public interface VehicleMaintenanceService {

    void checkVehiclesForMaintenance();
    Optional<List<MaintenanceMessage>> getAllBy(MessageStatus status);
    Result<MaintenanceMessage> getById(Long id);
    Result<Boolean> close(Long id, Long empId);
}

