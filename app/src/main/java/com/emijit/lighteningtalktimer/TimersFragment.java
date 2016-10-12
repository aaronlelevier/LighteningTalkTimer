package com.emijit.lighteningtalktimer;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class TimersFragment extends Fragment {

    public TimersFragment() {
    }

    public interface Callback {

        void onItemSelected(Uri uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Cursor cursor = getActivity().getContentResolver().query(
                TimerEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );
        final TimerAdapter adapter = new TimerAdapter(getActivity(), cursor, 0);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_timers);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    ((Callback) getActivity())
                            .onItemSelected(TimerEntry.buildTimerItem(rowId));
                }
            }
        });

        return rootView;
    }
}
