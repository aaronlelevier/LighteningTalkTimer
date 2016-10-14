package com.emijit.lighteningtalktimer.data;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RunnerTest  {

    @Test
    public void incrementTimerThenExit() {
        Timer timer = new Timer();
        timer.getTimerSeconds().addTimerItem(9);
        timer.getIntervalSeconds().addTimerItem(3);
        timer.setIntervals();
        // pre-test
        assertEquals(9, timer.getTimerSeconds().getRawSeconds());
        assertEquals(3, timer.getIntervalSeconds().getRawSeconds());
        assertEquals(3, timer.getIntervals());
        assertEquals(0, timer.getCurrentIntervalCount());

        Observers.TimerObserver rto = Observers.getTimerObserver(timer);

        rto.start();

        assertEquals("Error: should have incremented up to the timer.mIntervals",
                3, timer.getCurrentIntervalCount());

    }
}
