package br.com.herco.todoappmvp.observers.task;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public interface TaskChannel {
    void onTasksUpdated(List<TaskModel> tasks);
}
