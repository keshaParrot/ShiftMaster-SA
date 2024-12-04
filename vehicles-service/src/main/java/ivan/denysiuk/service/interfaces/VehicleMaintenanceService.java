package ivan.denysiuk.service.interfaces;

import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.entity.MaintenanceMessage;

public interface VehicleMaintenanceService {
    void checkVehiclesForMaintenance();
    Result<Boolean> close(Long id, Long empId);
    Result<MaintenanceMessage> getById(long id);
}
