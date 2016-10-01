package com.emijit.lighteningtalktimer.addtimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.emijit.lighteningtalktimer.MainActivity;
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
        addClearBtn();
        return rootView;
    }

    private void addClearBtn() {
        ImageButton btn = (ImageButton) rootView.findViewById(R.id.clear);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
    }
}
