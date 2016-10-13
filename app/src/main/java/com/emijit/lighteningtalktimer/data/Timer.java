package com.emijit.lighteningtalktimer.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;
import com.google.common.base.Objects;

import java.util.UUID;

public class Timer implements Parcelable {

    private final String mId;
    private Seconds mTimerSeconds;
    private Seconds mIntervalSeconds;
    private String mTimerSecondsStrValue = "";
    private String mIntervalSecondsStrValue = "";
    private int mIntervals = 0;

    public Timer() {
        mId = UUID.randomUUID().toString();
        mTimerSeconds = new Seconds();
        mIntervalSeconds = new Seconds();
    }

    public Timer(Cursor cursor) {
        mId = UUID.randomUUID().toString();
        mTimerSecondsStrValue = cursor.getString(cursor.getColumnIndex(TimerEntry.COLUMN_ADD_TIMER));
        mIntervalSecondsStrValue = cursor.getString(cursor.getColumnIndex(TimerEntry.COLUMN_SET_INTERVAL));
        mTimerSeconds = new Seconds(mTimerSecondsStrValue);
        mIntervalSeconds = new Seconds(mIntervalSecondsStrValue);
    }

    public String getId() {
        return mId;
    }

    public Seconds getTimerSeconds() {
        return mTimerSeconds;
    }

    public Seconds getIntervalSeconds() {
        return mIntervalSeconds;
    }

    public String getTimerSecondsStrValue() {
        return mTimerSecondsStrValue;
    }

    public void setTimerSecondsStrValue() {
        mTimerSecondsStrValue = getTimerSeconds().convertStrValue();
    }

    public String getIntervalSecondsStrValue() {
        return mIntervalSecondsStrValue;
    }

    public void setIntervalSecondsStrValue() {
        mIntervalSecondsStrValue = getIntervalSeconds().convertStrValue();
    }

    public int getIntervals() {
        return mIntervals;
    }

    public void setIntervals() {
        getTimerSeconds().setRawSeconds();
        getIntervalSeconds().setRawSeconds();
        this.mIntervals = getTimerSeconds().getRawSeconds() / getIntervalSeconds().getRawSeconds();
    }

    /*
        * Sets lists to primitive String values on the Timer. To be called before
        * sending a Timer instance through a Fragment Bundle.
        * */
    public void prepareToXfr() {
        setTimerSecondsStrValue();
        setIntervalSecondsStrValue();
    }

    public ContentValues getContentValues() {
        prepareToXfr();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TimerEntry.COLUMN_ADD_TIMER, getTimerSecondsStrValue());
        contentValues.put(TimerEntry.COLUMN_SET_INTERVAL, getIntervalSecondsStrValue());
        return contentValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timer timer = (Timer) o;
        return Objects.equal(mId, timer.getId()) &&
                Objects.equal(mTimerSecondsStrValue, timer.getTimerSecondsStrValue()) &&
                Objects.equal(mIntervalSecondsStrValue, timer.getIntervalSecondsStrValue());
    }

    // Parcelable methods: start

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mId);
        out.writeString(mTimerSecondsStrValue);
        out.writeString(mIntervalSecondsStrValue);
    }

    public static final Parcelable.Creator<Timer> CREATOR
            = new Parcelable.Creator<Timer>() {
        public Timer createFromParcel(Parcel in) {
            return new Timer(in);
        }

        public Timer[] newArray(int size) {
            return new Timer[size];
        }
    };

    private Timer(Parcel in) {
        mId = in.readString();
        mTimerSecondsStrValue = in.readString();
        mIntervalSecondsStrValue = in.readString();
    }
}