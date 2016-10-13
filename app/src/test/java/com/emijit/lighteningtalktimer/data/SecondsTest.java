package com.emijit.lighteningtalktimer.data;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class SecondsTest {

    @Test
    public void init() {
        Seconds seconds = new Seconds();

        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), seconds);
        assertEquals(0, seconds.getSlotsInUse());
        assertEquals("", seconds.getStrValue());
    }

    @Test
    public void initWithSecondsStr() {
        String secondsStr = "123456";

        Seconds seconds = new Seconds(secondsStr);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), seconds);
    }

    // addTimerItem

    @Test
    public void addTimerItem() {
        Seconds seconds = new Seconds();

        seconds.addTimerItem(1);

        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 1), seconds);
        assertEquals(1, seconds.getSlotsInUse());
    }

    @Test
    public void addTimerItemMaxSlotsUsed() {
        Seconds seconds = new Seconds();

        // 3x
        seconds.addTimerItem(1);
        seconds.addTimerItem(2);
        seconds.addTimerItem(3);

        assertEquals(Arrays.asList(0, 0, 0, 1, 2, 3), seconds);
        assertEquals(3, seconds.getSlotsInUse());

        // 6x
        seconds.addTimerItem(4);
        seconds.addTimerItem(5);
        seconds.addTimerItem(6);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), seconds);
        assertEquals(6, seconds.getSlotsInUse());

        // no affect, if 6 max slots
        seconds.addTimerItem(7);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), seconds);
        assertEquals(6, seconds.getSlotsInUse());
    }

    // removeTimerItem

    @Test
    public void removeTimerItem() {
        Seconds seconds = new Seconds();

        seconds.addTimerItem(1);

        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 1), seconds);
        assertEquals(1, seconds.getSlotsInUse());

        seconds.removeTimerItem();

        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), seconds);
        assertEquals(0, seconds.getSlotsInUse());
    }

    @Test
    public void removeTimerItemNoAffectIfSlotsInUseIsZero() {
        Seconds seconds = new Seconds();

        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), seconds);
        assertEquals(0, seconds.getSlotsInUse());

        seconds.removeTimerItem();

        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), seconds);
        assertEquals(0, seconds.getSlotsInUse());
    }

    // TimerTestUtils.createSeconds

    @Test
    public void createSeconds() {
        Seconds seconds = TimerTestUtils.createSeconds();

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), seconds);
        assertEquals(6, seconds.getSlotsInUse());
    }

    // get hour/min/sec

    @Test
    public void getHours() {
        Seconds seconds = TimerTestUtils.createSeconds();

        assertEquals("12", seconds.getHours());
    }

    @Test
    public void getMinutes() {
        Seconds seconds = TimerTestUtils.createSeconds();

        assertEquals("34", seconds.getMinutes());
    }

    @Test
    public void getSeconds() {
        Seconds seconds = TimerTestUtils.createSeconds();

        assertEquals("56", seconds.getSeconds());
    }

    // getStrValue

    @Test
    public void getStrValue() {
        Seconds seconds = TimerTestUtils.createSeconds();
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), seconds);

        assertEquals("", seconds.getStrValue());
    }

    // setStrValue

    @Test
    public void setStrValue() {
        Seconds seconds = TimerTestUtils.createSeconds();
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), seconds);

        assertEquals("", seconds.getStrValue());

        seconds.setStrValue();

        assertEquals("123456", seconds.getStrValue());
    }

    @Test
    public void setStrValueForExcessTimeValues() {
        Seconds seconds = new Seconds();
        seconds.addTimerItem(0);
        seconds.addTimerItem(0);
        seconds.addTimerItem(9);
        seconds.addTimerItem(9);
        seconds.addTimerItem(9);
        seconds.addTimerItem(9);
        assertEquals(Arrays.asList(0, 0, 9, 9, 9, 9), seconds);

        seconds.setStrValue();

        assertEquals("014039", seconds.getStrValue());
    }

    // rawSeconds

    @Test
    public void rawSeconds() {
        String secondsStr = "010500";
        Seconds seconds = new Seconds(secondsStr);
        assertEquals(Arrays.asList(0, 1, 0, 5, 0, 0), seconds);

        assertEquals(0, seconds.getRawSeconds());

        seconds.setRawSeconds();

        // 3600 sec for 1 hour + 300 sec for 1 minute
        assertEquals(3600+300, seconds.getRawSeconds());
    }
}
