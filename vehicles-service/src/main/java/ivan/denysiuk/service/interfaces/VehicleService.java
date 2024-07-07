package ivan.denysiuk.service.interfaces;




import ivan.denysiuk.domain.dto.VehicleDTO;
import ivan.denysiuk.domain.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    <T extends VehicleDTO> T saveToSystem(T vehicle);
    <T extends VehicleDTO> T updateById(T vehicle);
    <T extends VehicleDTO> T patchById(T vehicle);
    <T extends Vehicle> T getById(Long id, Class<T> expectedType);
    <T extends Vehicle> T getByRegistrationNumber(String registrationNumber, Class<T> expectedType);
    <T extends Vehicle> T getBySerialNumber(String serialNumber, Class<T> expectedType);
    List<Vehicle> getAllByNeedInspection();
    List<Vehicle> getAllByLocation(); //also availability
    List<Vehicle> getByAvailability(); //also availability
    boolean deleteById(Long id);

    //List<Vehicle> findByParams(String location,String availability);
}
