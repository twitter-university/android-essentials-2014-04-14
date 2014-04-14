package com.twitter.university.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class TweetActivity extends Activity {
    private static final String TAG = "TWEET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        Log.d(TAG, "in onCreate:" + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "in onDestroy:" + this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "in onStart:" + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "in onStop:" + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "in onResume:" + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "in onPause:" + this);
    }
}
