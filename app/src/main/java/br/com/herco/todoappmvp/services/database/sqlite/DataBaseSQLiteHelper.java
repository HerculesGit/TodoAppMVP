package br.com.herco.todoappmvp.services.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Observable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.List;

import br.com.herco.todoappmvp.models.TaskModel;
import br.com.herco.todoappmvp.services.synchronize.ISynchronizedDatabase;

public class DataBaseSQLiteHelper extends SQLiteOpenHelper {

    /* Inner class that defines the table contents */
    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";

        public static final String _ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_IS_DONE = "isDone";
        //public static final String COLUMN_NAME_SYNCHRONIZED = "synchronized";
        public static final String COLUMN_NAME_CREATED_AT = "createdAt";
        public static final String COLUMN_NAME_UPDATED_AT = "updatedAt";
        public static final String COLUMN_NAME_DELETED_AT = "deletedAt";
        public static final String COLUMN_NAME_USER_ID = "userId";
    }

    /* Inner class that defines the table contents */
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";

        public static final String _ID = "id";
        public static final String COLUMN_NAME_NAME = "userName";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_CREATED_AT = "createdAt";
        public static final String COLUMN_NAME_UPDATED_AT = "updatedAt";
        public static final String COLUMN_NAME_DELETED_AT = "deletedAt";
    }

    /* Inner class that defines the table contents */
    public static class SynchronizedDataTask implements BaseColumns {
        public static final String TABLE_NAME = "SynchronizedTaskData";

        public static final String _ID = "id";
        public static final String COLUMN_NAME_USER_ID = "userId";
        public static final String COLUMN_NAME_LAST_SYNCHRONIZED_DATE_TASK = "lastSynchronizedDateTask";
    }

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "todo_app.db";

    private static final String SQL_CREATE_ENTRIES_TASK =
            "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +
                    TaskEntry._ID + " VARCHAR(255) NOT NULL PRIMARY KEY," +
                    TaskEntry.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                    TaskEntry.COLUMN_NAME_IS_DONE + " INTEGER (0,1) NOT NULL," +
                    //TaskEntry.COLUMN_NAME_SYNCHRONIZED + " INTEGER (0,1) NOT NULL," +

                    TaskEntry.COLUMN_NAME_CREATED_AT + " text NOT NULL," +
                    TaskEntry.COLUMN_NAME_UPDATED_AT + " text NOT NULL," +
                    TaskEntry.COLUMN_NAME_DELETED_AT + " text," +
                    TaskEntry.COLUMN_NAME_USER_ID + " VARCHAR(255) NOT NULL," +
                    "FOREIGN KEY (" + TaskEntry.COLUMN_NAME_USER_ID + ") REFERENCES user(" + UserEntry._ID + "))";

    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " VARCHAR(255) NOT NULL PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                    UserEntry.COLUMN_NAME_EMAIL + " INTEGER (0,1) NOT NULL," +

                    UserEntry.COLUMN_NAME_CREATED_AT + " text NOT NULL," +
                    UserEntry.COLUMN_NAME_UPDATED_AT + " text NOT NULL," +
                    UserEntry.COLUMN_NAME_DELETED_AT + " text)";

    private static final String SQL_CREATE_ENTRIES_SYNCHRONIZED_DATA_TASK =
            "CREATE TABLE " + SynchronizedDataTask.TABLE_NAME + " (" +
                    SynchronizedDataTask._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SynchronizedDataTask.COLUMN_NAME_USER_ID + " VARCHAR(255) NOT NULL," +
                    SynchronizedDataTask.COLUMN_NAME_LAST_SYNCHRONIZED_DATE_TASK + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES_TASK =
            "DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_SYNCHRONIZED_DATA_TASK =
            "DROP TABLE IF EXISTS " + SynchronizedDataTask.TABLE_NAME;

    public DataBaseSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_TASK);
        db.execSQL(SQL_CREATE_ENTRIES_USER);
        db.execSQL(SQL_CREATE_ENTRIES_SYNCHRONIZED_DATA_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_TASK);
        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_SYNCHRONIZED_DATA_TASK);
        onCreate(db);
    }
}
