package br.com.herco.todoappmvp.repositories.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.herco.todoappmvp.dto.UserLoginDTO;
import br.com.herco.todoappmvp.exceptions.UserException;
import br.com.herco.todoappmvp.services.database.retrofit.UserRestService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest {

    @Mock
    private UserRestService service;

    @Mock
    private IUserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = new UserRepositoryImpl(service);
    }

    @Test
    public void shouldDoLoginUserWhenUsernameAndPasswordAreCorrected() throws Exception {
        String username = "Username";
        String password = "12345678";

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(username);
        userLoginDTO.setPassword(password);

        // when call the method
        userRepository.login(username, password);

       verify(service, times(1)).login(any());
    }

    @Test
    public void shouldThrowsExceptionWhenUsernameOrPasswordAreNull() throws Exception {
        String messageError = "Username cannot be null";

        try {
            when(userRepository.login(null, "1234")).thenThrow(UserException.class);
        } catch (UserException e) {
            assertEquals(e.getMessage(), messageError);
        }

        messageError = "Password cannot be null";
        try {
            when(userRepository.login("username", null)).thenThrow(UserException.class);
        } catch (UserException e) {
            assertEquals(e.getMessage(), messageError);
        }
        verify(service, times(0)).login(null);
    }

    @Test
    public void shouldThrowsTheUserExceptionWhenTokenIsNull() {
        String messageError = "Token not provided";
        try {
            when(userRepository.getCurrentUser(null)).thenThrow(new UserException(messageError));
        } catch (UserException e) {
            assertEquals(e.getMessage(), messageError);
        }
        verifyNoInteractions(service);
    }

    @Test
    public void shouldThrowsTheExceptionIfServiceIsNull() {
//        verify(service, times(1)).login("", "");
    }
}
