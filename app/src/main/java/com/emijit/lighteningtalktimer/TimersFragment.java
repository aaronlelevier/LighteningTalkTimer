package com.emijit.lighteningtalktimer;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;
import com.emijit.lighteningtalktimer.helper.SimpleItemTouchHelperCallback;

/**
 * A placeholder fragment containing a simple view.
 */
public class TimersFragment extends Fragment {

    RecyclerView mRecyclerView;
    Cursor mCursor;
    TimerAdapter mTimerAdapter;
    ItemTouchHelper mItemTouchHelper;

    public TimersFragment() {
    }

    public interface Callback {

        void onItemSelected(Uri uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCursor = getCursor();

        mTimerAdapter = new TimerAdapter(getActivity(), mCursor, mItemListener);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listview_timers);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mTimerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mTimerAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private Cursor getCursor() {
        return getActivity().getContentResolver().query(
                TimerEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );
    }

    public interface TimerItemListener {

        void onTimerClicked(int position);
    }

    TimerItemListener mItemListener = new TimerItemListener() {
        @Override
        public void onTimerClicked(int position) {
            if (mCursor != null) {
                mCursor.moveToPosition(position);
                long rowId = mCursor.getLong(mCursor.getColumnIndex(TimerEntry.COLUMN_ID));

                ((Callback) getActivity())
                        .onItemSelected(TimerEntry.buildTimerItem(rowId));
            }
        }
    };
}
