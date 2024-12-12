package ivan.denysiuk.Annotation;

import ivan.denysiuk.Annotation.validator.BusLocationValidator;
import ivan.denysiuk.service.LocationServiceImpl;
import ivan.denysiuk.service.interfaces.LocationService;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BusLocationValidator.class)
public @interface ValidBusLocation {
    String message() default "The bus location is occupied or invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
