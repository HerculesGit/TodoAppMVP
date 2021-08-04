package br.com.herco.todoappmvp.home;

import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.Drawable;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import java.util.List;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.fragments.home.HomeFragment;
import br.com.herco.todoappmvp.fragments.profile.ProfileFragment;
import br.com.herco.todoappmvp.listeners.OnNavDrawerListener;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.models.UserModel;
import br.com.herco.todoappmvp.modules.di.TodoAppDependenciesManager;
import br.com.herco.todoappmvp.mvp.BaseActivity;
import br.com.herco.todoappmvp.observers.task.TaskChannel;
import br.com.herco.todoappmvp.observers.task.TaskObservable;
import br.com.herco.todoappmvp.repositories.user.UserRepository;
import br.com.herco.todoappmvp.services.database.sqlite.DataBaseSQLiteHelper;
import br.com.herco.todoappmvp.services.database.sqlite.SQLiteClient;
import br.com.herco.todoappmvp.services.synchronize.ISynchronizedDatabase;
import br.com.herco.todoappmvp.services.synchronize.SynchronizedDatabase;

public class HomeActivity extends BaseActivity<HomeActivityPresenter>
        implements OnNavDrawerListener, IHomeContract.IHomeView, TaskChannel {

    private ProfileFragment profileFragment;
    private HomeFragment homeFragment;
    private boolean drawerIsOpen = false;
    private List<TaskModel> tasksLoaded;

    private boolean isFirstLoading = true;

    @Override
    public HomeActivityPresenter loadPresenter() {
        TodoAppDependenciesManager.addDependency(
                "SYNCHRONIZED_DATABASE", new SynchronizedDatabase(new DataBaseSQLiteHelper(this))
        );
        ISynchronizedDatabase synchronizedDatabase =
                (ISynchronizedDatabase) TodoAppDependenciesManager.getDependency("SYNCHRONIZED_DATABASE");

        TodoAppDependenciesManager.addDependency("SQLITE_CLIENT", new SQLiteClient(synchronizedDatabase));
        return new HomeActivityPresenter(this, new UserRepository());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initUI() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_home_task, homeFragment)
                .replace(R.id.fragment_user, profileFragment, profileFragment.getTag())
                .commit();

        TaskObservable.addObserver(this);
        profileFragment.setOnNabDrawerListener(this);
        homeFragment.setOnNabDrawerListener(this);
    }

    @Override
    public void onViewReady() {
        presenter.getCurrentUser();
        super.onViewReady();
    }

    private void handleNavDrawer() {
        View foregroundView = findViewById(R.id.relative_layout_home_tasks);
        View backgroundView = findViewById(R.id.relative_layout_user);
        Drawable drawable;
        if (drawerIsOpen) {
            drawable = getApplication().getDrawable(R.drawable.layout_default);
            closeNavDrawer(foregroundView, backgroundView, drawable);
        } else {
            drawable = getApplication().getDrawable(R.drawable.layout_radius);
            openNavDrawer(foregroundView, backgroundView, drawable);
        }

        drawerIsOpen = !drawerIsOpen;
    }

    // =====>
    private void animateLeftToRightUserFragment(View backgroundView) {
        backgroundView.animate()
                .translationX(-(backgroundView.getWidth() / 3f))
                .alpha(10.0f)
                .setDuration(0);
        new Handler(Looper.myLooper()).postDelayed(() -> {
            // stay default
            backgroundView.animate()
                    .translationX(0)
                    .alpha(10.0f)
                    .setDuration(100);
        }, 50);
    }

    // <=====
    private void animateRightToLeftUserFragment(View backgroundView) {
        backgroundView.animate()
                .translationX(-(backgroundView.getWidth() / 1f))
                .alpha(5.0f)
                .setDuration(100);
    }

    private void openNavDrawer(View foregroundView, View backgroundView, Drawable drawable) {
        animateLeftToRightUserFragment(backgroundView);
        profileFragment.calculateTasksProgress(tasksLoaded);

        foregroundView.animate()
                .translationX(foregroundView.getWidth() / 1.5f)
                .alpha(10.0f)
                .scaleX(0.85f)
                .scaleY(0.85f)
                .setDuration(200);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            foregroundView.setBackground(drawable);
        }, 0);
    }

    private void closeNavDrawer(View foregroundView, View backgroundView, Drawable drawable) {
        animateRightToLeftUserFragment(backgroundView);

        foregroundView.animate()
                .translationX(0)
                .alpha(1.0f)
                .scaleX(1.f)
                .scaleY(1.f)
                .setDuration(200);

        // To finish animation, return the not radius on the fragment
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            foregroundView.setBackground(drawable);
        }, 200);
    }

    @Override
    public void onBackDrawerPressed() {
        handleNavDrawer();
    }

    @Override
    public void onHamburgerDrawerPressed() {
        handleNavDrawer();
    }

    @Override
    public void getCurrentUserSuccess(UserModel userModel) {
        TodoApp.getInstance().currentUser = userModel;
        Log.e("HomeUSuccess", userModel.toString());
    }

    @Override
    public void getCurrentUserError(String message) {
        Log.e("HomeUError", message);
    }

    @Override
    public void onTasksUpdated(List<TaskModel> tasks) {
        this.tasksLoaded = tasks;

        if (!isFirstLoading) {
            this.profileFragment.calculateTasksProgress(tasksLoaded);
        }
        isFirstLoading = false;
    }
}