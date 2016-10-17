package com.emijit.lighteningtalktimer.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class TimerContract {

    public static final class TimerEntry implements BaseColumns {

        public static final String TABLE_NAME = "timer";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ADD_TIMER = "add_timer";
        public static final String COLUMN_SET_INTERVAL = "set_interval";
        public static final String COLUMN_INTERVALS = "intervals";

        public static final String CONTENT_AUTHORITY = "com.emijit.lighteningtalktimer";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_TIMER = "timer";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TIMER).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TIMER;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/" + CONTENT_AUTHORITY + "/" + PATH_TIMER;

        public static Uri buildTimerItem(Long rowId) {
            return ContentUris.withAppendedId(CONTENT_URI, rowId);
        }
    }
}
