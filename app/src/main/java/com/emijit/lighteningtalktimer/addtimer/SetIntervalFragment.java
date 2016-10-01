package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emijit.lighteningtalktimer.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SetIntervalFragment extends Fragment {

    private View rootView;

    public SetIntervalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_set_interval, container, false);
        TimerUtils.addClearBtn(this, rootView);
        return rootView;
    }

}
