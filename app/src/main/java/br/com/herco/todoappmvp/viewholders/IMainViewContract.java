package br.com.herco.todoappmvp.viewholders;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public interface IMainViewContract {
    void onLoadTasks(List<TaskModel> tasks);

    void onErrorLoad();
}
