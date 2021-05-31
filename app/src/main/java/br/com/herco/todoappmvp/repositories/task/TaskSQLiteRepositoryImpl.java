package br.com.herco.todoappmvp.repositories.task;

import java.util.List;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.database.sqlite.DatabaseHandler;
import io.reactivex.Observable;

public class TaskSQLiteRepositoryImpl implements ITaskRestRepository {

    final DatabaseHandler databaseHandler;

    public TaskSQLiteRepositoryImpl(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    public Observable<TaskModel> createTask(TaskModel taskModel) throws TaskException {
        try {
            Integer id = databaseHandler.addTask(taskModel);
            taskModel = databaseHandler.getTask(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TaskException(e.getMessage());
        }
        return Observable.just(taskModel);
    }

    @Override
    public Observable<TaskModel> updateTask(TaskModel taskModel) throws TaskException {
        try {
            databaseHandler.updateTask(taskModel);
            taskModel = databaseHandler.getTask(taskModel.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new TaskException(e.getMessage());
        }
        return Observable.just(taskModel);
    }

    @Override
    public Observable<TaskModel> deleteTask(TaskModel taskModel) throws TaskException {
        try {
            databaseHandler.deleteTask(taskModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TaskException(e.getMessage());
        }
        return Observable.just(taskModel);
    }

    @Override
    public Observable<TaskModel> getTask(int id) throws TaskException {
        TaskModel taskModel;
        try {
            taskModel = databaseHandler.getTask(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TaskException(e.getMessage());
        }
        return Observable.just(taskModel);
    }

    @Override
    public Observable<List<TaskModel>> getAllTasks(int userId) throws TaskException {
        List<TaskModel> tasks;
        try {
            tasks = databaseHandler.getAllTasks();
        } catch (Exception e) {
            e.printStackTrace();
            throw new TaskException(e.getMessage());
        }
        return Observable.just(tasks);
    }
}
