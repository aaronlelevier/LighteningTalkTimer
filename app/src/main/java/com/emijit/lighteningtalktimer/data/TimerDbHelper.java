package com.emijit.lighteningtalktimer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;


public class TimerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "timer.db";

    public TimerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TIMER_TABLE = "CREATE TABLE " + TimerEntry.TABLE_NAME + " (" +
                TimerEntry._ID + " INTEGER PRIMARY KEY, " +
                TimerEntry.COLUMN_ADD_TIMER + " TEXT NOT NULL, " +
                TimerEntry.COLUMN_SET_INTERVAL + " TEXT NOT NULL, " +
                TimerEntry.COLUMN_INTERVALS + " INTEGER NULL" +
                " );";
        db.execSQL(SQL_CREATE_TIMER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TimerEntry.TABLE_NAME);
        onCreate(db);
    }
}
