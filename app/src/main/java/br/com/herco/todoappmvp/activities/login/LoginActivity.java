package br.com.herco.todoappmvp.activities.login;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.material.textfield.TextInputEditText;

import br.com.herco.todoappmvp.R;
import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.home.HomeActivity;
import br.com.herco.todoappmvp.mvp.BaseActivity;
import br.com.herco.todoappmvp.repositories.user.UserRepositoryImpl;
import br.com.herco.todoappmvp.services.database.retrofit.ApiClient;
import br.com.herco.todoappmvp.services.database.retrofit.UserRestService;
import br.com.herco.todoappmvp.services.database.secure_preferences.SecurePreferencesImpl;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {
    private TextInputEditText textInputUserName;
    private TextInputEditText textInputPassword;
    private LinearLayout loginButton;

    @Override
    public LoginPresenter loadPresenter() {
        return new LoginPresenter(this, new UserRepositoryImpl(
                ApiClient.create(UserRestService.class)
        ), new SecurePreferencesImpl(getSharedPreferences(
                Constants.Database.DATABASE_PREFERENCES, Context.MODE_PRIVATE)));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initUI() {
        this.textInputUserName = findViewById(R.id.text_input_username);
        this.textInputPassword = findViewById(R.id.text_input_password);
        this.loginButton = findViewById(R.id.linear_layout_login_button);

        this.loginButton.setOnClickListener(v -> presenter.doLogin());
    }

    @Override
    public String getPassword() {
        return getTextByTextInputEditText(this.textInputPassword);
    }

    @Override
    public String getUserName() {
        return getTextByTextInputEditText(this.textInputUserName);
    }

    @Override
    public void loginSuccess() {
        activityUtils.toOff(this, HomeActivity.class);
    }

    @Override
    public void loginError(int resId) {
        showToast(resId);
        enableLoginButton();
    }

    @Override
    public void disableLoginButton() {
        loginButton.setClickable(false);
        loginButton.setBackground(getDrawable(R.drawable.round_disable_primary_button));
    }

    @Override
    public void enableLoginButton() {
        loginButton.setClickable(true);
        loginButton.setBackground(getDrawable(R.drawable.round_new_task_button));
    }

    @Override
    public void showUsernameError(@StringRes int resId) {
        showToast(resId);
    }

    @Override
    public void showPasswordError(int resId) {
        showToast(resId);
    }

    @NonNull
    private String getTextByTextInputEditText(TextInputEditText inputEditText) {
        return inputEditText.getText().toString();
    }
}