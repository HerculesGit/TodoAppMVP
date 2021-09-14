package br.com.herco.todoappmvp.activities.splash_screen;

import android.annotation.SuppressLint;

import br.com.herco.todoappmvp.application.ITodoApp;
import br.com.herco.todoappmvp.exceptions.UserException;
import br.com.herco.todoappmvp.repositories.user.IUserRepository;
import br.com.herco.todoappmvp.services.database.secure_preferences.ISecurePreferences;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashScreenPresenter implements ISplashScreenContract.ISplashScreenPresenter {
    private final ISplashScreenContract.ISplashScreenView splashScreenView;

    private final IUserRepository userRepository;
    private final ISecurePreferences securePreferences;
    private final ITodoApp todoApp;

    public SplashScreenPresenter(ISplashScreenContract.ISplashScreenView splashScreenView,
                                 ITodoApp todoApp,
                                 IUserRepository userRepository,
                                 ISecurePreferences securePreferences) {
        this.splashScreenView = splashScreenView;
        this.todoApp = todoApp;
        this.userRepository = userRepository;
        this.securePreferences = securePreferences;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadingCredentials() {
        try {

            final String[] credentials = securePreferences.getUserCredentials();
            final String username = credentials[0];
            final String password = credentials[1];

            if (password == null || username == null) {
                splashScreenView.goToLogin();
            } else {
                userRepository.login(username, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                authUser -> {
                                    todoApp.setAuthUser(authUser);
                                    todoApp.setCurrentUser(authUser.getUserModel());
                                    securePreferences.saveToken(authUser.getToken());
                                    securePreferences.saveUserCredentials(authUser.getUsername(), authUser.getPassword());
                                    splashScreenView.goToHome();

                                }, throwable -> {
                                    splashScreenView.goToLogin();
                                    splashScreenView.showNoInternetConnection();
                                    splashScreenView.hideLoading();
                                }
                        );
            }
        } catch (UserException e) {
            e.printStackTrace();
        }
    }
}
