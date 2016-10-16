package com.emijit.lighteningtalktimer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RunTimerActivity extends AppCompatActivity {

    private RunTimerFragment mRunTimerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_timer);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRunTimerFragment != null) {
                    mRunTimerFragment.startTimer();
                }

                Snackbar.make(view, "RunTimer started", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RunTimerFragment.URI, getIntent().getData());

            mRunTimerFragment = new RunTimerFragment();
            mRunTimerFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mRunTimerFragment)
                    .commit();
        }
    }
}
