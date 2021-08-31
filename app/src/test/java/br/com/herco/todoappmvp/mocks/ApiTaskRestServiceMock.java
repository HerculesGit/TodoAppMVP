package br.com.herco.todoappmvp.mocks;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserSynchronizedDateModel;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import io.reactivex.Observable;

public class ApiTaskRestServiceMock implements TaskRestService {
    @Override
    public Observable<List<TaskModel>> listTasks(String userId) {
        return null;
    }

    @Override
    public Observable<UserSynchronizedDateModel> synchronizeTasks(String userId, List<TaskModel> task) {
        return null;
    }

    @Override
    public Observable<TaskModel> postTask(TaskModel task) {
        return null;
    }

    @Override
    public Observable<TaskModel> updateTask(String uuid, TaskModel task) {
        return null;
    }

    @Override
    public Observable<TaskModel> deleteTask(String uuid) {
        return null;
    }
}
