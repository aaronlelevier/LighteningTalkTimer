package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.util.Log;
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
public class AddTimerFragment extends BaseTimerFragment {

    private static final String LOG_TAG = AddTimerFragment.class.getSimpleName();

    private View rootView;
    private Timer mTimer;

    private TextView secondsText;
    private TextView minutesText;
    private TextView hoursText;

    public AddTimerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_timer, container, false);

        // unique to 'add timer' fragment, 'set interval' has a 'done' btn
        setForwardBtn();

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
        ((TimerContract.AddTimerCallback) getActivity()).setToolbarTitle();
    }

    private void setForwardBtn() {
        ImageButton btn = (ImageButton) rootView.findViewById(R.id.forward);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TimerContract.AddTimerCallback) getActivity()).forwardToSetInterval(mTimer);
            }
        });
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
            secondsText.setText(mTimer.getTimerSeconds().getSeconds());
            minutesText.setText(mTimer.getTimerSeconds().getMinutes());
            hoursText.setText(mTimer.getTimerSeconds().getHours());
        }
    }
}
