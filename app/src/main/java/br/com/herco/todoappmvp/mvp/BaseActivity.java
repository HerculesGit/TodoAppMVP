package br.com.herco.todoappmvp.mvp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.utils.network.NetworkChangeReceiver;
import br.com.herco.todoappmvp.utils.network.OnNetworkChangeListener;

// TODO: CREATE A BASE COMPONENT TO REPLACE THE BOTH CODE OF THE BaseActivity and BaseFragment
public abstract class BaseActivity<T> extends AppCompatActivity implements IBaseView<T>, OnNetworkChangeListener {
    public T presenter;

    private boolean onViewReadyCalled = false;

    public abstract T loadPresenter();

    public abstract int getLayoutId();

    public abstract void initUI();

    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
        }
    };

    @Override
    public void onViewReady() {
        onViewReadyCalled = true;
    }


    @Override
    public void onNetworkChange(boolean isOnline) {
        if (onViewReadyCalled) {
            LinearLayout noInternetConnectionView = findViewById(R.id.linear_layout_no_internet_connection);
            if (noInternetConnectionView != null) {
                noInternetConnectionView.setVisibility(isOnline ? View.GONE : View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        networkChangeReceiver.setOnNetworkChange(this);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(this.networkChangeReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {

        unregisterReceiver(this.networkChangeReceiver);
        super.onPause();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        presenter = loadPresenter();
        initUI();
        onViewReady();
    }

    protected void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void showSnackBar(View parentLayout, String text, String textButton, View.OnClickListener onPressed) {
        Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction(textButton, view -> onPressed.onClick(view))
                .setActionTextColor(getResources().getColor(R.color.white))
                .show();
    }
}
