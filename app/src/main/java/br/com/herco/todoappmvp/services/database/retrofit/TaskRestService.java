package br.com.herco.todoappmvp.services.database.retrofit;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskRestService {

    @GET("tasks")
    Observable<List<TaskModel>> listTasks();

    @POST("tasks")
    Observable<TaskModel> postTask(@Body TaskModel task);

    @PUT("task/{id}")
    Observable<TaskModel> updateTask(@Path("id") Integer id, @Body TaskModel task);

    @DELETE("task/{id}")
    Observable<TaskModel> deleteTask(@Path("id") Integer id);
}
