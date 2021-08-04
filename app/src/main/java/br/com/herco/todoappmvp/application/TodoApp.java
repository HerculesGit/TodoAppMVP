package br.com.herco.todoappmvp.application;

import br.com.herco.todoappmvp.models.UserModel;

public final class TodoApp implements ITodoApp {
    private static TodoApp instance;

    public UserModel currentUser;
    private boolean online = false;
    private boolean offlineSynchronizationValue = true;

    private TodoApp() {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static TodoApp getInstance() {
        if (instance == null) {
            instance = new TodoApp();
        }
        return instance;
    }

    @Override
    public UserModel getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public boolean isOnline() {
        return this.online;
    }

    @Override
    public boolean getOfflineSynchronize() {
        return offlineSynchronizationValue;
    }

    @Override
    public void setOfflineSynchronize(boolean value) {
        this.offlineSynchronizationValue = false;
    }
}
