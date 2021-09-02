package br.com.herco.todoappmvp.application;

import br.com.herco.todoappmvp.models.AuthUser;
import br.com.herco.todoappmvp.models.UserModel;

public interface ITodoApp {
    UserModel getCurrentUser();

    void setCurrentUser(UserModel user);

    public AuthUser getAuthUser();

    public void setAuthUser(AuthUser authUser);

    void setOnline(boolean online);

    boolean isOnline();

    boolean getOfflineSynchronize();

    void setOfflineSynchronize(boolean value);
}
