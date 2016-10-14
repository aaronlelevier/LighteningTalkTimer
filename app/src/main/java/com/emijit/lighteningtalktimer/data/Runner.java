package com.emijit.lighteningtalktimer.data;

import android.util.Log;

/**
 * Created by aaron on 10/14/16.
 */


public class Runner implements Runnable {

    private static final String LOG_TAG = Runner.class.getSimpleName();

    private Timer mTimer;
    private int mCurrentIntervals = 0;

    public Runner(Timer timer) {
        mTimer = timer;
    }

    @Override
    public void run() {
        while (mTimer.getIntervals() > mCurrentIntervals) {
            try {
                Thread.sleep(mTimer.getIntervalSeconds().getRawSeconds());
            } catch (InterruptedException e) {
                Log.d(LOG_TAG, e.toString());
            }
            mCurrentIntervals++;
            mTimer.setCurrentIntervalCount(mCurrentIntervals);
        }
    }
}
