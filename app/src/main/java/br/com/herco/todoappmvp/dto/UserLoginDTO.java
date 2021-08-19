package br.com.herco.todoappmvp.dto;

import com.google.gson.annotations.SerializedName;

public class UserLoginDTO {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;


    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
