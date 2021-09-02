package br.com.herco.todoappmvp.test_utils;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * When execute the test without afterClass below code, the throws exception.
 * <br/>
 * This error occurs because the default scheduler returned by
 * <br/>
 * AndroidSchedulers.mainThread() is an instance of LooperScheduler and relies on Android dependencies that are not available in JUnit tests.
 * <br/>
 * Source; https://stackoverflow.com/questions/43356314/android-rxjava-2-junit-test-getmainlooper-in-android-os-looper-not-mocked-runt/43356315#43356315
 */

public class RemoveLooperSchedulerErrorUtils {

    /**
     * Call this method on <strong>@BeforeClass</strong>
     * </br>
     *
     * <p>
     * &nbsp;	@BeforeClass public static void afterClass() {
     * &nbsp;&nbsp;		RemoveLooperSchedulerErrorUtils.execute();
     * &nbsp;	}
     * </p>
     * </code>
     */
    public static void execute() {

        Scheduler immediate = new Scheduler() {

            @NonNull
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {

                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @NonNull
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }
}
