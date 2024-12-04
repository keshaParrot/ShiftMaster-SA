package ivan.denysiuk.repository;

import ivan.denysiuk.domain.entity.BusLocation;
import ivan.denysiuk.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee entity tables on database.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    /**
     * Get instance from database by user-provided id of Vehicle.
     *
     * @param id of employee
     * @return Vehicle, or a class that extends it.
     */
    @Query("SELECT v FROM Vehicle v where v.id = :vehicleId")
    Optional<Vehicle> getVehicleById(@Param("vehicleId") Long id);
    /**
     * Find all vehicles by their IDs.
     *
     * @param ids List of vehicle IDs
     * @return List of vehicles with the given IDs
     */
    @Query("SELECT v FROM Vehicle v WHERE v.id IN :vehicleIds")
    List<Vehicle> findByIdIn(@Param("vehicleIds")List<Long> ids);
    /**
     * Get instance from database by user-provided registration number of Vehicle.
     *
     * @param registrationNumber of vehicle
     * @return Vehicle, or a class that extends it.
     */
    @Query("SELECT v FROM Vehicle v where v.registrationNumber = :registrationNumber")
    Optional<Vehicle> getByRegistrationNumber(@Param("registrationNumber") String registrationNumber);
    /**
     * Get instance from database by user-provided serial number of Vehicle.
     *
     * @param serialNumber of vehicle
     * @return Vehicle, or a class that extends it.
     */
    @Query("SELECT v FROM Vehicle v where v.serialNumber = :serialNumber")
    Optional<Vehicle> getBySerialNumber(@Param("serialNumber") String serialNumber);
    /**
     * Checks if a location is occupied by a vehicle with the specified location.
     *
     * @param hangar of vehicle
     * @param platform of vehicle
     * @return Vehicle, or a class that extends it.
     */
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Vehicle v WHERE v.busLocation.hangar = :hangar AND v.busLocation.platform = :platform")
    boolean isLocationOccupied(@Param("hangar") int hangar, @Param("platform") int platform);
    /**
     * Get All location which is occupied by a vehicle.
     *
     * @return List of Bus Location, with now is unavailable.
     */

    @Query("SELECT v.busLocation FROM Vehicle v WHERE v.busLocation IS NOT NULL")
    List<BusLocation> findAllOccupiedLocations();
}
