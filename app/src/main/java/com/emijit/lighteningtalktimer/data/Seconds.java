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
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < size(); i++) {
            sb.append(get(i));
        }
        return sb.toString();
    }
}