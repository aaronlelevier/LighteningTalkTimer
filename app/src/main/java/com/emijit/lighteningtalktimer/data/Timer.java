package com.emijit.lighteningtalktimer.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Objects;
import com.google.common.primitives.Chars;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Timer implements Parcelable {

    private final String mId;
    private SecondsLinkedList mTimerSeconds;
    private SecondsLinkedList mIntervalSeconds;
    private String mTimerSecondsStrValue = "";
    private String mIntervalSecondsStrValue = "";

    public Timer() {
        mId = UUID.randomUUID().toString();
        mTimerSeconds = new SecondsLinkedList();
        mIntervalSeconds = new SecondsLinkedList();
    }

    public String getId() {
        return mId;
    }

    public SecondsLinkedList getTimerSeconds() {
        return mTimerSeconds;
    }

    public SecondsLinkedList getIntervalSeconds() {
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

    // Parcelable methods: end

    public class SecondsLinkedList extends LinkedList<Integer> {

        private static final int SECONDS_DEFAULT = 0;
        private static final int MAX_SLOTS = 6;
        private static final int INITIAL_SLOTS = 0;

        private int mSlotsInUse = INITIAL_SLOTS;
        private String mStrValue = "";

        public SecondsLinkedList() {
            super(Arrays.asList(0, 0, 0, 0, 0, 0));
        }

        public int getSlotsInUse() {
            return mSlotsInUse;
        }

        public void setSlotsInUse(int mSlotsInUse) {
            this.mSlotsInUse = mSlotsInUse;
        }
//
        public void addTimerItem(int i) {
            if (mSlotsInUse < MAX_SLOTS) {
                addLast(i);
                remove();
                mSlotsInUse++;
            }
        }

        public void removeTimerItem() {
            if (mSlotsInUse > INITIAL_SLOTS) {
                mTimerSeconds.removeLast();
                mTimerSeconds.addFirst(SECONDS_DEFAULT);
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

        public void convertStrToList() {
            for (int i=0; i < MAX_SLOTS; i++) {
                removeTimerItem();
            }
            List<Character> characterList = Chars.asList(mStrValue.toCharArray());
            for (char c:characterList) {
                String s = String.valueOf(c);
                addTimerItem(Integer.parseInt(s));
            }
        }
    }
}