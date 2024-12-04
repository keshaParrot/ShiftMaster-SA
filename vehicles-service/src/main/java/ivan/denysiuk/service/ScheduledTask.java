package ivan.denysiuk.service;

import ivan.denysiuk.service.interfaces.VehicleMaintenanceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private final VehicleMaintenanceService vehicleMaintenanceChecker;

    public ScheduledTask(VehicleMaintenanceService vehicleMaintenanceChecker) {
        this.vehicleMaintenanceChecker = vehicleMaintenanceChecker;
    }

    @Scheduled(cron = "0 8 * * *")
    public void scheduledMaintenanceCheck() {
        vehicleMaintenanceChecker.checkVehiclesForMaintenance();
    }
}
