package br.com.herco.todoappmvp.repositories.user;

import br.com.herco.todoappmvp.models.UserModel;

public class UserRepository implements IUserRepository {
    @Override
    public UserModel getCurrentUser(String token) {
        return new UserModel(1, "Joy");
    }
}
