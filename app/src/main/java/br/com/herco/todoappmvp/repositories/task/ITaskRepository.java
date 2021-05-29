package br.com.herco.todoappmvp.repositories.task;

import java.util.List;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;

@Deprecated
public interface ITaskRepository {

    TaskModel createTask(TaskModel taskModel) throws TaskException;

    TaskModel updateTask(TaskModel taskModelToUpdate) throws TaskException;

    void deleteTask(TaskModel taskModel) throws TaskException;

    TaskModel getTask(int id);

    List<TaskModel> getAllTasks(int userId);
}
