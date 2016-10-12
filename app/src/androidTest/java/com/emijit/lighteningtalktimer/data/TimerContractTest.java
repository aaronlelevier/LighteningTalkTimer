package com.emijit.lighteningtalktimer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TimerContractTest {

    private SQLiteDatabase db;
    private Cursor cursor;
    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void tearDown() {
        if (db != null) {
            db.close();
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    @Test
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

    @Test
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
    }
}
