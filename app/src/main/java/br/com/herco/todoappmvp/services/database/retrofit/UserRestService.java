package br.com.herco.todoappmvp.services.database.retrofit;

import br.com.herco.todoappmvp.models.AuthUser;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserRestService {

    /**
     * @param username
     * @param password
     */
    @POST("login")
    Observable<AuthUser> login(@Path("username") String username, @Path("password") String password) throws Exception;

    /**
     * @return Observable of the AuthUser with auth=false and token=null
     */
    @POST("logout")
    Observable<AuthUser> logout(@Path("token") String token);
}
