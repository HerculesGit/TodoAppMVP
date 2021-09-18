package br.com.herco.todoappmvp.services.database.firebase;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserSynchronizedDateModel;
import io.reactivex.Observable;

public interface IFirebaseClient {

    Observable<List<TaskModel>> listTasks(String userId);

    Observable<UserSynchronizedDateModel> synchronizeTasks(String userId, List<TaskModel> task);

    Observable<TaskModel> postTask(TaskModel task);

    Observable<TaskModel> updateTask(String uuid, TaskModel task);

    Observable<TaskModel> deleteTask(String uuid);
}
