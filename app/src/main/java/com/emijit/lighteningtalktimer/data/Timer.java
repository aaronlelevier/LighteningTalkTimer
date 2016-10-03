package com.emijit.lighteningtalktimer.data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

public class Timer {

    private final String mId;
    private LinkedList<Integer> mTimerSeconds;
    private LinkedList<Integer> mIntervalSeconds;
    private int mSlotsInUse = 0;

    public Timer() {
        mId = UUID.randomUUID().toString();

        mTimerSeconds = mIntervalSeconds = new LinkedList<>(Arrays.asList(0, 0, 0, 0, 0, 0));
    }

    public String getId() {
        return mId;
    }

    public LinkedList<Integer> getTimerSeconds() {
        return mTimerSeconds;
    }

    public LinkedList<Integer> getIntervalSeconds() {
        return mIntervalSeconds;
    }

    public void addTimerItem(int i) {
        if (mSlotsInUse < 6) {
            mTimerSeconds.addLast(i);
            mTimerSeconds.remove();
            mSlotsInUse++;
        }
    }

    public int getSlotsInUse() {
        return mSlotsInUse;
    }

    public void setSlotsInUse(int mSlotsInUse) {
        this.mSlotsInUse = mSlotsInUse;
    }
}