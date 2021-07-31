package br.com.herco.todoappmvp.activities.settings;

public interface SettingsContract {
    interface ISettingsView {
        void showActiveOfflineSyncMessage();

        void showDisabledOfflineSyncMessage();
    }

    interface ISettingsPresenter {
        void activeOfflineSynchronization(boolean active);
    }
}
