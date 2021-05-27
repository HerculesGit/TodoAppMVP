package br.com.herco.todoappmvp;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public interface IMainContractView {
    void onTasksLoad(List<TaskModel> tasks);

    void onTaskLoadError(String messageError);

    void updateTaskSuccess();

    void updateTaskError(String messageError);
}
