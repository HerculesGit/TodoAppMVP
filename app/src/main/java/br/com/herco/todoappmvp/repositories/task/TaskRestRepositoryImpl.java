package br.com.herco.todoappmvp.repositories.task;

import java.util.List;

import br.com.herco.todoappmvp.application.ITodoApp;
import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.modules.di.TodoAppDependenciesManager;
import br.com.herco.todoappmvp.services.database.retrofit.ApiClient;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import br.com.herco.todoappmvp.services.database.sqlite.SQLiteClient;
import io.reactivex.Observable;

public class TaskRestRepositoryImpl implements ITaskRestRepository {

    TaskRestService service;
    final ITodoApp app;

    public TaskRestRepositoryImpl(ITodoApp app) {
        this.app = app;
        service = ApiClient.create(TaskRestService.class);
    }

    // TODO: MELHORAR PARA NAO FICAR AQUI
    private void initialize() {

        if (app.isOnline()) {
            service = ApiClient.create(TaskRestService.class);
        } else {
            service = (SQLiteClient) TodoAppDependenciesManager.getDependency("SQLITE_CLIENT");
        }
    }

    @Override
    public Observable<TaskModel> createTask(final TaskModel taskModel) throws TaskException {

        initialize();

        return service.postTask(taskModel);
    }

    @Override
    public Observable<TaskModel> updateTask(TaskModel task) throws TaskException {

        initialize();


        return service.updateTask(task.getId(), task);
    }

    @Override
    public Observable<TaskModel> deleteTask(TaskModel taskModel) throws TaskException {
        initialize();

        return service.deleteTask(taskModel.getId());
    }

    @Override
    public Observable<TaskModel> getTask(String taskId) throws TaskException {
        return null;
    }

    @Override
    public Observable<List<TaskModel>> getAllTasks(String userId) throws TaskException {
        initialize();

        return service.listTasks(userId);
    }

    @Override
    public Observable<List<TaskModel>> synchronizeTasks(String userId, List<TaskModel> tasks) throws TaskException {
        initialize();

        return service.synchronizeTasks(userId, tasks);
    }
}
