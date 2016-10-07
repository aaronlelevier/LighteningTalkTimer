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

    public SetIntervalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_set_interval, container, false);
        TimerUtils.addClearBtn(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Timer timer = bundle.getParcelable(AddTimerActivity.TIMER);
            Log.d(LOG_TAG, timer.getTimerSecondsStrValue());
        } else {
            Log.d(LOG_TAG, "no Timer instance");
        }

        return rootView;
    }

}
