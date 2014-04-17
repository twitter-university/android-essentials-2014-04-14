package com.twitter.university.android.yamba;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaContract;


public class TimelineActivity
        extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final int TIMELINE_LOADER = 42;

    private static final String[] PROJ = new String[] {
        YambaContract.Timeline.Columns.HANDLE,
        YambaContract.Timeline.Columns.TIMESTAMP,
        YambaContract.Timeline.Columns.TWEET
    };

    private static final int[] VIEWS = new int[] {
        R.id.timeline_handle,
        R.id.timeline_timestamp,
        R.id.timeline_tweet
    };

    static class TimelineBinder implements SimpleCursorAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View v, Cursor c, int col) {
            if (R.id.timeline_timestamp != v.getId()) { return false; }

            CharSequence s = "long ago";
            long t = c.getLong(col);
            if (0 < t) { s = DateUtils.getRelativeTimeSpanString(t); }
            ((TextView) v).setText(s);
            return true;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
            this,
            YambaContract.Timeline.URI,
            null,
            null,
            null,
            YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor c) {
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(c);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            this,
            R.layout.timeline_row,
            null,
            PROJ,
            VIEWS,
            0);
        adapter.setViewBinder(new TimelineBinder());
        setListAdapter(adapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);
    }
}
