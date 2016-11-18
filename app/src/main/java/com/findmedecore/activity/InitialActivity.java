package com.findmedecore.activity;

import android.content.Intent;
import android.os.Bundle;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Session;

public class InitialActivity extends BaseActivity {
    private static final String TAG = InitialActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Session activeSession = Twitter.getSessionManager().getActiveSession();

        if (activeSession != null) {
            startOptionChooserActivity();
        } else {
            startLoginActivity();
        }
    }

    private void startOptionChooserActivity() {
        startActivity(new Intent(this, OptionChooserActivity.class));
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
