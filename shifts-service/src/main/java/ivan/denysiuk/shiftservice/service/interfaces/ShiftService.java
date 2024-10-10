package ivan.denysiuk.shiftservice.service.interfaces;

public interface ShiftService {




    boolean clockIn(String passcode);
    boolean clockOut(String passcode);

}
