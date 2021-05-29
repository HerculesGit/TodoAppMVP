package br.com.herco.todoappmvp.fragments.home;

import android.annotation.SuppressLint;
import android.util.Log;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentPresenter {
    final IHomeContractView iHomeContractView;
    final ITaskRestRepository taskRepository;

    public HomeFragmentPresenter(IHomeContractView mainContractView, ITaskRestRepository taskRepository) {
        this.iHomeContractView = mainContractView;
        this.taskRepository = taskRepository;
    }

    @SuppressLint("CheckResult")
    public void loadTasks() {
        try {
            taskRepository.getAllTasks(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((tasks) -> iHomeContractView.onTasksLoad(tasks), throwable -> {
                        throwable.printStackTrace();
                        iHomeContractView.onTaskLoadError(throwable.getMessage());
                    });
        } catch (TaskException e) {
            iHomeContractView.onTaskLoadError(e.getMessage());
        }
    }

    @SuppressLint("CheckResult")
    public void updateTask(int index, TaskModel taskModel) {
        try {
            taskRepository.updateTask(taskModel).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((taskUpdated) -> iHomeContractView.onUpdateTaskSuccess(index, taskUpdated),
                            throwable -> {
                                iHomeContractView.onUpdateTaskError(index, throwable.getMessage());
                            });
        } catch (TaskException e) {
            e.printStackTrace();
            iHomeContractView.onUpdateTaskError(index, e.getMessage());
        }
    }


    @SuppressLint("CheckResult")
    public void deleteTask(int index, TaskModel taskModel) {
        Log.e(">>>>>>", "deleteTask" + taskModel.getId() + "  " + taskModel.isDone());
        try {
            taskRepository.deleteTask(taskModel).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((taskUpdated) -> iHomeContractView.onDeletedTask(index),
                            throwable -> {
                                iHomeContractView.onDeletedTaskError(index, throwable.getMessage());
                            });
        } catch (TaskException e) {
            e.printStackTrace();
            iHomeContractView.onDeletedTaskError(index, e.getMessage());
        }
    }
}
