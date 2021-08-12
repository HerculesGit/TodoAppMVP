package br.com.herco.todoappmvp.services.synchronize;


import java.util.List;

import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.modules.di.DI;
import io.reactivex.Observable;

public interface ISynchronizedDatabase extends DI {

    Observable<TaskModel> createTask(TaskModel taskModel);

    /**
     * not update synchronize
     */
    Observable<TaskModel> updateTask(TaskModel taskModel);

    /**
     * @param userId - the owner of the tasks
     */
    Observable<List<TaskModel>> getAllTasks(String userId);


    /**
     * Delete a task
     *
     * @param taskId
     */
    Observable<TaskModel> deleteTask(String taskId);


    Observable<TaskModel> getOneTask(String taskId) throws TaskException;
}
