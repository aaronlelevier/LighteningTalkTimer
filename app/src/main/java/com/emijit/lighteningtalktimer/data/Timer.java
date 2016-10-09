package com.emijit.lighteningtalktimer.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Objects;

import java.util.UUID;

public class Timer implements Parcelable {

    private final String mId;
    private Seconds mTimerSeconds;
    private Seconds mIntervalSeconds;
    private String mTimerSecondsStrValue = "";
    private String mIntervalSecondsStrValue = "";

    public Timer() {
        mId = UUID.randomUUID().toString();
        mTimerSeconds = new Seconds();
        mIntervalSeconds = new Seconds();
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

    /*
    * Sets lists to primitive String values on the Timer. To be called before
    * sending a Timer instance through a Fragment Bundle.
    * */
    public void prepareToXfr() {
        setTimerSecondsStrValue();
        setIntervalSecondsStrValue();
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