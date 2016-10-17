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

    TimerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    static class ViewHolder {
        final TextView idText;
        final TextView timerText;
        final TextView intervalText;

        ViewHolder(View view) {
            idText = (TextView) view.findViewById(R.id.list_timer_id);
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
        viewHolder.idText.setText(cursor.getString(cursor.getColumnIndex(TimerEntry.COLUMN_ID)));
        viewHolder.timerText.setText(cursor.getString(cursor.getColumnIndex(TimerEntry.COLUMN_ADD_TIMER)));
        viewHolder.intervalText.setText(cursor.getString(cursor.getColumnIndex(TimerEntry.COLUMN_SET_INTERVAL)));
    }
}
