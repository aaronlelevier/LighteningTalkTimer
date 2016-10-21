package com.emijit.lighteningtalktimer;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emijit.lighteningtalktimer.data.Timer;

public class TimerAdapter extends CursorRecyclerViewAdapter<TimerAdapter.ViewHolder> {

    private final static String LOG_TAG = TimerAdapter.class.getSimpleName();

    private TimersFragment.TimerItemListener mItemListener;

    public TimerAdapter(Context context, Cursor cursor, TimersFragment.TimerItemListener listener) {
        super(context, cursor);
        mItemListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView numberOfIntervalsText;
        final TextView timerText;
        final TextView intervalText;

        private TimersFragment.TimerItemListener mItemListener;

        ViewHolder(View view, TimersFragment.TimerItemListener listener) {
            super(view);
            mItemListener = listener;
            view.setOnClickListener(this);
            numberOfIntervalsText = (TextView) view.findViewById(R.id.list_number_of_intervals_text);
            timerText = (TextView) view.findViewById(R.id.list_timer_text);
            intervalText = (TextView) view.findViewById(R.id.list_interval_text);
        }

        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "ViewHolder.onClick");
            int position = getAdapterPosition();
            mItemListener.onTimerClicked(position);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_timer_item, parent, false);
        ViewHolder vh = new ViewHolder(view, mItemListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Timer timer = new Timer(cursor);
        viewHolder.numberOfIntervalsText.setText(timer.getIntervalsStr());
        viewHolder.timerText.setText(timer.getTimerSeconds().getFormattedTime());
        viewHolder.intervalText.setText(timer.getIntervalSeconds().getFormattedTime());
    }
}
