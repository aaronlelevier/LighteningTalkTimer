package com.emijit.lighteningtalktimer.data;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Timer {

    private final String mId;
    private List<String> mTimerSeconds;
    private List<String> mIntervalSeconds;

    public Timer() {
        mId = UUID.randomUUID().toString();

        String[] strings = new String[6];
        Arrays.fill(strings, "0");
        mTimerSeconds = mIntervalSeconds = Arrays.asList(strings);
    }

    public String getId() {
        return mId;
    }

    public List<String> getTimerSeconds() {
        return mTimerSeconds;
    }

    public List<String> getIntervalSeconds() {
        return mIntervalSeconds;
    }
}