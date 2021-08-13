package br.com.herco.todoappmvp.services.database.localdatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.exceptions.TaskException;
import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.database.preferences.PreferencesHelper;
import br.com.herco.todoappmvp.services.database.sqlite.DataBaseSQLiteHelper;
import io.reactivex.Observable;

public class LocalDatabase implements ILocalDatabase {

    final DataBaseSQLiteHelper dataHelper;

    private final String TAG = "LOCAL_DATABASE";

    public LocalDatabase(DataBaseSQLiteHelper dataHelper) {
        this.dataHelper = dataHelper;
    }

    @Override
    public Observable<TaskModel> createTask(TaskModel taskModel) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        String isoDatePattern = Constants.Database.GSON_DATE_FORMAT;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

        String createdAt = simpleDateFormat.format(new Date());
        String updatedAt = simpleDateFormat.format(new Date());

        taskModel.setId(PreferencesHelper.getUUID());

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataBaseSQLiteHelper.TaskEntry._ID, taskModel.getId());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_NAME, taskModel.getName());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_IS_DONE, taskModel.isDone());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_USER_ID, taskModel.getUserId());
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_CREATED_AT, createdAt);
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT, updatedAt);

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
        return Observable.just(getTasks(userId));
    }

    @Override
    public Observable<TaskModel> deleteTask(String taskId) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        String isoDatePattern = Constants.Database.GSON_DATE_FORMAT;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String deletedAt = simpleDateFormat.format(new Date());
        String updateAt = simpleDateFormat.format(new Date());

        ContentValues values = new ContentValues();
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_DELETED_AT, deletedAt);
        values.put(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT, updateAt);
        Observable<TaskModel> observable = null;
        try {
            observable = getOneTask(taskId);
        } catch (TaskException e) {
            e.printStackTrace();
        }

        db.update(DataBaseSQLiteHelper.TaskEntry.TABLE_NAME, values,
                DataBaseSQLiteHelper.TaskEntry._ID + " = ?",
                new String[]{taskId});
        return observable;
    }

    public Observable<TaskModel> getOneTask(String taskId) throws TaskException {
        final String query = "SELECT * FROM " + DataBaseSQLiteHelper.TaskEntry.TABLE_NAME
                + " WHERE " + DataBaseSQLiteHelper.TaskEntry._ID + "='" + taskId + "'";
        SQLiteDatabase db = dataHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            TaskModel taskModel = getTaskModelFromDatabase(cursor);
            return Observable.just(taskModel);
        } else {
            throw new TaskException("not found task: " + taskId);
        }
    }

    @Override
    public Observable<List<TaskModel>> getUnsynchronizedTasks(String userId) {
        SQLiteDatabase db = dataHelper.getReadableDatabase();

        final String query = "SELECT * FROM " + DataBaseSQLiteHelper.SynchronizedDataTask.TABLE_NAME
                + " WHERE " + DataBaseSQLiteHelper.SynchronizedDataTask.COLUMN_NAME_USER_ID + "='" + userId + "'";

        Cursor cursor = db.rawQuery(query, null);
        String lastSynchronizedSTRDate = null;
        if (cursor.moveToFirst()) {
            lastSynchronizedSTRDate = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.SynchronizedDataTask.COLUMN_NAME_LAST_SYNCHRONIZED_DATE_TASK));
        }

        if (lastSynchronizedSTRDate == null) {
            return getAllTasks(userId);
        }

        List<TaskModel> tasks = new ArrayList<>();

        String whereToTask = "SELECT * FROM " + DataBaseSQLiteHelper.TaskEntry.TABLE_NAME
                + " WHERE " + "date(" + DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT + ") > ?";

        String[] whereValue = {lastSynchronizedSTRDate};
        cursor = db.rawQuery(whereToTask, whereValue);


        if (cursor.moveToFirst()) {
            do {
                TaskModel taskModel = getTaskModelFromDatabase(cursor);
                tasks.add(taskModel);
            } while (cursor.moveToNext());
        }

        Log.e(TAG, "QUERY => " + whereToTask + " ----> " + lastSynchronizedSTRDate + "\n Tasks=" + tasks);
        return Observable.just(tasks);
    }

    @Override
    public Observable saveLastSynchronizedDate(String userId, String isoDate) {
        assert userId != null;

        if (existsUserDataOnSynchronizedDataTaskTable(userId)) {
            Log.e(TAG, "existsUserDataOnSynchronizedDataTaskTable = true - " + userId);
            SQLiteDatabase db = dataHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(DataBaseSQLiteHelper.SynchronizedDataTask.COLUMN_NAME_LAST_SYNCHRONIZED_DATE_TASK, isoDate);

            db.update(DataBaseSQLiteHelper.SynchronizedDataTask.TABLE_NAME, values,
                    DataBaseSQLiteHelper.SynchronizedDataTask.COLUMN_NAME_USER_ID + " = ?",
                    new String[]{userId});
        } else {
            Log.e(TAG, "existsUserDataOnSynchronizedDataTaskTable = false - " + userId);

            insertLastSynchronizedDate(userId, isoDate);
        }
        return Observable.just("");
    }

    private boolean existsUserDataOnSynchronizedDataTaskTable(String userId) {
        SQLiteDatabase db = dataHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DataBaseSQLiteHelper.SynchronizedDataTask.TABLE_NAME
                + " WHERE " + DataBaseSQLiteHelper.SynchronizedDataTask.COLUMN_NAME_USER_ID + "='" + userId + "'";

        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    private void insertLastSynchronizedDate(String userId, String isoDate) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseSQLiteHelper.SynchronizedDataTask._ID, PreferencesHelper.getUUID());
        values.put(DataBaseSQLiteHelper.SynchronizedDataTask.COLUMN_NAME_LAST_SYNCHRONIZED_DATE_TASK, isoDate);
        values.put(DataBaseSQLiteHelper.SynchronizedDataTask.COLUMN_NAME_USER_ID, userId);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DataBaseSQLiteHelper.SynchronizedDataTask.TABLE_NAME, null, values);
        System.out.println(newRowId);
    }

    private List<TaskModel> getTasks(String userId) {

        SQLiteDatabase db = dataHelper.getReadableDatabase();
        final String query = "SELECT * FROM " + DataBaseSQLiteHelper.TaskEntry.TABLE_NAME
                + " WHERE " + DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_USER_ID + "='" + userId + "'"
                + " AND " + DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_DELETED_AT + " IS NULL";


        Cursor cursor = db.rawQuery(query, null);

        List<TaskModel> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TaskModel taskModel = getTaskModelFromDatabase(cursor);
                tasks.add(taskModel);
            } while (cursor.moveToNext());
        }
        return tasks;
    }

    private TaskModel getTaskModelFromDatabase(Cursor cursor) {
        String uuid = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry._ID));
        String name = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_NAME));
        int isDone = cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_IS_DONE));
        String createdAt = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_CREATED_AT));
        String updatedAt = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_UPDATED_AT));
        String deletedAt = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_DELETED_AT));
        String userId = cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.TaskEntry.COLUMN_NAME_USER_ID));

        TaskModel taskModel = new TaskModel(name, isDone == 1);
        taskModel.setId(uuid);
        taskModel.setUserId(userId);

        try {
            String isoDatePattern = Constants.Database.GSON_DATE_FORMAT;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern(isoDatePattern);

            taskModel.setCreatedAt(simpleDateFormat.parse(createdAt));
            taskModel.setUpdatedAt(simpleDateFormat.parse(updatedAt));
            if (deletedAt != null) {
                taskModel.setDeletedAt(simpleDateFormat.parse(deletedAt));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return taskModel;
    }
}
