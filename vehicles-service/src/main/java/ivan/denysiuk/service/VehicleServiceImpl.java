package ivan.denysiuk.service;


import ivan.denysiuk.customClasses.OptionalCollector;
import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.enumeration.VehicleType;
import ivan.denysiuk.domain.dto.VehicleDTO;
import ivan.denysiuk.domain.entity.BusLocation;
import ivan.denysiuk.domain.entity.Reserved;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.domain.mapper.VehicleMapperManager;
import ivan.denysiuk.repository.VehicleRepository;
import ivan.denysiuk.service.interfaces.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Override
    public <T extends VehicleDTO, E extends Vehicle> Result<E> saveToSystem(T vehicle) {
        try {
            E convertedVehicle = convertDtoToEntity(vehicle);
            E savedVehicle = vehicleRepository.save(convertedVehicle);
            logger.info("Vehicle was saved successfully: {}", savedVehicle);
            return Result.success(savedVehicle,"Vehicle was saved successfully");
        } catch (Exception e) {
            logger.error("Error while saving the vehicle: {}", e.getMessage(), e);
            return Result.failure("Vehicle could not be saved, error attempted");
        }
    }

    @Override
    public <T extends VehicleDTO, E extends Vehicle> Result<E> updateById(T vehicle, Long id) {
        try {
            E convertedVehicle = convertDtoToEntity(vehicle);
            E savedVehicle;
            if (vehicleRepository.existsById(id)){
                convertedVehicle.setId(id);
                savedVehicle = vehicleRepository.save(convertedVehicle);
                logger.info("Vehicle was updated successfully: {}", savedVehicle);
                return Result.success(savedVehicle,"Vehicle was saved successfully");
            }
            else {
                return Result.failure("Vehicle with provided id="+id +" does not exist");
            }
        } catch (Exception e) {
            logger.error("Error while saving the vehicle: {}", e.getMessage(), e);
            return Result.failure("Vehicle could not be updated, error attempted");
        }
    }

    @Override
    public <T extends VehicleDTO, E extends Vehicle> Result<E> patchById(T vehicle, Long id) {
        Optional<Vehicle> queriedVehicle = vehicleRepository.getVehicleById(id);
        if(queriedVehicle.isEmpty()){
            return Result.failure("Vehicle with provided id="+id +" does not exist");
        }

        if(StringUtils.hasText(vehicle.getSerialNumber())){
            queriedVehicle.get().setSerialNumber(vehicle.getSerialNumber());
        }
        if(StringUtils.hasText(vehicle.getRegistrationNumber())){
            queriedVehicle.get().setRegistrationNumber(vehicle.getRegistrationNumber());
        }
        if(StringUtils.hasText(vehicle.getBrand())){
            queriedVehicle.get().setBrand(vehicle.getBrand());
        }
        if(vehicle.getDeployDate() != null){
            queriedVehicle.get().setDeployDate(vehicle.getDeployDate());
        }
        if(vehicle.getLastAnnualMaintenance() != null){
            queriedVehicle.get().setLastAnnualMaintenance(vehicle.getLastAnnualMaintenance());
        }
        if(vehicle.getLastQuarterlyMaintenance() != null){
            queriedVehicle.get().setLastQuarterlyMaintenance(vehicle.getLastQuarterlyMaintenance());
        }
        if(vehicle.getStatus() != null){
            queriedVehicle.get().setStatus(vehicle.getStatus());
        }
        if(vehicle.getWhenReserved() != null && !vehicle.getWhenReserved().isEmpty()) {
            queriedVehicle.get().setWhenReserved(vehicle.getWhenReserved());
        }
        if(vehicle.getBusLocation() != null){
            queriedVehicle.get().setBusLocation(vehicle.getBusLocation());
        }
        E updatedVehicle = (E) vehicleRepository.save(queriedVehicle.get());
        return Result.success(updatedVehicle,"Vehicle was updated successfully");
    }

    @Override
    public Result<Boolean> addReservationToVehicle(Long id, Reserved reserved) {
        Optional<Vehicle> queriedVehicle = vehicleRepository.getVehicleById(id);
        if(queriedVehicle.isEmpty()){
            return Result.failure("Vehicle with provided id="+id +" does not exist");
        }
        int isAvailable = queriedVehicle.get().isAvailable(reserved.getDate(),reserved.getFrom(),reserved.getTo())
        if(isAvailable == 0){
            queriedVehicle.get().getWhenReserved().add(reserved);
            Vehicle updatedVehicle = vehicleRepository.save(queriedVehicle.get());
            return Result.success(true,"reservation on date [" + reserved.getDate() + "] [" + reserved.getFrom()+"-"+reserved.getTo() +"] added successfully");
        }
        else if (isAvailable == 2){
            return Result.failure("Between shifts Vehicle must to have 15 minutes break");
        }
        return Result.failure("Vehicle already have reservation on this time");
    }

    @Override
    public Result<Boolean> deleteReservationInVehicle(Long id, Reserved reserved) {
        return null;
    }

    @Override
    public Result<Boolean> addReservationsToVehicle(Long id, List<Reserved> reserved) {
        return Result.failure(null);
    }

    @Override
    public Result<Boolean> deleteReservationsInVehicle(Long id, List<Reserved> reserved) {
        return null;
    }

    @Override
    public <T extends Vehicle> Result<T> getById(Long id, Class<T> expectedType) {
        /* Employee employee = employeeRepository.getEmployeeById(id);
        if (expectedType.isInstance(employee) && expectedType.isAssignableFrom(employee.getClass())) {
            return expectedType.cast(employee);
        } else {
            throw new IllegalArgumentException("expected type " + expectedType.getSimpleName() + ", but received  " + employee.getClass().getSimpleName());
        }
        */
    }

    @Override
    public <T extends Vehicle> Result<T> getByRegistrationNumber(String registrationNumber, Class<T> expectedType) {
        return null;
    }
    @Override
    public <T extends Vehicle> Result<T> getBySerialNumber(String serialNumber, Class<T> expectedType) {
        return null;
    }
    //TODO we need to add handle all scenario when user provide or not args
    @Override
    public Stream<Vehicle> filterByAvailability(Stream<Vehicle> vehicles, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return vehicles.filter(vehicle -> vehicle.isAvailable(date,startTime,endTime) == 0);
    }
    @Override
    public Stream<Vehicle> filterByLocation(Stream<Vehicle> vehicles, int hangar) {
        return vehicles.filter(vehicle -> vehicle.getBusLocation().getHangar() == hangar);
    }
    @Override
    public Stream<Vehicle> filterByInspection(Stream<Vehicle> vehicles, int needInspection) {
        return vehicles.filter(vehicle -> vehicle.isNeedInspection() == needInspection);
    }
    @Override
    public Stream<Vehicle> filterByType(Stream<Vehicle> vehicles, VehicleType type) {
        return Stream.empty();
    }

    @Override
    public Optional<List<Vehicle>> getAllBy(
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            Boolean availability,
            Integer hangar,
            Integer needInspection,
            VehicleType type,
            Long[] vehiclesIds) {

        Stream<Vehicle> vehicleStream;
        if (vehiclesIds.length == 0){
            vehicleStream = vehicleRepository.findAll().stream();
        }else {
            vehicleStream = vehicleRepository.findByIdIn(Arrays.asList(vehiclesIds)).stream();
        }

        if (availability != null || date != null || startTime != null || endTime != null) {
            vehicleStream = filterByAvailability(vehicleStream, date, startTime, endTime);
        }

        if (hangar != null) {
            vehicleStream = filterByLocation(vehicleStream, hangar);
        }

        if (needInspection != null) {
            vehicleStream = filterByInspection(vehicleStream, needInspection);
        }
        if (type != null) {
            vehicleStream = filterByType(vehicleStream, type);
        }

        return vehicleStream.collect(OptionalCollector.toOptionalList());
    }

    @Override
    public Result<BusLocation> getLocationByBusId(Long id) {
        return null;
    }
    //TODO we need to check is bus have a shift on current time, and then change location
    @Override
    public Result<Boolean> changeBusLocation(BusLocation location, Long vehicleId) {
        return Result.failure(null);
    }
    @Override
    public Result<Boolean> changeBusLocation(int hangar, int platform, Long vehicleId) {
        return Result.failure(null);
    }
    @Override
    public Result<Boolean> isLocationOccupied(BusLocation location) {
        return Result.failure(null);
    }
    @Override
    public boolean deleteById(Long id) {
        return false;
    }
    @Override
    public int countAll(){
        return (int) vehicleRepository.count();
    }
    @Override
    public int countAllServiceable(){
        return (int) vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.isNeedInspection() != 0)
                .count();
    }
    @Override
    public int countAllNotServiceable(){
        return (int) vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.isNeedInspection() == 0)
                .count();
    }
    /*private <T extends Vehicle, E extends VehicleDTO> E convertEntityToDto(T entity) {
        return  VehicleMapperManager.convertEntityToDto(entity);
    }*/
    private <T extends VehicleDTO,E extends Vehicle> E convertDtoToEntity(T dto) {
        return VehicleMapperManager.convertDtoToEntity(dto);
    }
}
