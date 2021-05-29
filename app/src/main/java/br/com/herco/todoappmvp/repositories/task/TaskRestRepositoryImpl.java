package br.com.herco.todoappmvp.repositories.task;

import java.util.List;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.database.retrofit.ApiClient;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import io.reactivex.Observable;

public class TaskRestRepositoryImpl implements ITaskRestRepository {

    final TaskRestService service;

    public TaskRestRepositoryImpl() {
        service = ApiClient.create(TaskRestService.class);
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
    public Observable<TaskModel> getTask(int id) throws TaskException {
        return null;
    }

    @Override
    public Observable<List<TaskModel>> getAllTasks(int userId) throws TaskException {
        return service.listTasks();
    }
}
