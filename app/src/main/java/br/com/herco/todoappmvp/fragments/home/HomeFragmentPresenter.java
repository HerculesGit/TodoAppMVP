package br.com.herco.todoappmvp.fragments.home;

import android.annotation.SuppressLint;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class HomeFragmentPresenter implements HomeTaskContract.IHomeTaskFragmentPresenter {
    final HomeTaskContract.IHomeTaskFragmentView iHomeContractView;
    final ITaskRestRepository taskRepository;

    public HomeFragmentPresenter(HomeTaskContract.IHomeTaskFragmentView iHomeTaskFragmentView, ITaskRestRepository taskRepository) {
        this.iHomeContractView = iHomeTaskFragmentView;
        this.taskRepository = taskRepository;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAllTasks(String userId) {
        // TODO: REMOVER ESSE DELAY FAKE. Está aqui até ajeitar app.isOnline()
//        int fakeDelay = 2000;
//        new Handler().postDelayed(() -> {
//
//        }, fakeDelay);
        try {
            taskRepository.getAllTasks(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((tasks) -> {
                        if (tasks.isEmpty()) {
                            iHomeContractView.noTasksFound();
                            iHomeContractView.hideTasksLayout();
                        } else {
                            iHomeContractView.onLoadTaskSuccess(tasks);
                            iHomeContractView.hideNotFoundTasks();
                            iHomeContractView.showTasksLayout();
                        }
                    }, throwable -> {
                        int resId = R.string.error_load_tasks;

                        if (throwable instanceof HttpException) {
                            int code = ((HttpException) throwable).code();

                            if (code == 500) {
                                resId = R.string.server_error_500;
                            }
                        }

                        iHomeContractView.onLoadTaskError(resId);
                    });
        } catch (TaskException e) {
            iHomeContractView.onLoadTaskError(R.string.error_load_tasks);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteTask(TaskModel taskModel, int index) {
        try {
            taskRepository.deleteTask(taskModel).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((taskUpdated) -> iHomeContractView.onDeletedTask(index),
                            throwable -> {
                                int resId = R.string.error_deleting_task;

                                if (throwable instanceof HttpException) {
                                    int code = ((HttpException) throwable).code();

                                    if (code == 404) {
                                        resId = R.string.not_found_tasks;
                                    }
                                }

                                iHomeContractView.onDeletedTaskError(resId, index);
                            });
        } catch (TaskException e) {
            e.printStackTrace();
            iHomeContractView.onDeletedTaskError(0, index);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateTask(TaskModel taskModel, int index) {
        try {
            taskRepository.updateTask(taskModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((taskUpdated) -> {
                                iHomeContractView.onUpdatedTask(taskUpdated, index);
                            },
                            (throwable) -> {
                                int resId = R.string.error_updating_task;

                                if (throwable instanceof HttpException) {
                                    int code = ((HttpException) throwable).code();

                                    if (code == 404) {
                                        resId = R.string.not_found_tasks;
                                    } else {
                                        resId = R.string.error_updating_task;
                                    }
                                }
                                iHomeContractView.onUpdatedTaskError(resId, index);
                            });
        } catch (TaskException e) {
            e.printStackTrace();
            iHomeContractView.onUpdatedTaskError(index, R.string.error_updating_task);
        }
    }
}
