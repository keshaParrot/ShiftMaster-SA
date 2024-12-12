package ivan.denysiuk.service.interfaces;




import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.dto.RequestCreateVehicle;
import ivan.denysiuk.domain.enumeration.VehicleType;
import ivan.denysiuk.domain.dto.VehicleDTO;
import ivan.denysiuk.domain.entity.BusLocation;
import ivan.denysiuk.domain.entity.Reserved;
import ivan.denysiuk.domain.entity.Vehicle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface VehicleService {

    Result<Vehicle> saveToSystem(RequestCreateVehicle vehicle);
    <T extends VehicleDTO, E extends Vehicle> Result<E> updateById(T vehicle, Long id);
    <T extends VehicleDTO, E extends Vehicle> Result<E> patchById(T vehicle, Long id);
    <T extends Vehicle> Result<T> getById(Long id, Class<T> expectedType);
    <T extends Vehicle> Result<T> getByRegistrationNumber(String registrationNumber, Class<T> expectedType);
    <T extends Vehicle> Result<T> getBySerialNumber(String serialNumber, Class<T> expectedType);


    Result<Boolean> addReservationToVehicle(Long id, Reserved reserved);
    Result<Boolean> deleteReservationInVehicle(Long id, Reserved reserved);
    Result<Boolean> addReservationsToVehicle(Long id, List<Reserved> reserved);
    Result<Boolean> deleteReservationsInVehicle(Long id, List<Reserved> reserved);
    boolean deleteById(Long id);

    Stream<Vehicle> filterByAvailability(Stream<Vehicle> vehicles, LocalDate date, LocalTime startTime, LocalTime endTime);
    Stream<Vehicle> filterByLocation(Stream<Vehicle> vehicles, int hangar);
    Stream<Vehicle> filterByInspection(Stream<Vehicle> vehicles, int needInspection);
    Stream<Vehicle> filterByType(Stream<Vehicle> vehicles, VehicleType type);

    Optional<List<Vehicle>> getAllBy(
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            Boolean availability,
            Integer hangar,
            Integer needInspection,
            VehicleType type,
            Long[] vehiclesIds);

    int countAll();
    int countAllServiceable();
    int countAllNotServiceable();
}
