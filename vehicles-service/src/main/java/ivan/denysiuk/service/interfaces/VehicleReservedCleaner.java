package ivan.denysiuk.service.interfaces;

import org.springframework.scheduling.annotation.Scheduled;

public interface VehicleReservedCleaner {
    @Scheduled(cron = "0 8 * * *")
    void cleanOldReservations();
}
