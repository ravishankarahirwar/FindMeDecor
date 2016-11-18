package com.findmedecore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.findmedecore.R;
import com.findmedecore.util.Constants;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class OptionChooserActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = OptionChooserActivity.class.getSimpleName();

    private TextView mUserName;
    private TextView mDisplayName;

    private Button mPostTweet;
    private Button mSeeAllPost;
    private Button mSearchTimeline;
    private Button mLogout;

    private EditText mSearch;

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
        mSearch = (EditText) this.findViewById(R.id.search);

        mPostTweet = (Button) this.findViewById(R.id.post_tweet);
        mPostTweet.setOnClickListener(this);
        mSeeAllPost = (Button) this.findViewById(R.id.see_all_post);
        mSeeAllPost.setOnClickListener(this);
        mLogout = (Button) this.findViewById(R.id.logout);
        mLogout.setOnClickListener(this);
        mSearchTimeline = (Button) this.findViewById(R.id.search_timeline);
        mSearchTimeline.setOnClickListener(this);

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
                        .text("Find my decor : Your decor is here");
                builder.show();
                break;
            case R.id.see_all_post:
                startAllPostActivity();
                break;
            case R.id.search_timeline:
                if (TextUtils.isEmpty(mSearch.getText())) {
                    mSearch.setError("Please enter search query");
                } else {
                    String query = mSearch.getText().toString();
                    startAllPostActivity(query);
                }
                break;
            case R.id.logout:
                setUpSignOut();
                break;
        }
    }

    private void startAllPostActivity() {
        final Intent startAllPost = new Intent(OptionChooserActivity.this,
                AllPostActivity.class);
        startAllPost.putExtra(Constants.EXTRA_USER_NAME, mSession.getUserName());
        startActivity(startAllPost);
    }

    private void startAllPostActivity(String query) {
        final Intent startAllPost = new Intent(OptionChooserActivity.this,
                AllPostActivity.class);
        startAllPost.putExtra(Constants.EXTRA_USER_NAME, mSession.getUserName());
        startAllPost.putExtra(Constants.EXTRA_IS_SEARCH, true);
        startAllPost.putExtra(Constants.EXTRA_SEARCH_QUERY, query);
        startActivity(startAllPost);
    }

    private void setUpSignOut() {
        Twitter.getSessionManager().clearActiveSession();
        Toast.makeText(getApplicationContext(), R.string.logout,
                Toast.LENGTH_SHORT).show();
        startInitialActivity();
    }

    private void startInitialActivity() {
        final Intent themeChooserIntent = new Intent(OptionChooserActivity.this,
                InitialActivity.class);
        startActivity(themeChooserIntent);
        finish();
    }

}
