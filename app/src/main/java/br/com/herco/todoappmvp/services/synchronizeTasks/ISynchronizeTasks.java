package br.com.herco.todoappmvp.services.synchronizeTasks;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public interface ISynchronizeTasks {
    /**
     * Get all task from local server, eg. sqlite
     *
     * @param userId
     */
    void getAllTasksFromLocalDatabase(String userId);

    /**
     * Send UnSynchronized tasks to server
     *
     * @param unSynchronizedTasks
     * @param userId
     */
    void synchronizeTasksOnServer(String userId, List<TaskModel> unSynchronizedTasks);


    /**
     * After the server sync the tasks, the response should save on the local database
     *
     * @param date - The string iso date e.g: 2021-08-04T00:35:10.020Z
     */
    void saveLastSynchronizedDateOnLocalDatabase(String userId, String date);
}
