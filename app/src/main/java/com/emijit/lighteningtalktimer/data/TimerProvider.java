package com.emijit.lighteningtalktimer.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.emijit.lighteningtalktimer.data.TimerContract.TimerEntry;


public class TimerProvider extends ContentProvider {

    private static final int TIMER = 100;
    private static final int TIMER_ITEM = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private TimerDbHelper mTimerDbHelper;

    static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = TimerEntry.CONTENT_AUTHORITY;

        matcher.addURI(authority, TimerEntry.PATH_TIMER, TIMER);
        matcher.addURI(authority, TimerEntry.PATH_TIMER + "/*", TIMER_ITEM);

        return  matcher;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TIMER:
                return TimerEntry.CONTENT_TYPE;
            case TIMER_ITEM:
                return TimerEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public boolean onCreate() {
        mTimerDbHelper = new TimerDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case TIMER:
                retCursor = mTimerDbHelper.getReadableDatabase().query(
                        TimerEntry.TABLE_NAME,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                return null;
        }
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri retUri;

        switch (sUriMatcher.match(uri)) {
            case TIMER:
                long rowId = mTimerDbHelper.getWritableDatabase().insert(TimerEntry.TABLE_NAME, null, values);
                if (rowId > 0) {
                    retUri = TimerEntry.buildTimerItem(rowId);
                } else {
                    throw new SQLException("Failed to insert row: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted;
        // will delete all rows if "selection" is null
        if (selection == null) {
            selection = "1";
        }

        switch (sUriMatcher.match(uri)) {
            case TIMER:
                rowsDeleted = mTimerDbHelper.getWritableDatabase().delete(
                        TimerEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            case TIMER_ITEM:
                rowsDeleted = mTimerDbHelper.getWritableDatabase().delete(
                        TimerEntry.TABLE_NAME,
                        TimerEntry.COLUMN_ID + " = ?",
                        new String[] { Long.toString(ContentUris.parseId(uri)) }
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    /**
     * Not supported - it's either create or delete for new records. It's too simple to create,
     * so no reason to expose an update interface.
     * */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
