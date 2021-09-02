package br.com.herco.todoappmvp.activities.login;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.dto.UserLoginDTO;
import br.com.herco.todoappmvp.models.AuthUser;
import br.com.herco.todoappmvp.models.UserModel;
import br.com.herco.todoappmvp.repositories.user.IUserRepository;
import br.com.herco.todoappmvp.repositories.user.UserRepositoryImpl;
import br.com.herco.todoappmvp.services.database.retrofit.UserRestService;
import br.com.herco.todoappmvp.services.database.secure_preferences.ISecurePreferences;
import br.com.herco.todoappmvp.test_utils.RemoveLooperSchedulerErrorUtils;
import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {


    @BeforeClass
    public static void afterClass() {
        RemoveLooperSchedulerErrorUtils.execute();
    }

    class ApiClientMock implements UserRestService {

        @Override
        public Observable<AuthUser> login(UserLoginDTO userLoginDTO) throws Exception {

            if (!userLoginDTO.getUsername().equals(FAKE_USERNAME) || !userLoginDTO.getPassword().equals(FAKE_PASSWORD)) {
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

    @Mock
    private ISecurePreferences securePreferences;

    @Before
    public void setUp() {
        apiClientMock = new ApiClientMock();
        userRepository = new UserRepositoryImpl(apiClientMock);
        presenter = new LoginPresenter(view, userRepository, securePreferences);
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
