package com.emijit.lighteningtalktimer.data;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;


public class TimerProviderTest extends AndroidTestCase {

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

    public void testGetType() {
        // List
        String type = mContext.getContentResolver().getType(TimerEntry.CONTENT_URI);
        assertEquals("Error: got content type: " + type + " but should be: " + TimerEntry.CONTENT_TYPE,
                type, TimerEntry.CONTENT_TYPE);

        // Item
        String testTimer = "practice coding";
        type = mContext.getContentResolver().getType(TimerEntry.buildTimerItem(testTimer));
        assertEquals("Error: got content type: " + type + " but should be: " + TimerEntry.CONTENT_ITEM_TYPE,
                type, TimerEntry.CONTENT_ITEM_TYPE);
    }
}
