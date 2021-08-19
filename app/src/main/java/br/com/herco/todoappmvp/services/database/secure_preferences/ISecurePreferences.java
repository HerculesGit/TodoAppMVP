package br.com.herco.todoappmvp.services.database.secure_preferences;

public interface ISecurePreferences {
    String[] getUserCredentials();

    void saveUserCredentials(String username, String password);

    void saveToken(String token);

    String getToken();
}
