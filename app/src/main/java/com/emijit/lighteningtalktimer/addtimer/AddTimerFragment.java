package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.emijit.lighteningtalktimer.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTimerFragment extends Fragment {

    private static final String LOG_TAG = AddTimerFragment.class.getSimpleName();

    private View rootView;

    public AddTimerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_timer, container, false);
        addForwardBtn();
        TimerUtils.addClearBtn(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
        ((TimerContract.AddTimerCallback) getActivity()).setToolbarTitle();
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
