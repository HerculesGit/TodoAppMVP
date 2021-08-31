package br.com.herco.todoappmvp.mocks;

import br.com.herco.todoappmvp.dto.UserLoginDTO;
import br.com.herco.todoappmvp.models.AuthUser;
import br.com.herco.todoappmvp.services.database.retrofit.UserRestService;
import io.reactivex.Observable;

public class ApiUserRestServiceMock implements UserRestService {

    public final String FAKE_TOKEN = "fake_token";
    public final boolean FAKE_AUTH = true;

    public final String FAKE_USERNAME;
    public final String FAKE_PASSWORD;

    public final AuthUser authUser = new AuthUser(
            FAKE_TOKEN, FAKE_AUTH
    );

    public ApiUserRestServiceMock(String fake_username, String fake_password) {
        FAKE_USERNAME = fake_username;
        FAKE_PASSWORD = fake_password;
    }


    @Override
    public Observable<AuthUser> login(UserLoginDTO userLoginDTO) throws Exception {

        if (!userLoginDTO.getUsername().equals(FAKE_USERNAME) || !userLoginDTO.getPassword().equals(FAKE_PASSWORD)) {
            throw new Exception("Username or password invalid");
        }

        return Observable.just(authUser);
    }

    @Override
    public Observable<AuthUser> logout(String token) {
        return null;
    }
}