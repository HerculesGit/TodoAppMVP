package br.com.herco.todoappmvp.services.database.sqlite;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.modules.di.DI;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import br.com.herco.todoappmvp.services.synchronize.ISynchronizedDatabase;
import io.reactivex.Observable;

public class SQLiteClient implements TaskRestService, DI {

    final ISynchronizedDatabase synchronizedDatabase;

    public SQLiteClient(ISynchronizedDatabase synchronizedDatabase) {
        this.synchronizedDatabase = synchronizedDatabase;
    }

    @Override
    public Observable<List<TaskModel>> listTasks(String userId) {
        return this.synchronizedDatabase.getAllTasks(userId);
    }

    @Override
    public Observable<List<TaskModel>> synchronizeTasks(String userId, List<TaskModel> task) {
        return null;
    }

    @Override
    public Observable<TaskModel> postTask(TaskModel task) {
        return this.synchronizedDatabase.createTask(task);
    }

    @Override
    public Observable<TaskModel> updateTask(String uuid, TaskModel task) {
        return this.synchronizedDatabase.updateTask(task);
    }

    @Override
    public Observable<TaskModel> deleteTask(String uuid) {
        return this.synchronizedDatabase.deleteTask(uuid);
    }
}
