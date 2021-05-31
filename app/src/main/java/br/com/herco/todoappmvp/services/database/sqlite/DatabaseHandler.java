package br.com.herco.todoappmvp.services.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.herco.todoappmvp.models.TaskModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo_db";
    private static final String TABLE_TASKS = "tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IS_DONE = "is_done";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";
    private static final String KEY_DELETED_AT = "deleted_at";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_IS_DONE + " INTEGER DEFAULT 0,"
                + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + KEY_UPDATED_AT + " DATETIME,"
                + KEY_DELETED_AT + " DATETIME"
                + ")";

        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        //create table again
        onCreate(db);
    }

    /**
     *
     */
    public Integer addTask(TaskModel task) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_IS_DONE, task.isDone());
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_UPDATED_AT, getDateTime());

        long id = database.insert(TABLE_TASKS, null, values);
        database.close();

        return Integer.valueOf(String.valueOf(id));
    }

    /**
     *
     */
    public TaskModel getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{
                        KEY_ID, KEY_NAME, KEY_IS_DONE, KEY_CREATED_AT, KEY_UPDATED_AT, KEY_DELETED_AT
                }, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        TaskModel taskModel = new TaskModel(null);

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {

                int nameIndex = cursor.getColumnIndex(KEY_NAME);
                int isDoneIndex = cursor.getColumnIndex(KEY_IS_DONE);
                int createdAtIndex = cursor.getColumnIndex(KEY_CREATED_AT);
                int updatedAtIndex = cursor.getColumnIndex(KEY_UPDATED_AT);

                taskModel.setId(Integer.parseInt(cursor.getString(0)));
                taskModel.setName(cursor.getString(nameIndex));
                Boolean isDone = cursor.getInt(isDoneIndex) == 1;
                taskModel.setDone(isDone);
                taskModel.setCreatedAt(dateFromStringFormatted(cursor.getString(createdAtIndex)));
                taskModel.setUpdatedAt(dateFromStringFormatted(cursor.getString(updatedAtIndex)));
            }
        }
        return taskModel;
    }

    /**
     *
     */
    public List<TaskModel> getAllTasks() {
        List<TaskModel> taskModels = new ArrayList();

        final String selectQuery =
                String.format("SELECT * FROM %s WHERE %s IS NULL", TABLE_TASKS, KEY_DELETED_AT);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TaskModel taskModel = new TaskModel(null);

                int nameIndex = cursor.getColumnIndex(KEY_NAME);
                int isDoneIndex = cursor.getColumnIndex(KEY_IS_DONE);
                int createdAtIndex = cursor.getColumnIndex(KEY_CREATED_AT);
                int updatedAtIndex = cursor.getColumnIndex(KEY_UPDATED_AT);

                taskModel.setId(Integer.parseInt(cursor.getString(0)));
                taskModel.setName(cursor.getString(nameIndex));
                Boolean isDone = cursor.getInt(isDoneIndex) == 1;
                taskModel.setDone(isDone);
                taskModel.setCreatedAt(dateFromStringFormatted(cursor.getString(createdAtIndex)));
                taskModel.setUpdatedAt(dateFromStringFormatted(cursor.getString(updatedAtIndex)));

                taskModels.add(taskModel);
            } while (cursor.moveToNext());
        }
        return taskModels;
    }

    /**
     *
     */
    public int updateTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_IS_DONE, task.isDone());
        values.put(KEY_UPDATED_AT, getDateTime(task.getUpdatedAt()));

        return db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    /**
     *
     */
    public void deleteTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    private Date dateFromStringFormatted(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
