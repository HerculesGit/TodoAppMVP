package br.com.herco.todoappmvp.services.scheduler;

import android.util.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DatabaseSchedulerSynchronizer {
    final String TAG = "DatabaseScheduler";
    private OnTimeExpired timerExpired;
    private Timer myTimer = new Timer();

    private TimerTask mTimerTask;


    public void start() {
        if (mTimerTask != null) return;

        Log.e(TAG, "start " + new Date().toString());
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
//                Log.e(TAG, "call timerExpired " + new Date().toString());
//                timerExpired.onExpired();
            }
        };
        myTimer.schedule(mTimerTask, 0, 60000);
    }

    public void stop() {
        myTimer.cancel();
        myTimer.purge();
        mTimerTask.cancel();
        Log.e(TAG, "stop " + new Date().toString());
        mTimerTask = null;
    }

    public void setTimerExpired(OnTimeExpired timerExpired) {
        this.timerExpired = timerExpired;
    }
}
