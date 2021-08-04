package br.com.herco.todoappmvp.edit_task;

import android.annotation.SuppressLint;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.mvp.BasePresenter;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;
import br.com.herco.todoappmvp.utils.network.NetworkUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditTaskPresenter extends BasePresenter {
    private final IEditTaskContract mViewContract;
    private final ITaskRestRepository taskRepository;

    public EditTaskPresenter(IEditTaskContract mViewContract, ITaskRestRepository taskRepository) {
        this.mViewContract = mViewContract;
        this.taskRepository = taskRepository;
    }

    @SuppressLint("CheckResult")
    public void createTask(TaskModel taskModel, String userId) {
        try {
            if (NetworkUtil.isInternetAvailable()) {
                createLocalTask(taskModel, userId);
            } else {
                createRemoteTask(taskModel, userId);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mViewContract.onCreateTaskError(exception.getMessage());
        }
    }

    private void createLocalTask(TaskModel taskModel, String userId) {

    }

    @SuppressLint("CheckResult")
    private void createRemoteTask(TaskModel taskModel, String userId) throws TaskException {
        taskModel.setUserId(userId);

        taskRepository.createTask(taskModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((task) -> mViewContract.onCreateTask(task),
                        throwable -> {
                            throwable.printStackTrace();
                            mViewContract.onCreateTaskError(throwable.getMessage());
                        });
    }
}
