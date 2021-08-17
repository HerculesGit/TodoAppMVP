package br.com.herco.todoappmvp.models;

import com.google.gson.annotations.SerializedName;

public class AuthUser {

    @SerializedName("token")
    String token;

    @SerializedName("auth")
    boolean auth;


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

    @Override
    public String toString() {
        return "AuthUser{" +
                "token='" + token + '\'' +
                ", auth=" + auth +
                '}';
    }
}