package br.com.herco.todoappmvp.repositories.task;

import java.util.List;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.database.IDataBaseService;
import br.com.herco.todoappmvp.validators.TaskValidator;

public class TaskRepository implements ITaskRepository {

    private final IDataBaseService taskDataBaseService;

    public TaskRepository(IDataBaseService taskDataBaseService) {
        this.taskDataBaseService = taskDataBaseService;
    }

    @Override
    public TaskModel createTask(TaskModel taskModel) throws TaskException {
        TaskValidator.validateTaskToCreate(taskModel);

        taskModel = taskDataBaseService.saveTask(taskModel);
        return taskModel;
    }

    @Override
    public TaskModel updateTask(TaskModel taskModel) throws TaskException {
        TaskValidator.validateTaskToUpdate(taskModel);
        taskModel = taskDataBaseService.updateTask(taskModel);
        return taskModel;
    }

    @Override
    public void deleteTask(TaskModel taskModel) throws TaskException {
        TaskValidator.validateTaskToDelete(taskModel);
    }

    @Override
    public TaskModel getTask(int id) {
        return null;
    }

    @Override
    public List<TaskModel> getAllTasks(int userId) {
        return taskDataBaseService.getAllTaskByUser(userId);
    }
}
