package com.twitter.university.android.yamba;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import com.twitter.university.android.yamba.service.YambaContract;


public class TimelineActivity
        extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final int TIMELINE_LOADER = 42;

    private static final String[] PROJ = new String[] {
        YambaContract.Timeline.Columns.HANDLE,
    };

    private static final int[] VIEWS = new int[] {
        R.id.timeline_handle,
    };

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

        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.timeline_row,
                null,
                PROJ,
                VIEWS,
                0);
        setListAdapter(adapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);
    }
}
