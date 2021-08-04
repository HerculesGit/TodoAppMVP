package br.com.herco.todoappmvp.services.database.preferences;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.database.IDataBaseService;

public class DataBasePreferences implements IDataBaseService {

    final SharedPreferences sharedPreferences;
    final SharedPreferences.Editor editor;


    public DataBasePreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    @Override
    public TaskModel saveTask(TaskModel taskModel) {
        taskModel.setId(PreferencesHelper.getUUID());

        List<TaskModel> tasks = getAllTaskByUser(0);
        tasks.add(taskModel);

        saveOnPreferences(tasks);
        return taskModel;
    }

    @Override
    public TaskModel updateTask(TaskModel taskToUpdateModel) {

        List<TaskModel> tasks = getAllTaskByUser(0);
        int index = this.findTaskById(taskToUpdateModel.getId(), tasks);
        TaskModel taskModel = tasks.get(index);

        taskModel.setName(taskToUpdateModel.getName());
        taskModel.setDone(taskToUpdateModel.isDone());

        tasks.set(index, taskModel);

        saveOnPreferences(tasks);
        return taskModel;
    }

    @Override
    public List<TaskModel> getAllTaskByUser(int userId) {
        String jsonTasks = sharedPreferences.getString(Constants.Database.ALL_TASKS, "");
        Type type = new TypeToken<List<TaskModel>>() {
        }.getType();

        Gson gson = new Gson();
        List<TaskModel> taskModels = gson.fromJson(jsonTasks, type);

        if (taskModels == null) return new ArrayList<>();
        return taskModels;
    }

    private void saveOnPreferences(List<TaskModel> tasks) {
        Gson gson = new Gson();
        String jsonTasks = gson.toJson(tasks);

        Log.d("saveTask", jsonTasks);

        editor.putString(Constants.Database.ALL_TASKS, jsonTasks);
        editor.commit();
    }

    private int findTaskById(String uuid, List<TaskModel> tasks) {
        int index = 0;
        for (TaskModel task : tasks) {
            if (task.getId().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public void saveOffLineSynchronizationValue(boolean value) {
        editor.putBoolean(Constants.Database.OFFLINE_SYNCHRONIZATION, value);
        editor.commit();
    }

    @Override
    public boolean getOffLineSynchronizationValue() {
        return sharedPreferences.getBoolean(Constants.Database.OFFLINE_SYNCHRONIZATION, false);
    }
}
