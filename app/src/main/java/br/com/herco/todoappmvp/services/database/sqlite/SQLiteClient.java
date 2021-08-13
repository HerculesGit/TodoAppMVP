package br.com.herco.todoappmvp.services.database.sqlite;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserSynchronizedDateModel;
import br.com.herco.todoappmvp.modules.di.DI;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import br.com.herco.todoappmvp.services.database.localdatabase.ILocalDatabase;
import io.reactivex.Observable;

public class SQLiteClient implements TaskRestService, DI {

    final ILocalDatabase localDatabase;

    public SQLiteClient(ILocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    @Override
    public Observable<List<TaskModel>> listTasks(String userId) {
        return this.localDatabase.getAllTasks(userId);
    }

    @Override
    public Observable<UserSynchronizedDateModel> synchronizeTasks(String userId, List<TaskModel> task) {
        return null;
    }

    @Override
    public Observable<TaskModel> postTask(TaskModel task) {
        return this.localDatabase.createTask(task);
    }

    @Override
    public Observable<TaskModel> updateTask(String uuid, TaskModel task) {
        return this.localDatabase.updateTask(task);
    }

    @Override
    public Observable<TaskModel> deleteTask(String uuid) {
        return this.localDatabase.deleteTask(uuid);
    }
}
