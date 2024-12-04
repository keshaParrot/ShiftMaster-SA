package ivan.denysiuk.service.interfaces;

import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.entity.BusLocation;
import ivan.denysiuk.domain.entity.Vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface LocationService {

    Map<Integer, List<Integer>> getAvailableParkingSpots();
    Result<Boolean> parkVehicleOnHangar(Long vehicleId,int hangar, int platform);
    Result<BusLocation> getLocationByVehicleId(Long vehicleId);
    boolean isSpotAvailable(int hangar, int platform);
}
