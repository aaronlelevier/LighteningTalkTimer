package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emijit.lighteningtalktimer.R;
import com.emijit.lighteningtalktimer.data.Timer;

/**
 * A placeholder fragment containing a simple view.
 */
public class SetIntervalFragment extends Fragment {

    private static final String LOG_TAG = SetIntervalFragment.class.getSimpleName();

    private View rootView;
    private Timer mTimer;

    public SetIntervalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_set_interval, container, false);
        TimerUtils.addClearBtn(this, rootView);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mTimer = bundle.getParcelable(AddTimerActivity.TIMER);
            if (mTimer != null) {
                Log.d(LOG_TAG, mTimer.getTimerSecondsStrValue());
                Log.d(LOG_TAG, mTimer.getTimerSeconds().toString());
            }
        } else {
            Log.d(LOG_TAG, "no Timer instance");
        }

        return rootView;
    }



}
