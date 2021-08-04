package br.com.herco.todoappmvp.activities.settings;

public interface SettingsContract {
    interface ISettingsView {
        void showActiveOfflineSyncMessage();

        void showDisabledOfflineSyncMessage();

        void showSyncNowTextButton();

        void hideSyncNowTextButton();

        void showSyncNowProgress();

        void hideSyncNowProgress();

        void showFinishSyncMessage();

        void changeOfflineSyncSwitch(boolean isChecked);
    }

    interface ISettingsPresenter {

        void loadingOfflineSynchronization();

        void activeOfflineSynchronization(boolean active);

        void synchronizeNow();
    }
}
