package ivan.denysiuk.service;

import ivan.denysiuk.domain.entity.AuthData;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String createAuthDataRecord(String phoneNumber) {



        return null;
    }

    @Override
    public boolean loginWithData(String email, String password) {
        return false;
    }

    @Override
    public boolean patchById(Long id, AuthData newData) {
        return false;
    }

    @Override
    public boolean updateById(Long id, AuthData newData) {
        return false;
    }

    @Override
    public boolean resetPassword(String mobileCode) {
        return false;
    }

    @Override
    public boolean resetPasscode(String mobileCode) {
        return false;
    }
}
