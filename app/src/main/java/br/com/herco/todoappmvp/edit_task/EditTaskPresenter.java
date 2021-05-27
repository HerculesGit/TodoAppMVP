package br.com.herco.todoappmvp.edit_task;

import android.util.Log;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.mvp.BasePresenter;
import br.com.herco.todoappmvp.repositories.task.ITaskRepository;

public class EditTaskPresenter extends BasePresenter {
    final IEditTaskContract mViewContract;
    final ITaskRepository taskRepository;

    public EditTaskPresenter(IEditTaskContract mViewContract, ITaskRepository taskRepository) {
        this.mViewContract = mViewContract;
        this.taskRepository = taskRepository;
    }

    public void createTask(TaskModel taskModel) {
        try {
            taskModel = taskRepository.createTask(taskModel);
            mViewContract.onCreateTask(taskModel);
        } catch (Exception exception) {
            Log.d("getTaskError", exception.getMessage());
            exception.printStackTrace();
            mViewContract.onCreateTaskError("getTaskError");
        }
    }
}
