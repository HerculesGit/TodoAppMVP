package br.com.herco.todoappmvp.fragments.home;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public interface IHomeContractView {

    void onTasksLoad(List<TaskModel> tasks);

    void onTaskLoadError(String messageError);

    void updateTaskSuccess();

    void updateTaskError(String messageError);
}
