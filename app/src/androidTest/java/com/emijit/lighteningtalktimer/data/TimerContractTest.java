package com.emijit.lighteningtalktimer.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;

import java.util.HashSet;

public class TimerContractTest extends AndroidTestCase {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (db != null) {
            db.close();
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public void testCreateDb() {
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(TimerContract.TimerEntry.TABLE_NAME);

        mContext.deleteDatabase(TimerDbHelper.DATABASE_NAME);
        db = new TimerDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: db not created",
                cursor.moveToNext());

        do {
            tableNameHashSet.remove(cursor.getString(0));
        } while ( cursor.moveToNext());
        assertTrue("Error: tableNameHashSet not empty",
                tableNameHashSet.isEmpty());

        cursor = db.rawQuery("PRAGMA table_info(" + TimerEntry.TABLE_NAME + ")", null);
        assertTrue("Error: TodoEntry.TABLE_NAME does not exist in DB",
                cursor.moveToFirst());

        // test columns exit in 'to-do' table
        final HashSet<String> timerColumnHashSet = new HashSet<>();
        timerColumnHashSet.add(TimerEntry._ID);
        timerColumnHashSet.add(TimerEntry.COLUMN_ADD_TIMER);
        timerColumnHashSet.add(TimerEntry.COLUMN_SET_INTERVAL);

        int columnDescIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnDescIndex);
            timerColumnHashSet.remove(columnName);
        } while ( cursor.moveToNext());

        assertTrue("Error: Column names w/i to-do table not properly configured",
                timerColumnHashSet.isEmpty());
    }

    public void testCreateTimerTable() {
        db = new TimerDbHelper(mContext).getWritableDatabase();

        ContentValues testValues = TimerIntegrationTestUtils.createTimerValues();

        long timerRowId;
        timerRowId = db.insert(TimerEntry.TABLE_NAME, null, testValues);
        assertTrue("Error: no values inserted",
                timerRowId != -1);

        // Confirm 1 record exists
        cursor = db.query(
                TimerEntry.TABLE_NAME,
                null, // all columns
                null, // where clause
                null, // values to filter 'where' clause by
                null, // group by
                null, // columns to filter row groups
                null  // order by
        );
        assertTrue("Error: No records",
                cursor.moveToFirst());

        TimerIntegrationTestUtils.validateCurrentRecord("Error: records don't match",
                cursor, testValues);

        // no more records exist
        assertFalse("Error: only one record should exist",
                cursor.moveToNext());
    }
}
