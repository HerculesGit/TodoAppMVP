package br.com.herco.todoappmvp.services.synchronize;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.database.preferences.PreferencesHelper;
import br.com.herco.todoappmvp.services.database.sqlite.DataBaseSQLiteHelper;
import io.reactivex.Observable;

public class SynchronizedDatabase implements ISynchronizedDatabase {

    final DataBaseSQLiteHelper dataHelper;

    public SynchronizedDatabase(DataBaseSQLiteHelper dataHelper) {
        this.dataHelper = dataHelper;
    }


    @Override
    public Observable<TaskModel> createTask(TaskModel taskModel, boolean synchronize) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        String isoDatePattern = Constants.Database.GSON_DATE_FORMAT;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

        String createdAt = simpleDateFormat.format(new Date());
        String updatedAt = simpleDateFormat.format(new Date());

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBaseSQLiteHelper.TaskEntry._ID, taskModel.getId());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_NAME, taskModel.getName());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_IS_DONE, taskModel.isDone());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_SYNCHRONIZED, synchronize);
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_USER_ID, taskModel.getUserId());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_CREATED_AT, createdAt);
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT, updatedAt);
//        values.put(TaskEntry.COLUMN_NAME_SUBTITLE, subtitle);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DataBaseSQLiteHelper.TaskEntry.TABLE_NAME, null, values);
        System.out.println(newRowId);
        return Observable.just(taskModel);
    }

    @Override
    public Observable<TaskModel> updateTask(TaskModel taskModel) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();


        String isoDatePattern = Constants.Database.GSON_DATE_FORMAT;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

        String createdAt = simpleDateFormat.format(taskModel.getCreatedAt());
        String updatedAt = simpleDateFormat.format(taskModel.getUpdatedAt());

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBaseSQLiteHelper.TaskEntry._ID, taskModel.getId());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_NAME, taskModel.getName());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_IS_DONE, taskModel.isDone());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_USER_ID, taskModel.getUserId());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_CREATED_AT, createdAt);
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT, updatedAt);

        if (taskModel.getDeletedAt() != null) {
            String deletedAt = simpleDateFormat.format(taskModel.getDeletedAt());
            values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_DELETED_AT, deletedAt);
        }

        db.update(DataBaseSQLiteHelper.TaskEntry.TABLE_NAME, values,
                DataBaseSQLiteHelper.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(taskModel.getId())});

        return Observable.just(taskModel);
    }

    @Override
    public Observable<List<TaskModel>> getAllTasks(String userId) {
        return Observable.just(getTasks(userId, false));
    }

    @Override
    public Observable<List<TaskModel>> getUnsynchronizedTasks(String userId) {
        return Observable.just(getTasks(userId, true));
    }


    @Override
    public Observable<TaskModel> synchronizeTask(TaskModel taskModel, String lastUUID) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        String isoDatePattern = Constants.Database.GSON_DATE_FORMAT;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

        String createdAt = simpleDateFormat.format(taskModel.getCreatedAt());
        String updatedAt = simpleDateFormat.format(taskModel.getUpdatedAt());

        ContentValues values = new ContentValues();
        values.put(DataBaseSQLiteHelper.TaskEntry._ID, taskModel.getId());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_NAME, taskModel.getName());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_IS_DONE, taskModel.isDone());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_SYNCHRONIZED, 1);
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_USER_ID, taskModel.getUserId());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_CREATED_AT, createdAt);
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT, updatedAt);

        if (taskModel.getDeletedAt() != null) {
            String deletedAt = simpleDateFormat.format(taskModel.getUpdatedAt());
            values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT, deletedAt);
        }

        db.update(DataBaseSQLiteHelper.TaskEntry.TABLE_NAME, values,
                DataBaseSQLiteHelper.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(lastUUID)});
        return Observable.just(taskModel);
    }


    private List<TaskModel> getTasks(String userId, boolean addSynchronizedParameter) {

        SQLiteDatabase db = dataHelper.getReadableDatabase();
        final String query = "SELECT * FROM " + DataBaseSQLiteHelper.TaskEntry.TABLE_NAME
                + " WHERE " + DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_USER_ID + "='" + userId + "'"

                + (addSynchronizedParameter ? " AND " + DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_SYNCHRONIZED + "=0" : "")

                + " AND " + DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_DELETED_AT + " IS NULL";


        Cursor cursor = db.rawQuery(query, null);

        List<TaskModel> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String uuid = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_NAME));
                int isDone = cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_IS_DONE));
                String createdAt = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_CREATED_AT));
                String updatedAt = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT));
                String deletedAt = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_DELETED_AT));

                TaskModel taskModel = new TaskModel(name, isDone == 1);
                taskModel.setId(uuid);


                try {
                    String isoDatePattern = Constants.Database.GSON_DATE_FORMAT;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                    simpleDateFormat.applyPattern(isoDatePattern);

                    taskModel.setCreatedAt(simpleDateFormat.parse(createdAt));
                    taskModel.setUpdatedAt(simpleDateFormat.parse(updatedAt));
                    if (deletedAt != null) {
                        taskModel.setDeletedAt(simpleDateFormat.parse(deletedAt));
                    }

                    tasks.add(taskModel);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return tasks;
    }
}
