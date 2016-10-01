package com.emijit.lighteningtalktimer.addtimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.emijit.lighteningtalktimer.R;

public class AddTimerActivity extends AppCompatActivity implements AddTimerFragment.Callback {

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
    public void setToolbarTitle(String string) {
        mToolbar.setTitle(string);
    }

}
