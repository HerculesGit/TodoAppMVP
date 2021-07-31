package br.com.herco.todoappmvp.activities.settings;

import android.widget.ImageView;
import android.widget.Switch;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.mvp.BaseActivity;

public class SettingsActivity extends BaseActivity<SettingsContract.ISettingsPresenter>
        implements SettingsContract.ISettingsView {

    @Override
    public SettingsContract.ISettingsPresenter loadPresenter() {
        return new SettingsPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void initUI() {
        final Switch switchOfflineSynchronization = findViewById(R.id.switch_offline_synchronization);
        switchOfflineSynchronization.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.presenter.activeOfflineSynchronization(isChecked);
        });

        final ImageView closeSettings = findViewById(R.id.img_close_settings);
        closeSettings.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }

    @Override
    public void showActiveOfflineSyncMessage() {
        super.showToast("Sync activated");
    }

    @Override
    public void showDisabledOfflineSyncMessage() {
        super.showToast("Sync disabled");
    }
}