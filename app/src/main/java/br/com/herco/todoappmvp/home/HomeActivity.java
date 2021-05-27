package br.com.herco.todoappmvp.home;

import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.Drawable;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.fragments.home.HomeFragment;
import br.com.herco.todoappmvp.fragments.profile.ProfileFragment;
import br.com.herco.todoappmvp.listeners.OnNavDrawerListener;
import br.com.herco.todoappmvp.mvp.BaseActivity;

public class HomeActivity extends BaseActivity<HomeActivityPresenter> implements OnNavDrawerListener {
    private ProfileFragment profileFragment;
    private HomeFragment homeFragment;
    private boolean drawerIsOpen = false;

    @Override
    public HomeActivityPresenter loadPresenter() {
        return new HomeActivityPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initUI() {
        profileFragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_user, profileFragment, profileFragment.getTag())
                .commit();

        homeFragment = new HomeFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_home_task, homeFragment, homeFragment.getTag())
                .commit();


        profileFragment.setListener(this);
        homeFragment.setListener(this);
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
        profileFragment.animateAroundProfileCircle();

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
        profileFragment.stayDefaultAroundProfileCircle();

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
}