package com.emijit.lighteningtalktimer.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
