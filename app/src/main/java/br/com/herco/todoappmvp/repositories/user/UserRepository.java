package br.com.herco.todoappmvp.repositories.user;

import br.com.herco.todoappmvp.models.UserModel;

public class UserRepository implements IUserRepository {
    @Override
    public UserModel getCurrentUser(String token) {
        return new UserModel("07944e81-0aee-4eaa-8d77-0dc8d1d8a356", "Joy");
    }
}
