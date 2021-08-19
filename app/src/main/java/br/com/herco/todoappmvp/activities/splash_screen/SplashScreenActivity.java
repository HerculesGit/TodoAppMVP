package br.com.herco.todoappmvp.activities.splash_screen;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.security.GeneralSecurityException;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.activities.login.LoginActivity;
import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.home.HomeActivity;
import br.com.herco.todoappmvp.modules.di.TodoAppDependenciesManager;
import br.com.herco.todoappmvp.repositories.user.UserRepositoryImpl;
import br.com.herco.todoappmvp.services.database.localdatabase.ILocalDatabase;
import br.com.herco.todoappmvp.services.database.localdatabase.LocalDatabase;
import br.com.herco.todoappmvp.services.database.secure_preferences.ISecurePreferences;
//import br.com.herco.todoappmvp.services.database.secure_preferences.SecurePreferencesImpl;
import br.com.herco.todoappmvp.services.database.sqlite.DataBaseSQLiteHelper;
import br.com.herco.todoappmvp.services.database.sqlite.SQLiteClient;
import br.com.herco.todoappmvp.utils.activity.ActivityUtils;

import static br.com.herco.todoappmvp.constants.Constants.INJECTION_DEPENDENCIES.LOCAL_DATABASE;
import static br.com.herco.todoappmvp.constants.Constants.INJECTION_DEPENDENCIES.SQLITE_CLIENT;

public class SplashScreenActivity extends AppCompatActivity
        implements ISplashScreenContract.ISplashScreenView {

    private ISplashScreenContract.ISplashScreenPresenter presenter;

    private ActivityUtils activityUtils = ActivityUtils.getInstance();

    private ISecurePreferences securePreferences;

    public void loadPresenter() {

//        try {
//            //securePreferences = new SecurePreferencesImpl(this);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        presenter = new SplashScreenPresenter(this,
                TodoApp.getInstance(), new UserRepositoryImpl(null),
                securePreferences);

        presenter.loadingCredentials();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        activityUtils.setCurrentContext(this);
        loadPresenter();
    }

    public void initUI() {
        new Handler().postDelayed(() -> {
            // presenter.loadingCredentials();
        }, 200);
    }

    @Override
    public void loadingInjectionDependencies() {
        TodoAppDependenciesManager.getInstance();

        TodoAppDependenciesManager.addDependency(
                LOCAL_DATABASE, new LocalDatabase(new DataBaseSQLiteHelper(this))
        );
        ILocalDatabase synchronizedDatabase = (ILocalDatabase) TodoAppDependenciesManager.getDependency(LOCAL_DATABASE);

        TodoAppDependenciesManager.addDependency(SQLITE_CLIENT, new SQLiteClient(synchronizedDatabase));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void goToLogin() {
        activityUtils.toOff(this, LoginActivity.class);
    }

    @Override
    public void goToHome() {
        activityUtils.toOff(this, HomeActivity.class);
    }

    @Override
    public void showNoInternetConnection() {

    }
}