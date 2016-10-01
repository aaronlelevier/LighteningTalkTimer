package com.emijit.lighteningtalktimer.addtimer;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.emijit.lighteningtalktimer.MainActivity;
import com.emijit.lighteningtalktimer.R;

/**
 * Created by aaron on 10/1/16.
 */

public class TimerUtils {

    public static void addClearBtn(final Fragment frag, View view) {
        ImageButton btn = (ImageButton) view.findViewById(R.id.clear);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag.startActivity(new Intent(frag.getActivity(), MainActivity.class));
            }
        });
    }
}
