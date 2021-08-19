package br.com.herco.todoappmvp.services.database.secure_preferences;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import androidx.security.crypto.EncryptedSharedPreferences;
//import androidx.security.crypto.MasterKeys;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//
//public class SecurePreferencesImpl implements ISecurePreferences {
//    private static final String KEY_USERNAME = "KEY_USERNAME";
//    private static final String KEY_PASSWORD = "KEY_PASSWORD";
//    private static final String KEY_TOKEN = "KEY_TOKEN";
//    private SharedPreferences sharedPreferences;
//    private SharedPreferences.Editor editor;
//
//    public SecurePreferencesImpl(Context context) throws GeneralSecurityException, IOException {
//        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
//        sharedPreferences = EncryptedSharedPreferences.create(
//                "secret_shared_prefs",
//                masterKeyAlias,
//                context,
//                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        );
//
//        editor = sharedPreferences.edit();
//    }
//
//
//    @Override
//    public String[] getUserCredentials() {
//        final String username = sharedPreferences.getString(KEY_USERNAME, null);
//        final String password = sharedPreferences.getString(KEY_PASSWORD, null);
//
//        return new String[]{
//                username,
//                password
//        };
//    }
//
//    @Override
//    public void saveUserCredentials(String username, String password) {
//        editor.putString(KEY_USERNAME, username);
//        editor.putString(KEY_PASSWORD, password);
//        editor.commit();
//    }
//
//    @Override
//    public void saveToken(String token) {
//        editor.putString(KEY_TOKEN, token);
//        editor.commit();
//    }
//
//    public String getToken() {
//        return sharedPreferences.getString(KEY_TOKEN, "");
//    }
//}

import android.content.SharedPreferences;

import br.com.herco.todoappmvp.services.database.preferences.DataBasePreferences;

public class SecurePreferencesImpl extends DataBasePreferences implements ISecurePreferences {

    private final String KEY_SECURE_USERNAME = "KEY_SECURE_USERNAME";
    private final String KEY_SECURE_PASSWORD = "KEY_SECURE_PASSWORD";
    private final String KEY_SECURE_TOKEN = "KEY_SECURE_TOKEN";

    final SharedPreferences sharedPreferences;
    final SharedPreferences.Editor editor;

    public SecurePreferencesImpl(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    @Override
    public String[] getUserCredentials() {
        final String username = sharedPreferences.getString(KEY_SECURE_USERNAME, null);
        final String password = sharedPreferences.getString(KEY_SECURE_PASSWORD, null);

        return new String[]{
                username, password
        };
    }

    @Override
    public void saveUserCredentials(String username, String password) {
        editor.putString(KEY_SECURE_USERNAME, username);
        editor.putString(KEY_SECURE_PASSWORD, password);

        editor.commit();
    }

    @Override
    public void saveToken(String token) {
        editor.putString(KEY_SECURE_TOKEN, token);
        editor.commit();
    }

    @Override
    public String getToken() {
        return sharedPreferences.getString(KEY_SECURE_TOKEN, null);
    }
}