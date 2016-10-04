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
        Button oneBtn = (Button) rootView.findViewById(R.id.one);
        oneBtn.setOnClickListener(this);

        // hours/min/sec
        secondsText = (TextView) rootView.findViewById(R.id.timer_seconds);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                mTimer.getTimerSeconds().addTimerItem(1);
                break;
            case R.id.delete_char_btn:
                mTimer.getTimerSeconds().removeTimerItem();
            default:
                break;
        }
        secondsText.setText(mTimer.getTimerSeconds().getSeconds());

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
