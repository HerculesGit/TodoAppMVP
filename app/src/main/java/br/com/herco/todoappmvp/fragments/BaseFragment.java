package br.com.herco.todoappmvp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import br.com.herco.todoappmvp.R;

public abstract class BaseFragment<T> extends Fragment {

    public T presenter;

    public abstract T loadPresenter();

    public abstract View inflateFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void initUI();

    public View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflateFragment(inflater, container, savedInstanceState);
        presenter = loadPresenter();
        initUI();
        return mView;
    }

    /**
     * My findViewById implementation
     */
    protected <T extends View> T findViewById(int idResource) {
        return mView.findViewById(idResource);
    }

    /**
     * Show Toast
     */
    protected void showToast(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Show SnackBar
     */
    protected void showSnackBar(View parentLayout, String text, String textButton, View.OnClickListener onPressed) {
        showSnackBar(parentLayout, null, text, textButton, onPressed);
    }

    protected void showSnackBar(View parentLayout, String uuid, String text, String textButton, View.OnClickListener onPressed) {
        Snackbar snackbar = Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                .setAction(textButton, view -> onPressed.onClick(view))
                .setActionTextColor(getResources().getColor(R.color.white));
        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE) {
                    onSnackBarDismissEventTimeout(uuid);
                } else if (event == Snackbar.Callback.DISMISS_EVENT_MANUAL) {
                    onSnackBarDismissEvent(uuid);
                }
            }

            @Override
            public void onShown(Snackbar snackbar) {
            }
        });
        snackbar.show();
    }

    /**
     *
     */
    protected void onSnackBarDismissEvent(String uuid) {

    }

    /**
     *
     */
    protected void onSnackBarDismissEventTimeout(String uuid) {

    }
}
