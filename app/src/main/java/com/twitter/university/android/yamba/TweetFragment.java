package com.twitter.university.android.yamba;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaServiceHelper;

/**
 * Created by bmeike on 4/17/14.
 */
public class TweetFragment extends Fragment {
    private static final String TAG = "TWEET";

    private int okColor;
    private int warnColor;
    private int errColor;

    private int tweetLenMax;
    private int warnMax;
    private int errMax;

    private EditText tweetView;
    private TextView countView;
    private Button submitButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources rez = getResources();
        okColor = rez.getColor(R.color.green);
        tweetLenMax = rez.getInteger(R.integer.tweet_limit);
        warnColor = rez.getColor(R.color.yellow);
        warnMax = rez.getInteger(R.integer.warn_limit);
        errColor = rez.getColor(R.color.red);
        errMax = rez.getInteger(R.integer.err_limit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweet, container, false);
        v.findViewById(R.id.tweet_label);

        countView = (TextView) v.findViewById(R.id.tweet_count);

        submitButton = (Button) v.findViewById(R.id.tweet_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) { post(); }
        });

        tweetView = (EditText) v.findViewById(R.id.tweet_tweet);
        tweetView.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) { updateCount(); }

                @Override
                public void beforeTextChanged(CharSequence s, int b, int n, int a) { }

                @Override
                public void onTextChanged(CharSequence s, int b, int p, int n) { }
            });

        return v;
    }

    void updateCount() {
        int n = tweetView.getText().length();

        submitButton.setEnabled(checkTweetLen(n));

        n = tweetLenMax - n;

        int color;
        if (n > warnMax) { color = okColor; }
        else if (n > errMax) { color = warnColor; }
        else  { color = errColor; }

        countView.setText(String.valueOf(n));
        countView.setTextColor(color);
    }

    void post() {
        String tweet = tweetView.getText().toString();
        if (!checkTweetLen(tweet.length())) { return; }

        YambaServiceHelper.post(getActivity(), tweet);

        tweetView.setText("");
    }

    private boolean checkTweetLen(int n) {
        return (errMax < n) && (tweetLenMax > n);
    }
}
