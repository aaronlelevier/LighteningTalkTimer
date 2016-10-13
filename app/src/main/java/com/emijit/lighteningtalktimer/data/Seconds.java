package com.emijit.lighteningtalktimer.data;

import java.util.Arrays;
import java.util.LinkedList;


public class Seconds extends LinkedList<Integer> {

    private static final int SECONDS_DEFAULT = 0;
    private static final int MAX_SLOTS = 6;
    private static final int INITIAL_SLOTS = 0;

    private int mSlotsInUse = INITIAL_SLOTS;
    private String mStrValue = "";

    public Seconds() {
        super(Arrays.asList(0, 0, 0, 0, 0, 0));
    }

    public Seconds(String s) {
        String[] arr = s.split("");
        for (String a:arr) {
            try {
                addLast(Integer.parseInt(a));
            } catch (NumberFormatException e) {
                // silently pass when leading "" is attempted to be parseInt'd
            }
        }
    }

    public int getSlotsInUse() {
        return mSlotsInUse;
    }

    public void addTimerItem(int i) {
        if (mSlotsInUse < MAX_SLOTS) {
            addLast(i);
            remove();
            mSlotsInUse++;
        }
    }

    public void removeTimerItem() {
        if (mSlotsInUse > INITIAL_SLOTS) {
            removeLast();
            addFirst(SECONDS_DEFAULT);
            mSlotsInUse--;
        }
    }

    public String getSeconds() {
        return Integer.toString(get(4)) + Integer.toString(get(5));
    }

    public String getMinutes() {
        return Integer.toString(get(2)) + Integer.toString(get(3));
    }

    public String getHours() {
        return Integer.toString(get(0)) + Integer.toString(get(1));
    }

    public String getStrValue() {
        return mStrValue;
    }

    public void setStrValue() {
        mStrValue = convertStrValue();
    }

    public String convertStrValue() {
        int sec = 0;
        int min = 0;
        int hour = 0;

        int totalSeconds = Integer.parseInt(getSeconds());
        min += totalSeconds / 60;
        sec += totalSeconds % 60;

        int totalMinutes = Integer.parseInt(getMinutes());
        hour += totalMinutes / 60;
        min += totalMinutes % 60;

        int totalHours = Integer.parseInt(getHours());
        hour += totalHours;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", hour));
        sb.append(String.format("%02d", min));
        sb.append(String.format("%02d", sec));
        return sb.toString();
    }
}