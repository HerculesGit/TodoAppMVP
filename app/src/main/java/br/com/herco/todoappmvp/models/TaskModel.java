package br.com.herco.todoappmvp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class TaskModel implements Serializable, Comparable<TaskModel> {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("isDone")
    private boolean isDone;

    @SerializedName("createAt")
    private Date createAt;

    @SerializedName("updateAt")
    private Date updateAt;

    public TaskModel(String name) {
        this.name = name;
        this.isDone = false;

        this.createAt = new Date();
        this.updateAt = new Date();
    }

    public TaskModel(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;

        this.createAt = new Date();
        this.updateAt = new Date();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }


    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", name=" + name +
                ", isDone=" + isDone +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

//    public String toJson() {
//        return "{" +
//                "id=" + id +
//                ", name=" + name +
//                ", isDone=" + isDone +
//                ", createAt=" + createAt +
//                ", updateAt=" + updateAt +
//                '}';
//    }
//
//    public TaskModel fromJson(String taskStr) {
//        String[] values = taskStr.split("=");
//        setId(Integer.valueOf(values[1]));
//        setName(values[3]);
//        setDone(Boolean.valueOf(values[5]));
//
//        return this;
//    }

    @Override
    public int compareTo(TaskModel task) {
        return (this.updateAt.after(task.updateAt)) ? 0 : -1;
    }
}
