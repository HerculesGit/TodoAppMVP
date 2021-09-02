package br.com.herco.todoappmvp.models;

import com.google.gson.annotations.SerializedName;

public class AuthUser {

    @SerializedName("token")
    String token;

    @SerializedName("auth")
    boolean auth;

    @SerializedName("user")
    UserModel userModel;

    @SerializedName("password")
    String password;

    @SerializedName("username")
    String username;

    @SerializedName("email")
    String email;


    public AuthUser(String token, boolean auth) {
        this.token = token;
        this.auth = auth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "token='" + token + '\'' +
                ", auth=" + auth +
                '}';
    }
}
