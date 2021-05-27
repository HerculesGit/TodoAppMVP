package br.com.herco.todoappmvp;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRepository;

public class MainPresenter {
    final IMainContractView mainContractView;
    final ITaskRepository taskRepository;

    public MainPresenter(IMainContractView mainContractView, ITaskRepository taskRepository) {
        this.mainContractView = mainContractView;
        this.taskRepository = taskRepository;
    }

    void loadTasks() {
        try {
            mainContractView.onTasksLoad(taskRepository.getAllTasks(0));
        } catch (Exception e) {
            e.printStackTrace();
            mainContractView.onTaskLoadError(e.getMessage());
        }
    }

    void updateTask(TaskModel taskModel) {
        try {
            taskRepository.updateTask(taskModel);
        } catch (TaskException e) {
            e.printStackTrace();
            mainContractView.updateTaskError(e.getMessage());
        }
    }
}
