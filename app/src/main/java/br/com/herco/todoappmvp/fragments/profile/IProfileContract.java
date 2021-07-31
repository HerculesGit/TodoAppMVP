package br.com.herco.todoappmvp.fragments.profile;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public interface IProfileContract {
    interface IProfileView {
        void onCalculateTasksProgress(float totalProgressTasks);
    }

    interface IProfilePresenter {
        void calculateTasksProgress(List<TaskModel> tasks);
    }
}
