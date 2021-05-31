package br.com.herco.todoappmvp.fragments.profile;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.Arrays;
import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.adapters.NavAdapter;
import br.com.herco.todoappmvp.fragments.BaseFragment;
import br.com.herco.todoappmvp.fragments.NavItem;
import br.com.herco.todoappmvp.listeners.OnNavDrawerListener;
import br.com.herco.todoappmvp.modules.animation.Circle;
import br.com.herco.todoappmvp.modules.animation.CircleAngleAnimation;

public class ProfileFragment extends BaseFragment<ProfilePresenter> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnNavDrawerListener listener;
    private Circle circle;
    private CircleAngleAnimation animation;

    private List<NavItem> navItems;
    private ListView listViewNavItems;
    private int tasksInProgress = 0;
    private ProgressBar pBTasksInProgress;

    public void setListener(OnNavDrawerListener listener) {
        this.listener = listener;
    }

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public ProfilePresenter loadPresenter() {
        return new ProfilePresenter();
    }

    @Override
    public View inflateFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void initUI() {
        navItems = Arrays.asList(
                new NavItem(R.drawable.ic_icon_bookmark, R.string.templates),
                new NavItem(R.drawable.ic_icon_category, R.string.categories),
                new NavItem(R.drawable.ic_icon_chart_analytics, R.string.analytics),
                new NavItem(R.drawable.ic_icon_settings, R.string.settings)
        );

        NavAdapter navAdapter = new NavAdapter(getActivity(), navItems);

        listViewNavItems = findViewById(R.id.lv_nav_items);
        listViewNavItems.setAdapter(navAdapter);

//        circle = (Circle) findViewById(R.id.circle);
//        animation = new CircleAngleAnimation(circle, 145);

        initTasksInProgress();
        buildBackDrawer();
    }

    private void initTasksInProgress() {
        pBTasksInProgress = findViewById(R.id.progress_bar_task_in_progress);
    }

    /**
     * Class to custom the speed progress bar  animation
     */
    private class ProgressBarAnimation extends Animation {
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

    }

    public void animateAroundProfileCircle() {
        new Handler(Looper.myLooper()).postDelayed(() -> {
            long duration = 1500;
            ProgressBarAnimation anim = new ProgressBarAnimation(pBTasksInProgress, 0f, 40f);
            anim.setDuration(duration);
            pBTasksInProgress.startAnimation(anim);
//            Canvas canvas = new Canvas();
//            Paint paint = new Paint();
//            paint.setMaskFilter(new BlurMaskFilter(1000, BlurMaskFilter.Blur.SOLID));
//
//            canvas.drawPaint(paint);
//            pBTasksInProgress.draw(canvas);
//            pBTasksInProgress.setBackgroundTintMode(PorterDuff.Mode.OVERLAY);

        }, 200);
    }

    public void stayDefaultAroundProfileCircle() {
//        circle.setAngle(-145);
//        animation.setDuration(0);
//        circle.startAnimation(animation);
    }

    private void buildBackDrawer() {
        View backNavButton = findViewById(R.id.frame_layout_close_nav_drawer);
        backNavButton.setOnClickListener((v) -> listener.onBackDrawerPressed());
    }
}