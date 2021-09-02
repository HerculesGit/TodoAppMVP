package br.com.herco.todoappmvp.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserSynchronizedDateModel;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class ApiTaskRestServiceMock implements TaskRestService {

    public String userId = "00000";

    public List<TaskModel> tasks = init();

    public boolean forceServerError = false;

    List<TaskModel> init() {
        List<TaskModel> tasks = new ArrayList<>();
        int count = 0;
        for (int i : new int[10]) {
            count++;
            tasks.add(new TaskModel("id" + count, "T" + count, false, userId));
        }
        return tasks;
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public Observable<List<TaskModel>> listTasks(String userId) {
        if (forceServerError) {
            return Observable.error(new HttpException(Response.error(500, ResponseBody.create(null, new byte[0]))));
        }

        tasks = new ArrayList<>();

        if (!this.userId.equals(userId)) {
            return Observable.just(tasks);
        }

        int count = 0;
        for (int i : new int[10]) {
            count++;
            tasks.add(new TaskModel("id" + count, "T" + count, false, userId));
        }
        return Observable.just(tasks);
    }

    @Override
    public Observable<UserSynchronizedDateModel> synchronizeTasks(String userId, List<TaskModel> task) {
        return null;
    }

    @Override
    public Observable<TaskModel> postTask(TaskModel task) {
        return null;
    }

    @Override
    public Observable<TaskModel> updateTask(String uuid, TaskModel task) {
        TaskModel taskFound = null;

        for (TaskModel taskModel : tasks) {
            if (taskModel.getId().equals(uuid)) {
                taskFound = taskModel;
                break;
            }
        }
        if (taskFound == null) {
            return Observable.error(new HttpException(Response.error(404, ResponseBody.create(null, new byte[0]))));
        }
        return Observable.just(taskFound);
    }

    @Override
    public Observable<TaskModel> deleteTask(String uuid) {
        TaskModel taskFound = null;

        for (TaskModel taskModel : tasks) {
            if (taskModel.getId().equals(uuid)) {
                taskFound = taskModel;

                tasks.remove(taskModel);
                break;
            }
        }
        if (taskFound == null) {
            return Observable.error(new HttpException(Response.error(404, ResponseBody.create(null, new byte[0]))));
        }
        return Observable.just(taskFound);
    }
}
