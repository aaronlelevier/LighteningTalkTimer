package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.emijit.lighteningtalktimer.R;
import com.emijit.lighteningtalktimer.data.Timer;

public class AddTimerActivity extends AppCompatActivity implements TimerContract.AddTimerCallback {

    public static final String TIMER = "TIMER";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new AddTimerFragment())
                .commit();
    }

    @Override
    public void forwardToSetInterval(Timer timer) {
        mToolbar.setTitle(getString(R.string.title_set_timer_interval));

        Bundle args = new Bundle();
        args.putParcelable(TIMER, timer);
        SetIntervalFragment fragment = new SetIntervalFragment();
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setToolbarTitle() {
        mToolbar.setTitle(getString(R.string.title_activity_add_timer));
    }
}
