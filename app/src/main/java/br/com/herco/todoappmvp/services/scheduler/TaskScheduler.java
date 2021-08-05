package br.com.herco.todoappmvp.services.scheduler;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TaskScheduler {
    int doTryTimes = 0;
    Timer timer;
    boolean timerHasExecuted = false;

    private final String TAG = "TaskScheduler";

    /**
     * @param times - do try times after error
     */
    public void callOnMinuter(int times, long delay, Action action) {
        if (timer == null) {
            initValues();
            timer = new Timer();
        }

        if ((doTryTimes - 1) > 0) {
            Log.e(TAG, "catch. Try again => " + (doTryTimes - 1));
        }

        try {
            action.doAction();
            timerHasExecuted = true;
        } catch (Exception e) {
            timerHasExecuted = false;
            doTryTimes += 1;
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // doTry=1 | max=2
                if (doTryTimes >= times + 1 || timerHasExecuted) {
                    timer.cancel();
                    timer.purge();
                    Log.e(TAG, "Finalized");
                    Log.e(TAG, "TaskScheduler{" +
                            "doTryTimes=" + doTryTimes +
                            ", timer=" + timer +
                            ", timerHasExecuted=" + timerHasExecuted +
                            '}');
                    initValues();


                } else {
                    callOnMinuter(times, delay, action);
                }
            }
        }, delay);
    }

    private void initValues() {
        timer = null;
        doTryTimes = 0;
        timerHasExecuted = false;
    }

    public interface Action {
        void doAction() throws Exception;
    }
}
