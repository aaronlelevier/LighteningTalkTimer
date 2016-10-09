package com.emijit.lighteningtalktimer.addtimer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

    private TextView secondsText;
    private TextView minutesText;
    private TextView hoursText;

    public BaseTimerFragment() {
    }

    void setupButtons(View view) {
        // buttons
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_char_btn);
        deleteBtn.setOnClickListener(this);

        setAllButtonListeners((ViewGroup) view);

        // hours/min/sec
        secondsText = (TextView) view.findViewById(R.id.timer_seconds);
        minutesText = (TextView) view.findViewById(R.id.timer_minutes);
        hoursText = (TextView) view.findViewById(R.id.timer_hours);
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

    @Override
    public void onClick(View v) {
        Button b;
        switch (v.getId()) {
            case R.id.delete_char_btn:
                mTimer.getTimerSeconds().removeTimerItem();
                break;
            default:
                b = (Button) v;
                int i = Integer.parseInt(b.getText().toString());
                mTimer.getTimerSeconds().addTimerItem(i);
                break;
        }
        updateTimer();
    }

    private void updateTimer() {
        secondsText.setText(mTimer.getTimerSeconds().getSeconds());
        minutesText.setText(mTimer.getTimerSeconds().getMinutes());
        hoursText.setText(mTimer.getTimerSeconds().getHours());
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimer.prepareToXfr();
    }
}
