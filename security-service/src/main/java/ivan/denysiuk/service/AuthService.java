package ivan.denysiuk.service;

import ivan.denysiuk.domain.entity.AuthData;

public interface AuthService {

    String createAuthDataRecord(String phoneNumber);// всі генерування токенів і тп буде робитись тут відповідно, можливо ідентифікація власника буде по песелю
    boolean loginWithData(String email, String password);
    boolean patchById(Long id, AuthData newData);
    boolean updateById(Long id, AuthData newData);
    boolean resetPassword(String mobileCode);
    boolean resetPasscode(String mobileCode);

}
