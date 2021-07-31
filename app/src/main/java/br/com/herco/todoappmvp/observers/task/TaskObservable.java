package br.com.herco.todoappmvp.observers.task;

import java.util.ArrayList;
import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;

public final class TaskObservable {
    private static List<TaskChannel> channels = new ArrayList<>();
    private static TaskObservable instance;

    private TaskObservable() {
        if (instance == null) {
            instance = new TaskObservable();
        }
    }

    public static void addObserver(TaskChannel channel) {
        channels.add(channel);
    }

    public static void removeObserver(TaskChannel channel) {
        channels.remove(channel);
    }

    public static void onTasksUpdated(List<TaskModel> newTasks) {
        for (TaskChannel channel : channels) {
            channel.onTasksUpdated(newTasks);
        }
    }
}
