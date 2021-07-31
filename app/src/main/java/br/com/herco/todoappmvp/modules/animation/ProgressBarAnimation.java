package br.com.herco.todoappmvp.modules.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

/**
 * Class to custom the speed progress bar  animation
 */
public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float to;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }

        if (progress > progressBar.getMax()) {
            progress = progressBar.getMax();
        }

        long mStepDuration = 10;
        to = (float) progress;
        from = progressBar.getProgress();
        setDuration((long) (Math.abs(to - from) * mStepDuration));
        progressBar.startAnimation(this);
    }
}