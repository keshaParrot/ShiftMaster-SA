package ivan.denysiuk.service;

import ivan.denysiuk.domain.dto.RequestCreateCargoBus;
import ivan.denysiuk.domain.dto.RequestCreatePassengerBus;
import ivan.denysiuk.domain.dto.RequestCreateVehicle;
import ivan.denysiuk.domain.entity.CargoBus;
import ivan.denysiuk.domain.entity.PassengerBus;
import ivan.denysiuk.domain.entity.Vehicle;

public class VehicleFactory {
    public static Vehicle createVehicle(RequestCreateVehicle vehicle) {
        if (vehicle instanceof RequestCreatePassengerBus) {
            RequestCreatePassengerBus bus = (RequestCreatePassengerBus) vehicle;
            PassengerBus passengerBus = new PassengerBus();
            passengerBus.setNumberOfSeats(bus.getNumberOfSeats());
            return setCommonFields(passengerBus, bus);
        } else if (vehicle instanceof RequestCreateCargoBus) {
            RequestCreateCargoBus bus = (RequestCreateCargoBus) vehicle;
            CargoBus cargoBus = new CargoBus();
            cargoBus.setLoadCapacity(bus.getLoadCapacity());
            return setCommonFields(cargoBus, bus);
        }
        return null;
    }

    private static Vehicle setCommonFields(Vehicle vehicle, RequestCreateVehicle dto) {
        vehicle.setSerialNumber(dto.getSerialNumber());
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setBrand(dto.getBrand());
        return vehicle;
    }
}

