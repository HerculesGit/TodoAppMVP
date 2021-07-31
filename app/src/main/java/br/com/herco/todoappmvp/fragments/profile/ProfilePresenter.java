package br.com.herco.todoappmvp.fragments.profile;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public class ProfilePresenter implements IProfileContract.IProfilePresenter {
    private final IProfileContract.IProfileView profileView;

    public ProfilePresenter(IProfileContract.IProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void calculateTasksProgress(List<TaskModel> tasks) {
        if (tasks == null) {
            profileView.onCalculateTasksProgress(0f);
        } else {
            int obtained = 0;
            for (TaskModel task : tasks) {
                if (task.isDone()) obtained++;
            }

            float totalProgress = (obtained / (float) tasks.size()) * 100f;
            profileView.onCalculateTasksProgress(totalProgress);
        }
    }
}
