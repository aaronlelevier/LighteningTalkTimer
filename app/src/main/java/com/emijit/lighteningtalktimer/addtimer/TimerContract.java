package com.emijit.lighteningtalktimer.addtimer;

/**
 * Created by aaron on 10/1/16.
 */

public class TimerContract {

    interface AddTimerCallback {

        // go from "add timer" view to "set interval"
        void forwardToSetInterval();

        // set toolbar title when hitting back button from "set interval" back to "add timer"
        void setToolbarTitle();
    }
}
