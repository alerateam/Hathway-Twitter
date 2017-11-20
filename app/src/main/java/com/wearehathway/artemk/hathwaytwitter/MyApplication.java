package com.wearehathway.artemk.hathwaytwitter;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by Artem on 11/20/17.
 */

public class MyApplication extends Application {

    private static final String TWITTER_API_KEY = "lBHd8a37e5Uwqta6Rg7cPePHQ";
    private static final String TWITTER_API_SECRET = "RVUNLrvbLAnqBFJK8KN8eos3eNe3y431WEC7RSYFxJRxgDJdVX";

    public void onCreate() {
        super.onCreate();
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_API_KEY, TWITTER_API_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
