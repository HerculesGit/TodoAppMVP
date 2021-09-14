package br.com.herco.todoappmvp.activities.edit_task;

import br.com.herco.todoappmvp.models.TaskModel;

public interface IEditTaskContract {
    void onCreateTask(TaskModel task);

    void onCreateTaskError(String errorMessage);
}
