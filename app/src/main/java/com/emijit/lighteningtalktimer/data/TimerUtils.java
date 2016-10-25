package com.emijit.lighteningtalktimer.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 10/25/16.
 */

public class TimerUtils {

    public static List<Timer> createTimerList(Cursor cursor) {
        List<Timer> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new Timer(cursor));
        }
        return list;
    }
}
