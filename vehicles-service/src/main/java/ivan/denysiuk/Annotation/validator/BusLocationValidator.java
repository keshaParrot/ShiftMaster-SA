package ivan.denysiuk.Annotation.validator;

import ivan.denysiuk.Annotation.ValidBusLocation;
import ivan.denysiuk.domain.entity.BusLocation;
import ivan.denysiuk.service.interfaces.LocationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusLocationValidator implements ConstraintValidator<ValidBusLocation, BusLocation> {

    private final LocationService vehicleService; // Сервіс для перевірки локації

    @Override
    public boolean isValid(BusLocation busLocation, ConstraintValidatorContext context) {
        if (busLocation == null) {
            return false;
        }

        int hangar = busLocation.getHangar();
        int platform = busLocation.getPlatform();

        return vehicleService.isSpotAvailable(hangar, platform);
    }
}

