package ivan.denysiuk.employeesservice.domain.mappers;

import ivan.denysiuk.employeesservice.domain.dto.DriverLicenseDTO;
import ivan.denysiuk.employeesservice.domain.entity.DriverLicense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DriverLicenseMapper {
    DriverLicenseMapper INSTANCE = Mappers.getMapper(DriverLicenseMapper.class);
    DriverLicenseDTO toDTO(DriverLicense driverLicense);
    DriverLicense toEntity(DriverLicenseDTO driverLicenseDTO);
}
