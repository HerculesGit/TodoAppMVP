package br.com.herco.todoappmvp.repositories.user;

import br.com.herco.todoappmvp.dto.UserLoginDTO;
import br.com.herco.todoappmvp.exceptions.UserException;
import br.com.herco.todoappmvp.models.AuthUser;
import br.com.herco.todoappmvp.models.UserModel;
import br.com.herco.todoappmvp.services.database.retrofit.UserRestService;
import io.reactivex.Observable;

public class UserRepositoryImpl implements IUserRepository {

    final UserRestService service;

    public UserRepositoryImpl(UserRestService service) {
        this.service = service;
    }

    @Override
    public Observable<UserModel> getCurrentUser(String token) throws UserException {
        if (token == null) throw new UserException("Token not provided");
        return Observable.just(new UserModel("07944e81-0aee-4eaa-8d77-0dc8d1d8a356", "Joy"));
    }

    @Override
    public Observable<AuthUser> login(String username, String password) throws UserException {

        if (username == null) throw new UserException("Username cannot be null");
        if (password == null) throw new UserException("Password cannot be null");

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(username);
        userLoginDTO.setPassword(password);
        Observable<AuthUser> authUserObservable;
        try {
            authUserObservable = service.login(userLoginDTO);
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
        return authUserObservable;
    }
}
