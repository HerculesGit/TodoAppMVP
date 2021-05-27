package br.com.herco.todoappmvp.dto;

import java.io.Serializable;

import br.com.herco.todoappmvp.models.TaskModel;

public class TaskDTO implements Serializable {
    private TaskModel taskModel;

//    public TaskDTO(TaskModel taskModel) {
//        this.taskModel = taskModel;
//    }

    public TaskModel getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }
}