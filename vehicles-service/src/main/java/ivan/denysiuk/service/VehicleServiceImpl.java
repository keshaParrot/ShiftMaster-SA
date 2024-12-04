package ivan.denysiuk.service;


import ivan.denysiuk.customClasses.OptionalCollector;
import ivan.denysiuk.customClasses.Result;
import ivan.denysiuk.domain.entity.CargoBus;
import ivan.denysiuk.domain.entity.PassengerBus;
import ivan.denysiuk.domain.enumeration.VehicleType;
import ivan.denysiuk.domain.dto.VehicleDTO;
import ivan.denysiuk.domain.entity.Reserved;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.domain.mapper.VehicleMapperManager;
import ivan.denysiuk.repository.VehicleRepository;
import ivan.denysiuk.service.interfaces.VehicleService;
import jakarta.transaction.Transactional;
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
            Optional<Vehicle> existingVehicle = vehicleRepository.getBySerialNumber(vehicle.getSerialNumber());
            if (existingVehicle.isPresent()) {
                Long existingId = existingVehicle.get().getId();
                logger.warn("Vehicle with serial number '{}' already exists with ID: {}", vehicle.getSerialNumber(), existingId);
                return Result.failure("Vehicle with registration number: '" + vehicle.getSerialNumber() + "' already exists with ID: " + existingId);
            }
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
                return Result.success(savedVehicle,"Vehicle was updated successfully");
            }
            else {
                return Result.failure("Vehicle with provided id: "+id +" does not exist");
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
            return Result.failure("Vehicle with provided id: "+id +" does not exist");
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
            return Result.failure("Vehicle with provided id: "+id +" does not exist");
        }
        int isAvailable = queriedVehicle.get().isAvailable(reserved.getDate(),reserved.getFrom(),reserved.getTo());
        if(isAvailable == 0){
            queriedVehicle.get().getWhenReserved().add(reserved);
            vehicleRepository.save(queriedVehicle.get());
            return Result.success(true,"the reservation of the bus: "+queriedVehicle.get().getRegistrationNumber()+", on date " + reserved.getDate() + ", in the hours " + reserved.getFrom()+"-"+reserved.getTo() +" was added successfully");
        }
        else if (isAvailable == 2){
            return Result.failure("Between shifts Vehicle must to have 15 minutes break");
        }
        return Result.failure("Vehicle already have reservation on this time");
    }
    @Override
    public Result<Boolean> deleteReservationInVehicle(Long id, Reserved reserved) {
        Vehicle queriedVehicle = vehicleRepository.getVehicleById(id).orElse(null);
        if(queriedVehicle == null){
            return Result.failure("Vehicle with provided id: "+id +" does not exist");
        }
        if(queriedVehicle.getWhenReserved()!=null && queriedVehicle.getWhenReserved().contains(reserved)){
            queriedVehicle.getWhenReserved().remove(reserved);
            vehicleRepository.save(queriedVehicle);
            return Result.success(true,"the reservation of the bus: "+queriedVehicle.getRegistrationNumber()+", on date " + reserved.getDate() + ", in the hours " + reserved.getFrom()+"-"+reserved.getTo() +" was deleted successfully");
        }
        return Result.failure("the bus: "+queriedVehicle.getRegistrationNumber()+", dont have reservation on date " + reserved.getDate() + ", in the hours " + reserved.getFrom()+"-"+reserved.getTo());
    }
    @Transactional
    @Override
    public Result<Boolean> addReservationsToVehicle(Long id, List<Reserved> reservedList) {
        Optional<Vehicle> queriedVehicle = vehicleRepository.getVehicleById(id);
        if (queriedVehicle.isEmpty()) {
            return Result.failure("Vehicle with provided id: " + id + " does not exist");
        }

        Vehicle vehicle = queriedVehicle.get();

        for (Reserved reserved : reservedList) {
            int isAvailable = vehicle.isAvailable(reserved.getDate(), reserved.getFrom(), reserved.getTo());
            if (isAvailable != 0) {
                if (isAvailable == 2) {
                    return Result.failure("Between shifts Vehicle must have 15 minutes break for reservation on date ["
                            + reserved.getDate() + "] [" + reserved.getFrom() + "-" + reserved.getTo() + "]");
                }
                return Result.failure("Vehicle already has a reservation on date ["
                        + reserved.getDate() + "] [" + reserved.getFrom() + "-" + reserved.getTo() + "]");
            }
            vehicle.getWhenReserved().add(reserved);
        }

        vehicleRepository.save(vehicle);
        return Result.success(true, "All reservations were added successfully");
    }
    @Transactional
    @Override
    public Result<Boolean> deleteReservationsInVehicle(Long id, List<Reserved> reservedList) {
        Optional<Vehicle> queriedVehicle = vehicleRepository.getVehicleById(id);

        if (queriedVehicle.isEmpty()) {
            return Result.failure("Vehicle with provided id: " + id + " does not exist");
        }

        Vehicle vehicle = queriedVehicle.get();

        for (Reserved reserved : reservedList) {
            if (!vehicle.getWhenReserved().contains(reserved)) {
                return Result.failure("The bus with registration number " + vehicle.getRegistrationNumber() +
                        " does not have a reservation on date [" + reserved.getDate() + "] [" +
                        reserved.getFrom() + "-" + reserved.getTo() + "]");
            }
        }

        vehicle.getWhenReserved().removeAll(reservedList);
        vehicleRepository.save(vehicle);

        return Result.success(true, "All reservations have been deleted successfully");
    }
    @Override
    public <T extends Vehicle> Result<T> getById(Long id, Class<T> expectedType) {
        Vehicle queriedVehicle = vehicleRepository.getVehicleById(id).orElse(null);
        if(queriedVehicle == null){
            return Result.failure("Vehicle with provided id: " + id + " does not exist");
        }
        if(expectedType.isInstance(queriedVehicle) && expectedType.isAssignableFrom(queriedVehicle.getClass())){
            return (Result<T>) Result.success(queriedVehicle);
        }
        else {
            return Result.failure("An error occurred when receiving a bus of the " + expectedType.getSimpleName() + ", the bus with this identifier is a " + queriedVehicle.getClass().getSimpleName());
        }
    }
    @Override
    public <T extends Vehicle> Result<T> getByRegistrationNumber(String registrationNumber, Class<T> expectedType) {
        Vehicle queriedVehicle = vehicleRepository.getByRegistrationNumber(registrationNumber).orElse(null);
        if(queriedVehicle == null){
            return Result.failure("Vehicle with provided registration number: " + registrationNumber + " does not exist");
        }
        if(expectedType.isInstance(queriedVehicle) && expectedType.isAssignableFrom(queriedVehicle.getClass())){
            return (Result<T>) Result.success(queriedVehicle);
        }
        else {
            return Result.failure("An error occurred when receiving a bus of the " + expectedType.getSimpleName() + ", the bus with this identifier is a " + queriedVehicle.getClass().getSimpleName());
        }
    }
    @Override
    public <T extends Vehicle> Result<T> getBySerialNumber(String serialNumber, Class<T> expectedType) {
        Vehicle queriedVehicle = vehicleRepository.getBySerialNumber(serialNumber).orElse(null);
        if(queriedVehicle == null){
            return Result.failure("Vehicle with provided serial number: " + serialNumber + " does not exist");
        }
        if(expectedType.isInstance(queriedVehicle) && expectedType.isAssignableFrom(queriedVehicle.getClass())){
            return (Result<T>) Result.success(queriedVehicle);
        }
        else {
            return Result.failure("An error occurred when receiving a bus of the " + expectedType.getSimpleName() + ", the bus with this identifier is a " + queriedVehicle.getClass().getSimpleName());
        }
    }
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
        return vehicles.filter(vehicle -> {
            if (type == VehicleType.PASSENGER && vehicle instanceof PassengerBus) {
                return true;
            } else if (type == VehicleType.CARGO && vehicle instanceof CargoBus) {
                return true;
            }
            return false;
        });
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
    public boolean deleteById(Long id) {
        if(vehicleRepository.existsById(id)){
            vehicleRepository.deleteById(id);
            return true;
        }
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
    private <T extends VehicleDTO,E extends Vehicle> E convertDtoToEntity(T dto) {
        return VehicleMapperManager.convertDtoToEntity(dto);
    }
}
