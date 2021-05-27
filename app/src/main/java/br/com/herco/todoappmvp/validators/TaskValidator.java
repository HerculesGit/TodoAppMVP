package br.com.herco.todoappmvp.validators;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;

public class TaskValidator {
    public static void validateTaskToCreate(TaskModel taskModel) throws TaskException {
        if (taskModel == null) {
            throw new TaskException("Task cannot null!");
        }

        if (taskModel.getName() == null) {
            throw new TaskException("Task's name cannot null!");
        }
    }

    public static boolean validateTaskToDelete(TaskModel taskModel) throws TaskException {
        if (taskModel == null) {
            throw new TaskException("Task cannot null!!");
        }

        if (taskModel.getId() == null) {
            throw new TaskException("Task's id cannot null!!");
        }
        return true;
    }

    public static boolean validateTaskToUpdate(TaskModel taskModel) throws TaskException {
        validateTaskToCreate(taskModel);
        validateTaskToDelete(taskModel);
        return true;
    }
}
