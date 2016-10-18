package com.emijit.lighteningtalktimer.data;

import java.util.Arrays;
import java.util.LinkedList;


public class Seconds extends LinkedList<Integer> {

    private static final int SECONDS_DEFAULT = 0;
    private static final int MAX_SLOTS = 6;
    private static final int INITIAL_SLOTS = 0;

    private int mSlotsInUse = INITIAL_SLOTS;
    private String mStrValue = "";
    private int mRawSeconds = 0;

    public Seconds() {
        super(Arrays.asList(0, 0, 0, 0, 0, 0));
    }

    public Seconds(String s) {
        mStrValue = s;

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

    public int getRawSeconds() {
        return mRawSeconds;
    }

    public void setRawSeconds() {
        int rawSeconds = 0;

        rawSeconds += Integer.parseInt(getSeconds());
        rawSeconds += Integer.parseInt(getMinutes()) * 60;
        rawSeconds += Integer.parseInt(getHours()) * 3600;

        mRawSeconds = rawSeconds;
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

    public String getFormattedTime() {
        StringBuilder sb = new StringBuilder();
        boolean hasTime = false;
        TimeUnits timeUnits = new TimeUnits(TimeUnitsEnum.DEFAULT);

        if (!getHours().equals("00")) {
            timeUnits = new TimeUnits(TimeUnitsEnum.HOUR);
            hasTime = true;
            sb.append(getHours());
            sb.append(":");
        }
        if (hasTime || !getMinutes().equals("00")) {
            if (!hasTime)
                timeUnits = new TimeUnits(TimeUnitsEnum.MIN);
            hasTime = true;
            sb.append(getMinutes());
            sb.append(":");
        }
        if (hasTime || !getSeconds().equals("00")) {
            if (!hasTime)
                timeUnits = new TimeUnits(TimeUnitsEnum.SEC);
            sb.append(getSeconds());
        }

        // time units should be at the end of the string value
        sb.append(timeUnits.getUnits());

        return sb.toString();
    }

    private enum TimeUnitsEnum {
        HOUR, MIN, SEC, DEFAULT
    }

    private class TimeUnits {

        TimeUnitsEnum units;

        private TimeUnits(TimeUnitsEnum units) {
            this.units = units;
        }

        private String getUnits() {
            switch (units) {
                case HOUR:
                    return " hour";
                case MIN:
                    return " min";
                case SEC:
                    return " sec";
                default:
                    return "";
            }
        }
    }
}