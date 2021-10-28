package br.com.herco.todoappmvp.fragments.home;

import androidx.annotation.StringRes;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public interface HomeTaskContract {
    interface IHomeTaskFragmentView {
        void onLoadTaskSuccess(List<TaskModel> loadedTasks);

        void onLoadTaskError(@StringRes int resId);

        void onUpdatedTask(TaskModel taskModel, int index);

        void onUpdatedTaskError(@StringRes int resId, int index);

        void onDeletedTask(int index);

        void onDeletedTaskError(@StringRes int resId, int index);

        void noTasksFound();

        void noInternetConnection();

        void hideNotFoundTasks();

        void showNotFoundTasks();

        void showTasksLayout();

        void hideTasksLayout();

        void showLoading();

        void hideLoading();
    }

    interface IHomeTaskFragmentPresenter {
        void loadAllTasks(String userId);

        void deleteTask(TaskModel taskModel, int index);

        void updateTask(TaskModel taskModel, int index);
    }
}
