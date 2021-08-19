package br.com.herco.todoappmvp.activities.splash_screen;

import android.annotation.SuppressLint;

import br.com.herco.todoappmvp.application.ITodoApp;
import br.com.herco.todoappmvp.exceptions.UserException;
import br.com.herco.todoappmvp.models.UserModel;
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
        splashScreenView.goToLogin();
//        final int passwordIndex = 0;
//        final int userNameIndex = 1;
//
//        final String[] userCredentials = securePreferences.getUserCredentials();
//        final String userName = userCredentials[userNameIndex];
//        final String password = userCredentials[passwordIndex];
//
//        if (userName == null || password == null) {
//            todoApp.setCurrentUser(new UserModel("", ""));
//            splashScreenView.goToLogin();
//            return;
//        }
//
//        try {
//            userRepository.login(userName, password)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                            authUser -> {
//                                securePreferences.saveToken(authUser.getToken());
//                            }, throwable -> {
//                                splashScreenView.showNoInternetConnection();
//                                splashScreenView.hideLoading();
//                            }
//                    );
//        } catch (UserException e) {
//            splashScreenView.showNoInternetConnection();
//        }
    }
}
