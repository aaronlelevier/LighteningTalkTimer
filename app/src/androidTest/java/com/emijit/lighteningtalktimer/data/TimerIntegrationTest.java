package com.emijit.lighteningtalktimer.data;

import android.os.Parcel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by aaron on 10/6/16.
 */

public class TimerIntegrationTest {

    @Test
    public void timerIsParcelable() throws Exception {
        Timer timer = new Timer();
        Timer.SecondsLinkedList seconds = timer.getTimerSeconds();
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
    }
}
