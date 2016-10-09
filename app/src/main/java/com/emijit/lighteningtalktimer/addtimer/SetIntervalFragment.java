package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.emijit.lighteningtalktimer.R;
import com.emijit.lighteningtalktimer.data.Timer;

/**
 * A placeholder fragment containing a simple view.
 */
public class SetIntervalFragment extends BaseTimerFragment {

    private static final String LOG_TAG = SetIntervalFragment.class.getSimpleName();

    private View rootView;
    private Timer mTimer;

    private TextView secondsText;
    private TextView minutesText;
    private TextView hoursText;

    public SetIntervalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_set_interval, container, false);

        // unique to 'add timer' fragment, 'set interval' has a 'done' btn
        setDoneBtn();

        // setup all buttons and hour/min/sec UI fields
        setupButtons(rootView);
        TimerUtils.addClearBtn(this, rootView);

        // this call needs to happen after button setup b/c needs UI fields
        // present, in order to update their values to that of the Timer instance
        mTimer = setTimerInstance();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateTimer();
    }

    private void setDoneBtn() {
        ImageButton btn = (ImageButton) rootView.findViewById(R.id.done);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TimerContract.AddTimerCallback) getActivity()).setDoneBtn();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Button b;
        switch (v.getId()) {
            case R.id.delete_char_btn:
                mTimer.getIntervalSeconds().removeTimerItem();
                break;
            default:
                b = (Button) v;
                int i = Integer.parseInt(b.getText().toString());
                mTimer.getIntervalSeconds().addTimerItem(i);
                break;
        }
        updateTimer();
    }

    @Override
    void setupButtons(View view) {
        super.setupButtons(view);

        // hours/min/sec
        secondsText = (TextView) view.findViewById(R.id.timer_seconds);
        minutesText = (TextView) view.findViewById(R.id.timer_minutes);
        hoursText = (TextView) view.findViewById(R.id.timer_hours);
    }

    @Override
    public void updateTimer() {
        if (mTimer != null) {
            secondsText.setText(mTimer.getIntervalSeconds().getSeconds());
            minutesText.setText(mTimer.getIntervalSeconds().getMinutes());
            hoursText.setText(mTimer.getIntervalSeconds().getHours());
        }
    }
}
