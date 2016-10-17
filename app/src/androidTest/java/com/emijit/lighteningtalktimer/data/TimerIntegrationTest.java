package com.emijit.lighteningtalktimer.data;

import android.content.ContentValues;
import android.os.Parcel;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimerIntegrationTest {

    @Test
    public void getContentValues() {
        Timer timer = new Timer();

        ContentValues contentValues = timer.getContentValues();

        assertEquals(timer.getTimerSecondsStrValue(), contentValues.getAsString(TimerEntry.COLUMN_ADD_TIMER));
        assertEquals(timer.getIntervalSecondsStrValue(), contentValues.getAsString(TimerEntry.COLUMN_SET_INTERVAL));
        assertEquals(timer.getIntervals(), (int) contentValues.getAsInteger(TimerEntry.COLUMN_INTERVALS));
    }

    @Test
    public void timerIsParcelable() throws Exception {
        Timer timer = new Timer();
        Seconds seconds = timer.getTimerSeconds();
        seconds.addTimerItem(1);
        seconds.addTimerItem(2);
        seconds.addTimerItem(3);
        seconds.addTimerItem(4);
        seconds.addTimerItem(5);
        seconds.addTimerItem(6);

        Parcel parcel = Parcel.obtain();
        timer.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Timer createdFromParcel = Timer.CREATOR.createFromParcel(parcel);
        assertEquals(timer, createdFromParcel);
        assertEquals(timer.getId(), createdFromParcel.getId());
        assertEquals(timer.getTimerSecondsStrValue(), createdFromParcel.getTimerSecondsStrValue());
        assertEquals(timer.getIntervalSecondsStrValue(), createdFromParcel.getIntervalSecondsStrValue());
    }
}
