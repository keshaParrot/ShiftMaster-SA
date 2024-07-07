package ivan.denysiuk.service;


import ivan.denysiuk.domain.dto.VehicleDTO;
import ivan.denysiuk.domain.entity.Vehicle;
import ivan.denysiuk.domain.mapper.VehicleMapperManager;
import ivan.denysiuk.repository.VehicleRepository;
import ivan.denysiuk.service.interfaces.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;

    @Override
    public <T extends VehicleDTO> T saveToSystem(T vehicle) {
        return null;
    }

    @Override
    public <T extends VehicleDTO> T updateById(T vehicle) {
        return null;
    }

    @Override
    public <T extends VehicleDTO> T patchById(T vehicle) {
        return null;
    }

    @Override
    public <T extends Vehicle> T getById(Long id, Class<T> expectedType) {
        return null;
    }

    @Override
    public <T extends Vehicle> T getByRegistrationNumber(String registrationNumber, Class<T> expectedType) {
        return null;
    }

    @Override
    public <T extends Vehicle> T getBySerialNumber(String serialNumber, Class<T> expectedType) {
        return null;
    }

    @Override
    public List<Vehicle> getAllByNeedInspection() {
        return null;
    }

    @Override
    public List<Vehicle> getAllByLocation() {
        return null;
    }

    @Override
    public List<Vehicle> getByAvailability() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    //@Override
    //public List<Vehicle> findByParams(String location, String availability) {
    //    return null;
    //}

    private static <T extends Vehicle> VehicleDTO convertEntityToDto(T entity) {
        return  VehicleMapperManager.convertEntityToDto(entity);
    }
    private static <T extends VehicleDTO> Vehicle convertDtoToEntity(T dto) {
        return VehicleMapperManager.convertDtoToEntity(dto);
    }
}
