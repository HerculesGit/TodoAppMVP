package br.com.herco.todoappmvp.activities.login;

import android.annotation.SuppressLint;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.exceptions.UserException;
import br.com.herco.todoappmvp.repositories.user.IUserRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.ILoginPresenter {
    private final LoginContract.ILoginView loginView;
    private final IUserRepository userRepository;

    public LoginPresenter(LoginContract.ILoginView view, IUserRepository userRepository) {
        this.loginView = view;
        this.userRepository = userRepository;
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

        try {
            userRepository.login(loginView.getUserName(), loginView.getPassword())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userModel -> {
                        System.out.println("Login_Presenter -> " + userModel);
                        loginView.loginSuccess();
                    }, throwable -> {
                        System.out.println("throwable -> " + throwable.getMessage());
                        loginView.loginError(R.string.login_message_error_username_or_password_invalid);
                        loginView.enableLoginButton();
                    });
        } catch (UserException ex) {
            System.out.println("throwable -> " + ex.getMessage());
            loginView.loginError(R.string.login_message_error_username_or_password_invalid);
            loginView.enableLoginButton();

        }
    }
}
