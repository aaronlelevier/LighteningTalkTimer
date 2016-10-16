package com.emijit.lighteningtalktimer;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emijit.lighteningtalktimer.data.Timer;
import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;


public class RunTimerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = RunTimerFragment.class.getSimpleName();

    private View rootView;
    private Uri mUri;
    private long mTimerId = 0;
    private Timer mTimer;

    private static final int DETAIL_LOADER = 0;

    public static String URI = "URI";

    public RunTimerFragment() {
    }

    static class ViewHolder {
        TextView intervals;
        TextView timerSeconds;
        TextView intervalSeconds;

        public ViewHolder(View view) {
            intervals = (TextView) view.findViewById(R.id.run_timer_intervals);
            timerSeconds = (TextView) view.findViewById(R.id.run_timer_timer_seconds);
            intervalSeconds = (TextView) view.findViewById(R.id.run_timer_interval_seconds);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_run_timer, container, false);

        ViewHolder viewHolder = new ViewHolder(rootView);
        rootView.setTag(viewHolder);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(URI);
            if (mUri != null) {
                mTimerId = ContentUris.parseId(mUri);
            }
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mTimerId != 0) {
            return new CursorLoader(
                    getActivity(),
                    TimerEntry.CONTENT_URI,
                    null,
                    TimerEntry._ID + " = ?",
                    new String[] { Long.toString(mTimerId) },
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            mTimer = new Timer(cursor);

            Log.d(LOG_TAG, "timer seconds: + " + mTimer.getTimerSeconds().getRawSeconds() +
                           " interval seconds: " + mTimer.getIntervalSeconds().getRawSeconds() +
                           " intervals: " + mTimer.getIntervals());

            ViewHolder viewHolder = (ViewHolder) rootView.getTag();
            viewHolder.timerSeconds.setText(mTimer.getTimerSecondsStrValue());
            viewHolder.intervalSeconds.setText(mTimer.getIntervalSecondsStrValue());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void startTimer() {
        new RunTimerTask().execute(mTimer);
    }

    private class RunTimerTask extends AsyncTask<Timer, Integer, Long> {

        private final String LOG_TAG = RunTimerTask.class.getSimpleName();

        @Override
        protected Long doInBackground(Timer... params) {
            int mCurrentIntervals = 0;
            Timer timer = params[0];

            while (timer.getIntervals() > mCurrentIntervals) {
                try {
                    Thread.sleep(timer.getIntervalSeconds().getRawSeconds() * 1000);
                } catch (InterruptedException e) {
                    Log.d(LOG_TAG, e.toString());
                }
                mCurrentIntervals++;
                publishProgress(mCurrentIntervals);
            }
            return (long) mCurrentIntervals;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            String interval = Integer.toString(values[0]);
            Log.d(LOG_TAG, "onProgressUpdate: " + interval);

            // update view w/ current interval
            ViewHolder viewHolder = (ViewHolder) rootView.getTag();
            viewHolder.intervals.setText(interval);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.d(LOG_TAG, "onPostExecute");
        }
    }
}
