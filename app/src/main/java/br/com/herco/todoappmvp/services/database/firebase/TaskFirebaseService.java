package br.com.herco.todoappmvp.services.database.firebase;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserSynchronizedDateModel;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import io.reactivex.Observable;

public class TaskFirebaseService implements TaskRestService {
    private final FirebaseClient fbClient = new FirebaseClient();

    @Override
    public Observable<List<TaskModel>> listTasks(String userId) {
        return fbClient.listTasks(userId);
    }

    @Override
    public Observable<UserSynchronizedDateModel> synchronizeTasks(String userId, List<TaskModel> task) {
        return null;
    }

    @Override
    public Observable<TaskModel> postTask(TaskModel task) {
        return fbClient.postTask(task);
    }

    @Override
    public Observable<TaskModel> updateTask(String uuid, TaskModel task) {
        return fbClient.updateTask(uuid, task);
    }

    @Override
    public Observable<TaskModel> deleteTask(String uuid) {
        return fbClient.deleteTask(uuid);
    }
}
