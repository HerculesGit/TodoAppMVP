package br.com.herco.todoappmvp.models;

import com.google.gson.annotations.SerializedName;

public class UserSynchronizedDateModel {

    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("lastSynchronizedDate")
    private String lastSynchronizedDate;

    public UserSynchronizedDateModel() {
    }

    public UserSynchronizedDateModel(String id, String userId, String lastSynchronizedDate) {
        this.id = id;
        this.userId = userId;
        this.lastSynchronizedDate = lastSynchronizedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastSynchronizedDate() {
        return lastSynchronizedDate;
    }

    public void setLastSynchronizedDate(String lastSynchronizedDate) {
        this.lastSynchronizedDate = lastSynchronizedDate;
    }

    @Override
    public String toString() {
        return "UserSynchronizedDateModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", lastSynchronizedDate='" + lastSynchronizedDate + '\'' +
                '}';
    }
}
