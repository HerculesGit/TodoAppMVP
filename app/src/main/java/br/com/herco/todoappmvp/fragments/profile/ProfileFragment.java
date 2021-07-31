package br.com.herco.todoappmvp.fragments.profile;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.Arrays;
import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.activities.settings.SettingsActivity;
import br.com.herco.todoappmvp.adapters.NavAdapter;
import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.fragments.BaseFragment;
import br.com.herco.todoappmvp.fragments.NavItem;
import br.com.herco.todoappmvp.listeners.OnNavDrawerListener;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.modules.animation.ProgressBarAnimation;

public class ProfileFragment extends BaseFragment<ProfilePresenter> implements IProfileContract.IProfileView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnNavDrawerListener listener;

    private List<NavItem> navItems;
    private ListView listViewNavItems;

    private ProgressBarAnimation progressBarAnimation;
    private ProgressBar pBTasksInProgress;

    public void setOnNabDrawerListener(OnNavDrawerListener listener) {
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
        return new ProfilePresenter(this);
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
        navAdapter.setOnNavItemListener((navItem, position) -> {

            // Settings
            if (position == 3) {
                startActivity(new Intent(requireActivity(), SettingsActivity.class));
            }
        });

        listViewNavItems = findViewById(R.id.lv_nav_items);
        listViewNavItems.setAdapter(navAdapter);

        initTasksInProgress();
        buildBackDrawer();
    }

    @Override
    public void onCalculateTasksProgress(float totalProgressTasks) {
        animateAroundProfileCircle(totalProgressTasks);
    }

    private void initTasksInProgress() {
        pBTasksInProgress = findViewById(R.id.progress_bar_task_in_progress);
        progressBarAnimation = new ProgressBarAnimation(pBTasksInProgress, 0f, 0f);
    }

    public void calculateTasksProgress(List<TaskModel> tasks) {
        presenter.calculateTasksProgress(tasks);
    }

    private void animateAroundProfileCircle(float totalProgressTasks) {
        new Handler(Looper.myLooper()).postDelayed(() -> {
            progressBarAnimation.setProgress((int) totalProgressTasks);
        }, Constants.Animations.DELAY_TO_ANIMATE_CIRCLE_AROUND_PROFILE);
    }

    private void buildBackDrawer() {
        View backNavButton = findViewById(R.id.frame_layout_close_nav_drawer);
        backNavButton.setOnClickListener((v) -> listener.onBackDrawerPressed());
    }
}