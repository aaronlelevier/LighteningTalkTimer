package com.emijit.lighteningtalktimer.data;

import android.os.HandlerThread;

/**
 * Created by aaron on 10/14/16.
 */

public class Observers {

    public static TimerObserver getTimerObserver(Timer timer) {
        return TimerObserver.getTimerObserver(timer);
    }

    public static class TimerObserver {
        final HandlerThread mHT;
        static Timer mTimer;

        static TimerObserver getTimerObserver(Timer timer) {
            mTimer = timer;
            HandlerThread ht = new HandlerThread("Runner");
            ht.start();
            return new TimerObserver(ht);
        }

        public TimerObserver(HandlerThread ht) {
            mHT = ht;
        }

        public void start() {
            new Runner(mTimer).run();
            mHT.quit();
        }
    }
}
