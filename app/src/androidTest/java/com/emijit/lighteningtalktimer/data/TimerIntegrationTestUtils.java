package com.emijit.lighteningtalktimer.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;

import java.util.Map;
import java.util.Set;

public class TimerIntegrationTestUtils extends AndroidTestCase {

    private static final String COLUMN_ADD_TIMER = "050000";
    private static final String COLUMN_SET_INTERVAL = "010000";

    static ContentValues createTimerValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(TimerEntry.COLUMN_ADD_TIMER, COLUMN_ADD_TIMER);
        testValues.put(TimerEntry.COLUMN_SET_INTERVAL, COLUMN_SET_INTERVAL);
        return testValues;
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }
}
