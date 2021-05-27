package br.com.herco.todoappmvp.fragments.home;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRepository;

public class HomeFragmentPresenter {
    final IHomeContractView iHomeContractView;
    final ITaskRepository taskRepository;

    public HomeFragmentPresenter(IHomeContractView mainContractView, ITaskRepository taskRepository) {
        this.iHomeContractView = mainContractView;
        this.taskRepository = taskRepository;
    }

    public void loadTasks() {
        try {
            iHomeContractView.onTasksLoad(taskRepository.getAllTasks(0));
        } catch (Exception e) {
            e.printStackTrace();
            iHomeContractView.onTaskLoadError(e.getMessage());
        }
    }

    public void updateTask(TaskModel taskModel) {
        try {
            taskRepository.updateTask(taskModel);
        } catch (TaskException e) {
            e.printStackTrace();
            iHomeContractView.updateTaskError(e.getMessage());
        }
    }
}
