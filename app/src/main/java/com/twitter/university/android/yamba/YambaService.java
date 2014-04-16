package com.twitter.university.android.yamba;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by bmeike on 4/16/14.
 */
public class YambaService extends IntentService {
    private static final String TAG = "SVC";

    private static final int OP_POST = -1;

    private static final String PARAM_OP = "YambaService.OP";
    private static final String PARAM_TWEET = "YambaService.TWEET";

    public static void post(Context ctxt, String tweet) {
        Intent i = new Intent(ctxt, YambaService.class);
        i.putExtra(PARAM_OP, OP_POST);
        i.putExtra(PARAM_TWEET, tweet);
        Log.d(TAG, "sending post");
        ctxt.startService(i);
    }


    private volatile Handler hdlr;

    public YambaService() { super(TAG); }

    @Override
    public void onCreate() {
        super.onCreate();
        hdlr = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int op = intent.getIntExtra(PARAM_OP, 0);
        Log.d(TAG, "handle intent: " + op);
        switch (op) {
            case OP_POST:
                doPost(intent.getStringExtra(PARAM_TWEET));
                break;
            default:
                throw new IllegalStateException("unexpected op: " + op);
        }
    }

    private void doPost(String tweet) {
        int status = R.string.post_failed;
        try {
            Thread.sleep(1000 * 10);
            status = R.string.post_succeeded;
        }
        catch (InterruptedException e) { }
        Log.d(TAG, "post complete");

        final int stat = status;
        hdlr.post(new Runnable() {
            @Override public void run() { postComplete(stat); }
        });
    }

    void postComplete(int status) {
        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }
}
