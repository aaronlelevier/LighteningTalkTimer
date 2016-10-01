package com.emijit.lighteningtalktimer.data;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TimerTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void initTimer() throws Exception {
        Timer timer = new Timer();

        // id
        assertTrue("Error: Timer should have a random id",
                timer.getId() != null);

        // timerSeconds
        assertTrue("Error: Timer should have a 'timer seconds' array to hold 6 digits for 00h00m00s",
                timer.getTimerSeconds() instanceof List);
        assertEquals("Error: length should be 6 of 'timer seconds' list",
                timer.getTimerSeconds().size(), 6);

        // intervalSeconds
        assertTrue("Error: Timer should have a 'interval seconds' array to hold 6 digits for 00h00m00s",
                timer.getIntervalSeconds() instanceof List);
        assertEquals("Error: length should be 6 of 'interval seconds' list",
                timer.getIntervalSeconds().size(), 6);
    }
}