package br.com.herco.todoappmvp.utils.activity;

import android.content.Context;

public final class ActivityUtils {

    private static ActivityUtils instance;


    private ActivityUtils() {
        // do nothing
    }

    public static ActivityUtils getInstance() {
        if (instance == null) {
            instance = new ActivityUtils();
        }
        return instance;
    }

    private Context currentContext;

    public void setCurrentContext(Context context) {
        this.currentContext = context;
    }

    public Context getCurrentContext() {
        return currentContext;
    }
}
