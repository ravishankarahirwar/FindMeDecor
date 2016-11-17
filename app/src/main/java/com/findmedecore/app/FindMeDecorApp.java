package com.findmedecore.app;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ravi on 17/11/16.
 */

public class FindMeDecorApp extends Application {

    private static FindMeDecorApp singleton;
    private TwitterAuthConfig authConfig;

    public static FindMeDecorApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());
    }
}
