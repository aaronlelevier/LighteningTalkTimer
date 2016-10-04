package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class AddTimerFragment extends Fragment implements View.OnClickListener {

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
        mTimer = new Timer();
        addForwardBtn();
        setupButtons();
        TimerUtils.addClearBtn(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
        ((TimerContract.AddTimerCallback) getActivity()).setToolbarTitle();
    }

    public void setupButtons() {
        // buttons
        ImageButton deleteBtn = (ImageButton) rootView.findViewById(R.id.delete_char_btn);
        deleteBtn.setOnClickListener(this);

        setAllButtonListener((ViewGroup)rootView);

        // hours/min/sec
        secondsText = (TextView) rootView.findViewById(R.id.timer_seconds);
        minutesText = (TextView) rootView.findViewById(R.id.timer_minutes);
        hoursText = (TextView) rootView.findViewById(R.id.timer_hours);

    }

    public void setAllButtonListener(ViewGroup viewGroup) {

        View v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                setAllButtonListener((ViewGroup) v);
            } else if (v instanceof Button) {
                ((Button) v).setOnClickListener(this);
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

    private void addForwardBtn() {
        ImageButton btn = (ImageButton) rootView.findViewById(R.id.forward);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TimerContract.AddTimerCallback) getActivity()).forwardToSetInterval();
            }
        });
    }
}
