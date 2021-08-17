package br.com.herco.todoappmvp.repositories.user;

import br.com.herco.todoappmvp.exceptions.UserException;
import br.com.herco.todoappmvp.models.AuthUser;
import br.com.herco.todoappmvp.models.UserModel;
import io.reactivex.Observable;

public interface IUserRepository {
    Observable<UserModel> getCurrentUser(String token) throws UserException;

    Observable<AuthUser> login(String username, String password) throws UserException;
}
