package com.emijit.lighteningtalktimer;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;

public class TimerAdapter extends CursorAdapter {

    public TimerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public static class ViewHolder {
        public final TextView timerText;
        public final TextView intervalText;

        public ViewHolder(View view) {
            timerText = (TextView) view.findViewById(R.id.list_timer_text);
            intervalText = (TextView) view.findViewById(R.id.list_interval_text);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_timer_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.timerText.setText(cursor.getString(cursor.getColumnIndex(TimerEntry.COLUMN_ADD_TIMER)));
        viewHolder.intervalText.setText(cursor.getString(cursor.getColumnIndex(TimerEntry.COLUMN_SET_INTERVAL)));
    }
}
