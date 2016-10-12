package com.emijit.lighteningtalktimer.data;

import android.content.ContentValues;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TimerTest {

    @Test
    public void init() {
        Timer timer = new Timer();

        assertTrue(timer.getId() != null);
        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), timer.getTimerSeconds());
        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), timer.getIntervalSeconds());
    }

    // getAndSetSecondsStrValue

    @Test
    public void getAndSetSecondsStrValueAddTimer() {
        Timer timer = TimerTestUtils.createTimer();

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), timer.getTimerSeconds());
        assertEquals("", timer.getTimerSecondsStrValue());

        timer.setTimerSecondsStrValue();

        assertEquals("123456", timer.getTimerSecondsStrValue());
    }

    @Test
    public void getAndSetSecondsStrValueSetInterval() {
        Timer timer = TimerTestUtils.createTimer();

        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), timer.getIntervalSeconds());
        assertEquals("", timer.getIntervalSecondsStrValue());

        timer.setIntervalSecondsStrValue();

        assertEquals("000000", timer.getIntervalSecondsStrValue());
    }

    @Test
    public void prepareToXfr() {
        Timer timer = new Timer();
        // add timer seconds
        timer.getTimerSeconds().addTimerItem(1);
        timer.getTimerSeconds().addTimerItem(2);
        // set interval seconds
        timer.getIntervalSeconds().addTimerItem(3);
        timer.getIntervalSeconds().addTimerItem(4);
        // pre-test
        assertEquals(Arrays.asList(0, 0, 0, 0, 1, 2), timer.getTimerSeconds());
        assertEquals(Arrays.asList(0, 0, 0, 0, 3, 4), timer.getIntervalSeconds());
        assertEquals("", timer.getTimerSecondsStrValue());
        assertEquals("", timer.getIntervalSecondsStrValue());

        timer.prepareToXfr();

        assertEquals("000012", timer.getTimerSecondsStrValue());
        assertEquals("000034", timer.getIntervalSecondsStrValue());
    }
}