package br.com.herco.todoappmvp.activities.splash_screen;

public interface ISplashScreenContract {

    interface ISplashScreenPresenter {
        void loadingCredentials();
    }

    interface ISplashScreenView {

        void loadingInjectionDependencies();

        void showLoading();

        void hideLoading();

        void goToLogin();

        void goToHome();


        // TODO: add to baseView
        void showNoInternetConnection();
    }
}
