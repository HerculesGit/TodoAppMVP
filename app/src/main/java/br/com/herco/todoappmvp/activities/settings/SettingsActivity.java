package br.com.herco.todoappmvp.activities.settings;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.modules.di.TodoAppDependenciesManager;
import br.com.herco.todoappmvp.mvp.BaseActivity;
import br.com.herco.todoappmvp.repositories.task.TaskRestRepositoryImpl;
import br.com.herco.todoappmvp.services.database.preferences.DataBasePreferences;
import br.com.herco.todoappmvp.services.database.sqlite.SQLiteClient;
import br.com.herco.todoappmvp.services.synchronize.ISynchronizedDatabase;

public class SettingsActivity extends BaseActivity<SettingsContract.ISettingsPresenter>
        implements SettingsContract.ISettingsView {

    TextView tvSyncNow;
    ProgressBar pgSyncNow;
    Switch switchOfflineSynchronization;

    @Override
    public SettingsContract.ISettingsPresenter loadPresenter() {

        ISynchronizedDatabase synchronizedDatabase =
                (ISynchronizedDatabase) TodoAppDependenciesManager.getDependency("SYNCHRONIZED_DATABASE");

        SQLiteClient sqlClient = (SQLiteClient) TodoAppDependenciesManager.getDependency("SQLITE_CLIENT");

        return new SettingsPresenter(this, TodoApp.getInstance(),
                synchronizedDatabase, new DataBasePreferences(getSharedPreferences(Constants.Database.DATABASE_PREFERENCES, Context.MODE_PRIVATE)),
                new TaskRestRepositoryImpl(sqlClient, TodoApp.getInstance())
        );
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void initUI() {
        switchOfflineSynchronization = findViewById(R.id.switch_offline_synchronization);
        switchOfflineSynchronization.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.presenter.activeOfflineSynchronization(isChecked);
        });

        final ImageView closeSettings = findViewById(R.id.img_close_settings);
        closeSettings.setOnClickListener(v -> {
            super.onBackPressed();
        });

        tvSyncNow = findViewById(R.id.tv_synchronize_now);
        pgSyncNow = findViewById(R.id.pg_synchronize_now);

        final FrameLayout flSyncNowButton = findViewById(R.id.fl_synchronize_now_button);
        flSyncNowButton.setOnClickListener(v -> {
            presenter.synchronizeNow();
        });
    }

    @Override
    public void onViewReady() {
        super.onViewReady();
        presenter.loadingOfflineSynchronization();
    }

    @Override
    public void showActiveOfflineSyncMessage() {
        super.showToast("Sync activated");
    }

    @Override
    public void showDisabledOfflineSyncMessage() {
        super.showToast("Sync disabled");
    }

    @Override
    public void showSyncNowTextButton() {
        tvSyncNow.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSyncNowTextButton() {
        tvSyncNow.setVisibility(View.GONE);
    }

    @Override
    public void showSyncNowProgress() {
        pgSyncNow.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSyncNowProgress() {
        pgSyncNow.setVisibility(View.GONE);
    }

    @Override
    public void showFinishSyncMessage() {
        super.showToast("Synchronized finished");
    }

    @Override
    public void changeOfflineSyncSwitch(boolean isChecked) {
        switchOfflineSynchronization.setChecked(isChecked);
    }
}