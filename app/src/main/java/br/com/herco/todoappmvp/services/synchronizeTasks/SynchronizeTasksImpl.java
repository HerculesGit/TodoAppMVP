package br.com.herco.todoappmvp.services.synchronizeTasks;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.database.localdatabase.ILocalDatabase;
import br.com.herco.todoappmvp.services.database.retrofit.TaskRestService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SynchronizeTasksImpl implements ISynchronizeTasks {
    private final ILocalDatabase localDatabase;
    private final TaskRestService remoteDatabase;

    private final String TAG = "LOCAL-REMOTE";

    public SynchronizeTasksImpl(ILocalDatabase localDatabase, TaskRestService remoteDatabase) {
        this.localDatabase = localDatabase;
        this.remoteDatabase = remoteDatabase;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAllTasksFromLocalDatabase(String userId) {
        localDatabase.getUnsynchronizedTasks(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unSynchronizedTasks -> {
                            Log.e(TAG, "call getAllTasksFromLocalDatabase success => " + unSynchronizedTasks.toString());

                            if (!unSynchronizedTasks.isEmpty())
                                synchronizeTasksOnServer(userId, unSynchronizedTasks);
                        },
                        throwable -> {
                            Log.e(TAG, "call getAllTasksFromLocalDatabase error");

                            throwable.printStackTrace();
                        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void synchronizeTasksOnServer(String userId, List<TaskModel> unSynchronizedTasks) {


        remoteDatabase.synchronizeTasks(userId, unSynchronizedTasks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userSynchronizedDateModel -> {

                    Log.e(TAG, "call synchronizeTasksOnServer success => " + userSynchronizedDateModel);
                    this.saveLastSynchronizedDateOnLocalDatabase(userSynchronizedDateModel.getUserId(),
                            userSynchronizedDateModel.getLastSynchronizedDate());
                }, throwable -> {
                    Log.e(TAG, "call synchronizeTasksOnServer error => " + throwable);
                    throwable.printStackTrace();
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveLastSynchronizedDateOnLocalDatabase(String userId, String date) {
        localDatabase.saveLastSynchronizedDate(userId, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(voidResult -> {
                    Log.e(TAG, "call saveLastSynchronizedDateOnLocalDatabase success");
                }, throwable -> {
                    Log.e(TAG, "call saveLastSynchronizedDateOnLocalDatabase error");
                });
    }
}
