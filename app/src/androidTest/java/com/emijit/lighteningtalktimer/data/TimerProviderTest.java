package com.emijit.lighteningtalktimer.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TimerProviderTest {

    private Cursor cursor;
    private Context mContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getTargetContext();
        deleteAllRecords();
    }

    @After
    public void tearDown() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Test
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                TimerProvider.class.getName());

        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            assertEquals("Error: TimerProvider registered as: " + providerInfo.authority + " but should be: " + TimerEntry.CONTENT_AUTHORITY,
                    providerInfo.authority, TimerEntry.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: TimerProvider no registered at: " + mContext.getPackageName(),
                    false);
        }
    }

    @Test
    public void testGetType() {
        // List
        String type = mContext.getContentResolver().getType(TimerEntry.CONTENT_URI);
        assertEquals("Error: got content type: " + type + " but should be: " + TimerEntry.CONTENT_TYPE,
                type, TimerEntry.CONTENT_TYPE);

        // Item
        long rowId = 1;
        type = mContext.getContentResolver().getType(TimerEntry.buildTimerItem(rowId));
        assertEquals("Error: got content type: " + type + " but should be: " + TimerEntry.CONTENT_ITEM_TYPE,
                type, TimerEntry.CONTENT_ITEM_TYPE);
    }

    @Test
    public void testQuery() {
        ContentValues testValues = TimerIntegrationTestUtils.createTimerValues();

        testInsert();

        cursor = mContext.getContentResolver().query(
                TimerEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertTrue(cursor != null);
        assertEquals(1, cursor.getCount());
        cursor.moveToFirst();
        TimerIntegrationTestUtils.validateCurrentRecord(
                "Error: inserted data doesn't match testValues", cursor, testValues);
    }

    @Test
    public void testInsert() {
        ContentValues testValues = TimerIntegrationTestUtils.createTimerValues();

        TimerIntegrationTestUtils.TestContentObserver tco = TimerIntegrationTestUtils.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(TimerEntry.CONTENT_URI, true, tco);

        Uri timerUri = mContext.getContentResolver().insert(
                TimerEntry.CONTENT_URI,
                testValues
        );

        // wait for insert to complete via the observer
        tco.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(tco);

        long timerRowId = ContentUris.parseId(timerUri);
        assertTrue("Error: record not created",
                timerRowId != -1);
    }

    @Test
    public void testDelete() {
        testInsert();

        TimerIntegrationTestUtils.TestContentObserver tco = TimerIntegrationTestUtils.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(TimerEntry.CONTENT_URI, true, tco);

        deleteAllRecords();

        tco.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(tco);
    }

    public void deleteAllRecords() {
        mContext.getContentResolver().delete(TimerEntry.CONTENT_URI, null, null);
        cursor = mContext.getContentResolver().query(
                TimerEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );
        assertTrue(cursor != null);
        assertEquals(0, cursor.getCount());
    }

    @Test
    public void testGetValuesFromCursor() {
        testInsert();
        cursor = mContext.getContentResolver().query(
                TimerEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );
        assertTrue(cursor != null);
        assertEquals(1, cursor.getCount());
        cursor.moveToFirst();

        Timer timer = new Timer(cursor);

        assertEquals(Arrays.asList(0, 5, 0, 0, 0, 0), timer.getTimerSeconds());
        assertEquals("050000", timer.getTimerSecondsStrValue());
        assertEquals(Arrays.asList(0, 1, 0, 0, 0, 0), timer.getIntervalSeconds());
        assertEquals("010000", timer.getIntervalSecondsStrValue());
    }
}
