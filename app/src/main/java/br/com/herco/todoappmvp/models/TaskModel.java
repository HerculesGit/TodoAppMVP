package br.com.herco.todoappmvp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskModel implements Serializable, Comparable<TaskModel> {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("isDone")
    private boolean isDone;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("updatedAt")
    private Date updatedAt;

    @SerializedName("deletedAt")
    private Date deletedAt;

    @SerializedName("userId")
    private String userId;

    public TaskModel() {
    }

    public TaskModel(String name) {
        this.name = name;
        this.isDone = false;

        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public TaskModel(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;

        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public TaskModel(String id, String name, boolean isDone, String userId) {
        this.id = id;
        this.name = name;
        this.isDone = isDone;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isDone=" + isDone +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    @Override
    public int compareTo(TaskModel task) {
        return (this.updatedAt.after(task.updatedAt)) ? 0 : -1;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("isDone", isDone);
        result.put("createdAt", createdAt);
        result.put("updatedAt", updatedAt);
        result.put("deletedAt", deletedAt);
        return result;
    }
}
