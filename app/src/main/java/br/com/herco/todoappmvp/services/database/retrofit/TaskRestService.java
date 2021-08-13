package br.com.herco.todoappmvp.services.database.retrofit;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserSynchronizedDateModel;
import br.com.herco.todoappmvp.modules.di.DI;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskRestService {

    @GET("user/{userId}/tasks")
    Observable<List<TaskModel>> listTasks(@Path("userId") String userId);

    @POST("user/{userId}/tasks/synchronize")
    Observable<UserSynchronizedDateModel> synchronizeTasks(@Path("userId") String userId, @Body List<TaskModel> task);

    @POST("tasks")
    Observable<TaskModel> postTask(@Body TaskModel task);

    @PUT("task/{id}")
    Observable<TaskModel> updateTask(@Path("id") String uuid, @Body TaskModel task);

    @DELETE("task/{id}")
    Observable<TaskModel> deleteTask(@Path("id") String uuid);
}
