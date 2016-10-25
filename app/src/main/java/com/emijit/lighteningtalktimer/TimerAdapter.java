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
import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;
import com.emijit.lighteningtalktimer.helper.ItemTouchHelperAdapter;
import com.emijit.lighteningtalktimer.helper.ItemTouchHelperViewHolder;

public class TimerAdapter extends CursorRecyclerViewAdapter<TimerAdapter.ViewHolder>
    implements ItemTouchHelperAdapter{

    private final static String LOG_TAG = TimerAdapter.class.getSimpleName();

    private Context mContext;
    private Cursor mCursor;
    private TimersFragment.TimerItemListener mItemListener;

    public TimerAdapter(Context context, Cursor cursor, TimersFragment.TimerItemListener listener) {
        super(context, cursor);
        mContext = context;
        mCursor = cursor;
        mItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_timer_item, parent, false);
        ViewHolder vh = new ViewHolder(view, mItemListener, mNotifyChange);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Timer timer = new Timer(cursor);
        viewHolder.numberOfIntervalsText.setText(timer.getIntervalsStr());
        viewHolder.timerText.setText(timer.getTimerSeconds().getFormattedTime());
        viewHolder.intervalText.setText(timer.getIntervalSeconds().getFormattedTime());
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {
        long rowId = mCursor.getLong(mCursor.getColumnIndex(TimerEntry.COLUMN_ID));
        mContext.getContentResolver().delete(TimerEntry.buildTimerItem(rowId), null, null);
        this.swapCursor(getCursor());
        notifyItemChanged(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, ItemTouchHelperViewHolder {
        final TextView numberOfIntervalsText;
        final TextView timerText;
        final TextView intervalText;

        private TimersFragment.TimerItemListener mItemListener;
        private NotifyChange mNotifyChange;

        ViewHolder(View view, TimersFragment.TimerItemListener listener, NotifyChange notifyChange) {
            super(view);
            mItemListener = listener;
            mNotifyChange = notifyChange;
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
            // if User is quick enough, they could click a view 2x, so that's why this "if" check is here
            if (position != RecyclerView.NO_POSITION) {
                mNotifyChange.remove(position);
            }
        }

        @Override
        public void onItemSelected() {
            Log.d(LOG_TAG, "ViewHolder.onItemSelected");
        }

        @Override
        public void onItemClear() {
            Log.d(LOG_TAG, "ViewHolder.onItemClear");
        }
    }

    public interface NotifyChange {
        void remove(int position);
    }
    NotifyChange mNotifyChange = new NotifyChange() {
        @Override
        public void remove(int position) {
            notifyItemRemoved(position);
        }
    };
}
