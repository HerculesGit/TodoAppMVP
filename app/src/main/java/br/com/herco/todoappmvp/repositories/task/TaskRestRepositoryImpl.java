package br.com.herco.todoappmvp.repositories.task;

import java.util.List;

import br.com.herco.todoappmvp.application.ITodoApp;
import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import io.reactivex.Observable;

public class TaskRestRepositoryImpl implements ITaskRestRepository {

    final TaskRestService service;
    final ITodoApp app;

    public TaskRestRepositoryImpl(TaskRestService service, ITodoApp app) {
        this.service = service;
        this.app = app;
    }

    @Override
    public Observable<TaskModel> createTask(final TaskModel taskModel) throws TaskException {
        return service.postTask(taskModel);
    }

    @Override
    public Observable<TaskModel> updateTask(TaskModel task) throws TaskException {
        return service.updateTask(task.getId(), task);
    }

    @Override
    public Observable<TaskModel> deleteTask(TaskModel taskModel) throws TaskException {
        return service.deleteTask(taskModel.getId());
    }

    @Override
    public Observable<TaskModel> getTask(String taskId) throws TaskException {
        return null;
    }

    @Override
    public Observable<List<TaskModel>> getAllTasks(String userId) throws TaskException {
        return service.listTasks(userId);
    }

    @Override
    public Observable<List<TaskModel>> synchronizeTasks(String userId, List<TaskModel> tasks) throws TaskException {
        return service.synchronizeTasks(userId, tasks);
    }
}
