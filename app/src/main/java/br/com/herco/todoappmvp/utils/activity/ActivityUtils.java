package br.com.herco.todoappmvp.utils.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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

    /**
     * Go to next activity stayed the previous
     */
    public void to(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * Go to next activity and remove the previous
     */
    public void toOff(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        activity.finish();
    }

    private Context currentContext;

    public void setCurrentContext(Context context) {
        this.currentContext = context;
    }

    public Context getCurrentContext() {
        return currentContext;
    }
}
