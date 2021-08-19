package br.com.herco.todoappmvp.services.database.retrofit;

import br.com.herco.todoappmvp.dto.UserLoginDTO;
import br.com.herco.todoappmvp.models.AuthUser;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserRestService {

    /**
     * @param userLoginDTO
     */
    @POST("login")
    Observable<AuthUser> login(@Body UserLoginDTO userLoginDTO) throws Exception;

    /**
     * @return Observable of the AuthUser with auth=false and token=null
     */
    @POST("logout")
    Observable<AuthUser> logout(@Path("token") String token);
}
