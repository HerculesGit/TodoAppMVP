package br.com.herco.todoappmvp.activities.login;

import androidx.annotation.NonNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.models.AuthUser;
import br.com.herco.todoappmvp.models.UserModel;
import br.com.herco.todoappmvp.repositories.user.IUserRepository;
import br.com.herco.todoappmvp.repositories.user.UserRepositoryImpl;
import br.com.herco.todoappmvp.services.database.retrofit.UserRestService;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {


    /**
     * When execute the test without afterClass below code, the throws exception.
     * <br/>
     * This error occurs because the default scheduler returned by
     * <br/>
     * AndroidSchedulers.mainThread() is an instance of LooperScheduler and relies on Android dependencies that are not available in JUnit tests.
     * <br/>
     * Source; https://stackoverflow.com/questions/43356314/android-rxjava-2-junit-test-getmainlooper-in-android-os-looper-not-mocked-runt/43356315#43356315
     */
    @BeforeClass
    public static void afterClass() {
        Scheduler immediate = new Scheduler() {

            @NonNull
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {

                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @NonNull
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    class ApiClientMock implements UserRestService {

        @Override
        public Observable<AuthUser> login(String username, String password) throws Exception {

            if (!username.equals(FAKE_USERNAME) || !password.equals(FAKE_PASSWORD)) {
                throw new Exception("Username or password invalid");
            }

            return Observable.just(authUser);
        }

        @Override
        public Observable<AuthUser> logout(String token) {
            return null;
        }
    }

    private final String FAKE_TOKEN = "afp98gvizbfaçsdhijvASBCJO";
    private final boolean FAKE_AUTH = true;

    private final String FAKE_USERNAME = "Joy";
    private final String FAKE_PASSWORD = "12345";
    private final String FAKE_USER_ID = "as96asd486";

    private AuthUser authUser = new AuthUser(
            FAKE_TOKEN, FAKE_AUTH
    );

    private ApiClientMock apiClientMock;

    private final UserModel fakeUserModel = new UserModel(FAKE_USER_ID, FAKE_USERNAME);

    @Mock
    private LoginContract.ILoginView view;

    @Mock
    private LoginContract.ILoginPresenter presenter;

    @Mock
    private IUserRepository userRepository;

    @Before
    public void setUp() {
        apiClientMock = new ApiClientMock();
        userRepository = new UserRepositoryImpl(apiClientMock);
        presenter = new LoginPresenter(view, userRepository);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() {
        when(view.getUserName()).thenReturn("");
        when(view.getPassword()).thenReturn("");
        presenter.doLogin();

        verify(view, times(1)).showUsernameError(R.string.username_is_required);
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() {
        when(view.getUserName()).thenReturn(FAKE_USERNAME);
        when(view.getPassword()).thenReturn("");
        presenter.doLogin();

        verify(view, times(1)).showPasswordError(R.string.password_is_required);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsNull() {
        when(view.getUserName()).thenReturn(null);
        when(view.getPassword()).thenReturn(FAKE_PASSWORD);
        presenter.doLogin();

        verify(view, times(1)).showUsernameError(R.string.username_is_required);
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsNull() {
        when(view.getUserName()).thenReturn(FAKE_USERNAME);
        when(view.getPassword()).thenReturn(null);
        presenter.doLogin();

        verify(view, times(1)).showPasswordError(R.string.password_is_required);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameOrPasswordIsInvalid() {
        when(view.getUserName()).thenReturn(FAKE_USERNAME);
        when(view.getPassword()).thenReturn("google");
        presenter.doLogin();

        verify(view, times(1)).loginError(R.string.login_message_error_username_or_password_invalid);
    }

    @Test
    public void shouldStartHomeActivityWhenUsernameAndPasswordAreCorrect() {
        when(view.getUserName()).thenReturn(FAKE_USERNAME);
        when(view.getPassword()).thenReturn(FAKE_PASSWORD);

        presenter.doLogin();

        verify(view, times(1)).loginSuccess();
    }

    @Test
    public void shouldDisableLoginButtonWhenCallTheLoginMethod() {
        when(view.getUserName()).thenReturn(FAKE_USERNAME);
        when(view.getPassword()).thenReturn(FAKE_PASSWORD);
        presenter.doLogin();

        verify(view, times(1)).disableLoginButton();

        // This method should not call when the login is successfully
        verify(view, times(0)).enableLoginButton();
    }

    @Test
    public void shouldEnableLoginButtonWhenTheUsernameOrPasswordAreInvalid() {
        when(view.getUserName()).thenReturn(FAKE_USERNAME);
        when(view.getPassword()).thenReturn("google");
        presenter.doLogin();
        verify(view, times(1)).disableLoginButton();

        verify(view, times(1)).loginError(R.string.login_message_error_username_or_password_invalid);
        verify(view, times(1)).enableLoginButton();
    }
}
