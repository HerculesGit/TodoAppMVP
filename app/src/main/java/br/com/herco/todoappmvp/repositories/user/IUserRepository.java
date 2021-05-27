package br.com.herco.todoappmvp.repositories.user;

import br.com.herco.todoappmvp.models.UserModel;

public interface IUserRepository {
    UserModel getCurrentUser(String token);
}
