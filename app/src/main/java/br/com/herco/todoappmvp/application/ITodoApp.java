package br.com.herco.todoappmvp.application;

import br.com.herco.todoappmvp.models.UserModel;

public interface ITodoApp {
    UserModel getCurrentUser();

    void setCurrentUser(UserModel user);

    void setOnline(boolean online);

    boolean isOnline();

    boolean getOfflineSynchronize();

    void setOfflineSynchronize(boolean value);
}
