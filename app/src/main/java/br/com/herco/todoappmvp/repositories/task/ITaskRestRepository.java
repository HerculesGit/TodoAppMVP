package br.com.herco.todoappmvp.repositories.task;

import java.util.List;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import io.reactivex.Observable;

public interface ITaskRestRepository {
    Observable<TaskModel> createTask(TaskModel taskModel) throws TaskException;

    Observable<TaskModel> updateTask(TaskModel taskModelToUpdate) throws TaskException;

    Observable<TaskModel> deleteTask(TaskModel taskModel) throws TaskException;

    Observable<TaskModel> getTask(int id) throws TaskException;

    Observable<List<TaskModel>> getAllTasks(int userId) throws TaskException;
}
