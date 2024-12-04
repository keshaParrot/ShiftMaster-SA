package ivan.denysiuk.service;

import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.repository.VehicleRepository;
import ivan.denysiuk.service.interfaces.VehicleReservedCleaner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VehicleReservedCleanerImpl implements VehicleReservedCleaner {
    private final VehicleRepository vehicleRepository;

    public VehicleReservedCleanerImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Scheduled(cron = "0 8 * * *")
    public void cleanOldReservations() {
        Iterable<Vehicle> vehicles = vehicleRepository.findAll();

        vehicles.forEach(vehicle -> {
            if (vehicle.getWhenReserved() != null) {
                vehicle.getWhenReserved().removeIf(reserved -> reserved.getDate().isBefore(LocalDate.now().minusWeeks(2)));
                vehicleRepository.save(vehicle);
            }
        });
    }
}
