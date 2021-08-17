package br.com.herco.todoappmvp.activities.login;

import androidx.annotation.StringRes;

public interface LoginContract {
    interface ILoginPresenter {
        void doLogin();
    }

    interface ILoginView {
        String getPassword();

        String getUserName();

        void loginSuccess();

        void loginError(@StringRes int resId);

        void disableLoginButton();

        void enableLoginButton();

        void showUsernameError(@StringRes int resId);

        void showPasswordError(@StringRes int resId);
    }
}
