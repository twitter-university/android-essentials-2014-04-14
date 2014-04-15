package com.twitter.university.android.yamba;

import android.app.Activity;
import android.os.Bundle;


public class TweetActivity extends Activity {
    private static final String TAG = "TWEET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
    }
}
