package br.com.herco.todoappmvp.activities.login;

import android.annotation.SuppressLint;

import java.net.SocketTimeoutException;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.exceptions.UserException;
import br.com.herco.todoappmvp.models.ResponseError;
import br.com.herco.todoappmvp.repositories.user.IUserRepository;
import br.com.herco.todoappmvp.services.database.secure_preferences.ISecurePreferences;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.ILoginPresenter {
    private final LoginContract.ILoginView loginView;
    private final IUserRepository userRepository;
    private final ISecurePreferences securePreferences;

    public LoginPresenter(LoginContract.ILoginView view, IUserRepository userRepository, ISecurePreferences securePreferences) {
        this.loginView = view;
        this.userRepository = userRepository;
        this.securePreferences = securePreferences;
    }

    @Override
    public void doLogin() {
        String password = loginView.getPassword();
        String userName = loginView.getUserName();


        if (userName == null || userName.isEmpty()) {
            loginView.showUsernameError(R.string.username_is_required);
            return;
        }

        if (password == null || password.isEmpty()) {
            loginView.showPasswordError(R.string.password_is_required);
            return;
        }
        dotLoginRequest();
    }

    @SuppressLint("CheckResult")
    private void dotLoginRequest() {
        loginView.disableLoginButton();

        final String username = loginView.getUserName();
        final String password = loginView.getPassword();
        try {
            userRepository.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(authUser -> {
                        System.out.println("Login_Presenter -> " + authUser);
                        TodoApp.getInstance().setCurrentUser(authUser.getUserModel());
                        TodoApp.getInstance().setAuthUser(authUser);
                        this.securePreferences.saveUserCredentials(username, password);
                        this.securePreferences.saveToken(authUser.getToken());
                        loginView.loginSuccess();
                    }, throwable -> {
                        loginView.loginError(new ResponseError().getStringRes(throwable, R.string.login_message_error_username_or_password_invalid));
                        loginView.enableLoginButton();
                        System.out.println("throwable -> " + throwable.getMessage());
                    });
        } catch (UserException ex) {
            System.out.println("throwable -> " + ex.getMessage());
            loginView.loginError(R.string.login_message_error_username_or_password_invalid);
            loginView.enableLoginButton();

        }
    }
}
