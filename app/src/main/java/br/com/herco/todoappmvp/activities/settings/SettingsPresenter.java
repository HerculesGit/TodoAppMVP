package br.com.herco.todoappmvp.activities.settings;

import android.annotation.SuppressLint;

import java.util.List;

import br.com.herco.todoappmvp.application.ITodoApp;
import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.repositories.task.ITaskRestRepository;
import br.com.herco.todoappmvp.services.database.IDataBaseService;
import br.com.herco.todoappmvp.services.database.localdatabase.ILocalDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenter implements SettingsContract.ISettingsPresenter {

    final SettingsContract.ISettingsView settingsView;
    final ITodoApp todoApp;
    final IDataBaseService localStorage;
    final ITaskRestRepository taskRestRepository;
    final ILocalDatabase synchronizedDatabase;

    public SettingsPresenter(SettingsContract.ISettingsView settingsView,
                             ITodoApp todoApp,
                             ILocalDatabase synchronizedDatabase, IDataBaseService localStorage,
                             ITaskRestRepository taskRestRepository) {
        this.settingsView = settingsView;
        this.todoApp = todoApp;
        this.synchronizedDatabase = synchronizedDatabase;
        this.localStorage = localStorage;
        this.taskRestRepository = taskRestRepository;
    }

    @Override
    public void activeOfflineSynchronization(boolean active) {
        localStorage.saveOffLineSynchronizationValue(active);

        if (active) {
            settingsView.showActiveOfflineSyncMessage();
        } else {
            settingsView.showDisabledOfflineSyncMessage();
        }
    }

    @Override
    public void loadingOfflineSynchronization() {
        boolean offLineSynchronizationValue = localStorage.getOffLineSynchronizationValue();
        settingsView.changeOfflineSyncSwitch(offLineSynchronizationValue);
    }

    @SuppressLint("CheckResult")
    @Override
    public void synchronizeNow() {
        settingsView.hideSyncNowTextButton();
        settingsView.showSyncNowProgress();
        String userId = todoApp.getCurrentUser().getId();

//        try {
//            synchronizedDatabase.getUnsynchronizedTasks(userId)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe((tasks) -> {
//                        synchronizeTasks(userId, tasks);
//                    }, throwable -> {
//                        throwable.printStackTrace();
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @SuppressLint("CheckResult")
    private void synchronizeTasks(String userId, List<TaskModel> tasks) {
        try {
            taskRestRepository.synchronizeTasks(userId, tasks)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((synchronizedTasks) -> {
                        settingsView.showSyncNowTextButton();
                        settingsView.hideSyncNowProgress();
                        settingsView.showFinishSyncMessage();
                    }, throwable -> {
                        throwable.printStackTrace();
                    });
        } catch (TaskException e) {
            e.printStackTrace();
        }
    }
}
