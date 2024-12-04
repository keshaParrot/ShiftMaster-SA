package ivan.denysiuk.service;

import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.entity.BusLocation;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.repository.VehicleRepository;
import ivan.denysiuk.service.interfaces.LocationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationServiceImpl implements LocationService {

    private final VehicleRepository vehicleRepository;
    private final Map<Integer, Integer> parkingLocations = new HashMap<>();

    public LocationServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;

        parkingLocations.put(1, 12);
        parkingLocations.put(2, 8);
    }

    @Override
    public Map<Integer, List<Integer>> getAvailableParkingSpots() {
        List<BusLocation> occupiedLocations = vehicleRepository.findAllOccupiedLocations();

        Map<Integer, List<Integer>> allSpots = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : parkingLocations.entrySet()) {
            int hangar = entry.getKey();
            int spotsPerHangar = entry.getValue();

            List<Integer> spots = new ArrayList<>();
            for (int spot = 1; spot <= spotsPerHangar; spot++) {
                spots.add(spot);
            }
            allSpots.put(hangar, spots);
        }

        for (BusLocation location : occupiedLocations) {
            allSpots.get(location.getHangar()).remove((Integer)location.getPlatform());
        }

        return allSpots;
    }

    @Override
    public Result<Boolean> parkVehicleOnHangar(Long vehicleId, int hangar, int platform) {
        Vehicle queriedVehicle = vehicleRepository.getVehicleById(vehicleId).orElseThrow();
        if(isSpotAvailable(hangar,platform))
            queriedVehicle.setBusLocation(new BusLocation(hangar,platform));
        else
            return Result.failure("Park spot: " + platform + " on hangar: " + hangar + " is not available or not exist");

        vehicleRepository.save(queriedVehicle);
        return Result.success(null, "Bus was parked successfully on hangar: " + hangar + " platform: " + platform);
    }

    @Override
    public Result<BusLocation> getLocationByVehicleId(Long vehicleId) {
        Vehicle queriedVehicle = vehicleRepository.getVehicleById(vehicleId).orElseThrow();
        BusLocation queriedBusLocation = queriedVehicle.getBusLocation();
        if (queriedBusLocation == null)
            return Result.failure("Error while getting location for vehicle. This vehicle can be on trip");
        else
            return Result.success(queriedBusLocation);
    }

    @Override
    public boolean isSpotAvailable(int hangar, int platform) {
        if(!isSpotExist(hangar,platform)) return false;
        return !vehicleRepository.isLocationOccupied(hangar,platform);
    }
    public boolean isSpotExist(int hangar, int platform){
        Integer totalSpots = parkingLocations.get(hangar);

        if (totalSpots == null) {
            return false;
        }

        return platform > 0 && platform <= totalSpots;
    }
}
