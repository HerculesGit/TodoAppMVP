package br.com.herco.todoappmvp.edit_task;

import android.annotation.SuppressLint;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.mvp.BasePresenter;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;
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
            taskModel.setUserId(userId);

            taskRepository.createTask(taskModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((task) -> mViewContract.onCreateTask(task),
                            throwable -> {
                                throwable.printStackTrace();
                                mViewContract.onCreateTaskError(throwable.getMessage());
                            });
        } catch (Exception exception) {
            exception.printStackTrace();
            mViewContract.onCreateTaskError(exception.getMessage());
        }
    }
}
