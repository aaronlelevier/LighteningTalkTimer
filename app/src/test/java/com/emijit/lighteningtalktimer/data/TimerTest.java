package com.emijit.lighteningtalktimer.data;

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
                timer.getTimerSeconds() != null);
        assertEquals("Error: default timer array doesn't match",
                Arrays.asList(0, 0, 0, 0, 0, 0), timer.getTimerSeconds());

        // intervalSeconds
        assertTrue("Error: Timer should have a 'interval seconds' array to hold 6 digits for 00h00m00s",
                timer.getIntervalSeconds() != null);
        assertEquals("Error: default interval array doesn't match",
                Arrays.asList(0, 0, 0, 0, 0, 0), timer.getIntervalSeconds());
    }

    @Test
    public void secondsLinkedList() throws Exception {
        Timer timer = new Timer();
        Timer.SecondsLinkedList seconds = timer.getTimerSeconds();
        int one = 1;
        int two = 2;
        assertEquals(0, seconds.getSlotsInUse());

        // add
        seconds.addTimerItem(one);
        assertEquals("Error: 'add' should append to end, and shift by one",
                Arrays.asList(0, 0, 0, 0, 0, 1), seconds);
        assertEquals(1, seconds.getSlotsInUse());

        seconds.addTimerItem(two);
        assertEquals("Error: 'add' should append to end, and shift by one (again)",
                Arrays.asList(0, 0, 0, 0, 1, 2), seconds);
        assertEquals(2, seconds.getSlotsInUse());

        seconds.setSlotsInUse(6);
        seconds.addTimerItem(one);
        assertEquals("Error: if all slots are in use, then 'add' doesn't modify 'timer' array",
                Arrays.asList(0, 0, 0, 0, 1, 2), seconds);

        // remove
        seconds.removeTimerItem();
        assertEquals("Error: should use LIFO and remove last item",
                Arrays.asList(0, 0, 0, 0, 0, 1), seconds);

        seconds.removeTimerItem();
        assertEquals("Error: should use LIFO and remove last item (again)",
                Arrays.asList(0, 0, 0, 0, 0, 0), seconds);

        seconds.removeTimerItem();
        assertEquals("Error: if 'slots in use' is 0, then this operation has no affect",
                Arrays.asList(0, 0, 0, 0, 0, 0), seconds);

        // don't run all method tests for 'interval seconds', just confirm that it's the same type
        assertEquals("Error: 'timer seconds' and 'interval seconds' should be the same type",
                timer.getTimerSeconds().getClass(), timer.getIntervalSeconds().getClass());

    }

    @Test
    public void getAndSetSecondsStrValueOnTimer() throws Exception {
        // add timer
        Timer timer = TimerTestUtils.createTimer();
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), timer.getTimerSeconds());
        assertEquals("", timer.getTimerSecondsStrValue());
        timer.setTimerSecondsStrValue();
        assertEquals("123456", timer.getTimerSecondsStrValue());
        // set interval
        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), timer.getIntervalSeconds());
        assertEquals("", timer.getIntervalSecondsStrValue());
        timer.setIntervalSecondsStrValue();
        assertEquals("Error: this should be 6 zeros it's just the default list, unlike 'add timer'",
                "000000", timer.getIntervalSecondsStrValue());
    }

    @Test
    public void getSecondsMinutesAndHours() throws Exception {
        Timer timer = TimerTestUtils.createTimer();
        Timer.SecondsLinkedList seconds = timer.getTimerSeconds();

        assertEquals("Error: initial setup incorrect",
                Arrays.asList(1, 2, 3, 4, 5, 6), seconds);

        assertEquals("Error: getSeconds - should be able to slice and return the last two items",
                "56", seconds.getSeconds());

        assertEquals("Error: getMinutes - should be able to slice and return the middle two items",
                "34", seconds.getMinutes());

        assertEquals("Error: getHours - should be able to slice and return the first two items",
                "12", seconds.getHours());
    }

    @Test
    public void convertStrValue() throws Exception {
        Timer timer = new Timer();
        assertEquals("Error: str representation of the seconds linked list should be empty if list is empty",
                "000000", timer.getTimerSeconds().convertStrValue());

        Timer timerTwo = TimerTestUtils.createTimer();
        assertEquals("Error: should return a string representation of the linked list's contents",
                "123456", timerTwo.getTimerSeconds().convertStrValue());
    }

    @Test
    public void setAndGetStrValue() throws Exception {
        Timer timer = new Timer();
        assertEquals("Error: should be blank until set",
                "", timer.getTimerSeconds().getStrValue());
        timer.getTimerSeconds().setStrValue();
        assertEquals("Error: should be populated with the converted value after set has been called",
                "000000", timer.getTimerSeconds().getStrValue());

        Timer timerTwo = TimerTestUtils.createTimer();
        assertEquals("Error: should be blank until set",
                "", timerTwo.getTimerSeconds().getStrValue());
        timerTwo.getTimerSeconds().setStrValue();
        assertEquals("Error: should be populated with the converted value after set has been called",
                "123456", timerTwo.getTimerSeconds().getStrValue());
    }

    @Test
    public void convertStrToList() throws Exception {
        Timer timer = TimerTestUtils.createTimer();
        timer.getTimerSeconds().setStrValue();
        assertEquals("123456", timer.getTimerSeconds().getStrValue());

        timer.getTimerSeconds().removeTimerItem();
        assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5), timer.getTimerSeconds());
        assertEquals("Error: even though the list value has changed, we still have the saved strValue",
                "123456", timer.getTimerSeconds().getStrValue());
        timer.getTimerSeconds().convertStrToList();
        assertEquals("Error: after calling 'convertStrToList' it should restore the list contents to the strValue",
                Arrays.asList(1, 2, 3, 4, 5, 6), timer.getTimerSeconds());

    }

    @Test
    public void prepareToXfr() throws Exception {
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