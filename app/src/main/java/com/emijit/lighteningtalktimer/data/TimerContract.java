package com.emijit.lighteningtalktimer.data;

import android.provider.BaseColumns;

/**
 * Created by aaron on 10/9/16.
 */

public class TimerContract {

    public static final class TimerEntry implements BaseColumns {

        public static final String TABLE_NAME = "timer";

        public static final String COLUMN_ADD_TIMER = "add_timer";
        public static final String COLUMN_SET_INTERVAL = "set_interval";
    }
}
