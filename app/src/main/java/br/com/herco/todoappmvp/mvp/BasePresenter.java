package br.com.herco.todoappmvp.mvp;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.List;

import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;
import br.com.herco.todoappmvp.repositories.task.TaskRestRepositoryImpl;
import br.com.herco.todoappmvp.services.database.localdatabase.ILocalDatabase;
import br.com.herco.todoappmvp.services.database.localdatabase.LocalDatabase;
import br.com.herco.todoappmvp.services.database.retrofit.ApiClient;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import br.com.herco.todoappmvp.services.database.sqlite.DataBaseSQLiteHelper;
import br.com.herco.todoappmvp.utils.activity.ActivityUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter {
//
//    private final ILocalDatabase localDatabase;
//    private final ITaskRestRepository taskRestRepository;
//
//    public BasePresenter() {
//        localDatabase = null;
//        this.taskRestRepository = null;
//    }
//
//    public BasePresenter(ILocalDatabase localDatabase, ITaskRestRepository taskRestRepository) {
//        this.taskRestRepository = taskRestRepository;
//        this.localDatabase = localDatabase;
//    }
//
//    @SuppressLint("CheckResult")
//    public void load() {
//        if (localDatabase == null || taskRestRepository == null) return;
//
//        localDatabase.getUnsynchronizedTasks("07944e81-0aee-4eaa-8d77-0dc8d1d8a356")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(unsynchronizedTasks -> {
//                            Log.e("getUnsynchronizedTasks", unsynchronizedTasks.toString());
//
//                            // postToUnSync(unsynchronizedTasks);
//
//                        },
//                        throwable -> {
//                            throwable.printStackTrace();
//                        });
//    }
//
//    @SuppressLint("CheckResult")
//    private void postToUnSync(List<TaskModel> unsynchronizedTasks) {
//        ITaskRestRepository taskRestRepository = new TaskRestRepositoryImpl(
//                ApiClient.create(TaskRestService.class),
//                TodoApp.getInstance());
//
//
//        try {
//            taskRestRepository.synchronizeTasks("07944e81-0aee-4eaa-8d77-0dc8d1d8a356", unsynchronizedTasks)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(taskModels -> {
//
//                    }, throwable -> {
//                        throwable.printStackTrace();
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
