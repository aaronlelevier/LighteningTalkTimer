package com.emijit.lighteningtalktimer.data;

/**
 * Created by aaron on 10/6/16.
 */

public class TimerTestUtils {

    public static Timer createTimer() {
        Timer timer = new Timer();
        SecondsLinkedList seconds = timer.getTimerSeconds();
        seconds.addTimerItem(1);
        seconds.addTimerItem(2);
        seconds.addTimerItem(3);
        seconds.addTimerItem(4);
        seconds.addTimerItem(5);
        seconds.addTimerItem(6);
        return timer;
    }

    public static SecondsLinkedList createSeconds() {
        SecondsLinkedList seconds = new SecondsLinkedList();
        seconds.addTimerItem(1);
        seconds.addTimerItem(2);
        seconds.addTimerItem(3);
        seconds.addTimerItem(4);
        seconds.addTimerItem(5);
        seconds.addTimerItem(6);
        return seconds;
    }
}
