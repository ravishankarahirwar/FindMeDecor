package com.findmedecore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.findmedecore.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class OptionChooserActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = OptionChooserActivity.class.getSimpleName();

    private TextView mUserName;
    private TextView mDisplayName;

    private Button mPostTweet;
    private Button mSeeAllPost;
    private Button mLogout;

    private String userName;

    private TwitterSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_chooser);
        init();
        setupView();
    }

    private void init() {
        mUserName = (TextView) this.findViewById(R.id.user_name);
        mDisplayName = (TextView) this.findViewById(R.id.display_name);

        mPostTweet = (Button) this.findViewById(R.id.post_tweet);
        mPostTweet.setOnClickListener(this);
        mSeeAllPost = (Button) this.findViewById(R.id.see_all_post);
        mSeeAllPost.setOnClickListener(this);
        mLogout = (Button) this.findViewById(R.id.logout);
        mLogout.setOnClickListener(this);

        mSession = Twitter.getSessionManager().getActiveSession();
    }

    private void setupView() {
        if (mSession != null) {
            mUserName.setText(mSession.getUserName());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_tweet:
                TweetComposer.Builder builder = new TweetComposer.Builder(OptionChooserActivity.this)
                        .text("Find my decor : Your decore is here");
                builder.show();
                break;
            case R.id.see_all_post:
                startAllPostActivity();
                break;
            case R.id.logout:
                setUpSignOut();
                break;
        }
    }

    private void startAllPostActivity() {
        final Intent startAllPost = new Intent(OptionChooserActivity.this,
                AllPostActivity.class);
        startAllPost.putExtra("user_name", mSession.getUserName());
        startActivity(startAllPost);
    }

    private void setUpSignOut() {
        Twitter.getSessionManager().clearActiveSession();
        Toast.makeText(getApplicationContext(), "Logout",
                Toast.LENGTH_LONG).show();
        startInitialActivity();
    }

    private void startInitialActivity() {
        final Intent themeChooserIntent = new Intent(OptionChooserActivity.this,
                InitialActivity.class);
        startActivity(themeChooserIntent);
        finish();
    }

}
