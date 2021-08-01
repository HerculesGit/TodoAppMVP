package br.com.herco.todoappmvp.services.synchronize;


import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import io.reactivex.Observable;

public interface ISynchronizedDatabase {

    /**
     * if task.id==null, create a task for it
     * else task.id!=null sync task
     *
     * @param taskModel
     * @return taskCreated
     */
    Observable<TaskModel> createTask(TaskModel taskModel, boolean isSynchronized);

    /**
     * not update synchronize
     */
    Observable<TaskModel> updateTask(TaskModel taskModel);

    /**
     * get only tasks with synchronized=false
     *
     * @return
     */
    Observable<List<TaskModel>> getUnsynchronizedTasks(String userId);

    /**
     * @param taskModel
     */
    Observable<TaskModel> synchronizeTask(TaskModel taskModel, String lastUUID);

    /**
     * @param userId - the owner of the tasks
     */
    Observable<List<TaskModel>> getAllTasks(String userId);

}
