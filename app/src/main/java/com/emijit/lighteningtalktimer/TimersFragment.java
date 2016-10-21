package com.emijit.lighteningtalktimer;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class TimersFragment extends Fragment {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    Cursor mCursor;

    public TimersFragment() {
    }

    public interface Callback {

        void onItemSelected(Uri uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mCursor = getActivity().getContentResolver().query(
                TimerEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listview_timers);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        final TimerAdapter adapter = new TimerAdapter(getActivity(), mCursor, mItemListener);
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

    public interface TimerItemListener {

        void onTimerClicked(int position);
    }

    TimerItemListener mItemListener = new TimerItemListener() {
        @Override
        public void onTimerClicked(int position) {
            if (mCursor != null) {
                mCursor.moveToPosition(position);
                ((Callback) getActivity())
                        .onItemSelected(TimerEntry.buildTimerItem(
                                mCursor.getLong(mCursor.getColumnIndex(TimerEntry.COLUMN_ID))));
            }
        }
    };
}
