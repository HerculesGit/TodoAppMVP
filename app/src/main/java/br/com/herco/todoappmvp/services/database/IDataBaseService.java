package br.com.herco.todoappmvp.services.database;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public interface IDataBaseService {
    TaskModel saveTask(TaskModel taskModel);

    TaskModel updateTask(TaskModel taskModel);

    List<TaskModel> getAllTaskByUser(int userId);

    void saveOffLineSynchronizationValue(boolean value);

    boolean getOffLineSynchronizationValue();
}
