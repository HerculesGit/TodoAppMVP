package br.com.herco.todoappmvp.activities.settings;

public class SettingsPresenter implements SettingsContract.ISettingsPresenter {

    final SettingsContract.ISettingsView settingsView;

    public SettingsPresenter(SettingsContract.ISettingsView settingsView) {
        this.settingsView = settingsView;
    }

    @Override
    public void activeOfflineSynchronization(boolean active) {

        if (active) {
            settingsView.showActiveOfflineSyncMessage();
        } else {
            settingsView.showDisabledOfflineSyncMessage();
        }
    }
}
