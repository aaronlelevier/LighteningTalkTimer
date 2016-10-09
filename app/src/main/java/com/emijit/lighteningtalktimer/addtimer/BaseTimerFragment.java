package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.emijit.lighteningtalktimer.R;
import com.emijit.lighteningtalktimer.data.Timer;

/**
 * Created by aaron on 10/8/16.
 */

public abstract class BaseTimerFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = AddTimerFragment.class.getSimpleName();

    private Timer mTimer;

    public BaseTimerFragment() {
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimer.prepareToXfr();
    }

    void setupButtons(View view) {
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_char_btn);
        deleteBtn.setOnClickListener(this);

        setAllButtonListeners((ViewGroup) view);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
    }

    Timer setTimerInstance() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTimer = bundle.getParcelable(AddTimerActivity.TIMER);
            if (mTimer != null) {
                Log.d(LOG_TAG, "mTimer exists");
                updateTimer();
            }
        } else {
            mTimer = new Timer();
            Log.d(LOG_TAG, "had to instantiate mTimer");
        }
        return mTimer;
    }

    private void setAllButtonListeners(ViewGroup viewGroup) {
        View v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                setAllButtonListeners((ViewGroup) v);
            } else if (v instanceof Button) {
                v.setOnClickListener(this);
            }
        }
    }

    /**
     * Needs to access a different Seconds object depending on the fragment
     * */
    public abstract void updateTimer();

    public static class ViewHolder {
        public final TextView secondsText;
        public final TextView minutesText;
        public final TextView hoursText;

        public ViewHolder(View view) {
            secondsText = (TextView) view.findViewById(R.id.timer_seconds);
            minutesText = (TextView) view.findViewById(R.id.timer_minutes);
            hoursText = (TextView) view.findViewById(R.id.timer_hours);
        }
    }

}
