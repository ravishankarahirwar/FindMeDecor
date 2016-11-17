package com.findmedecore.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.findmedecore.R;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class AllPostActivity extends AppCompatActivity {
    private ListView timeLine;
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);

        mUserName = getIntent().getStringExtra("user_name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mUserName != null) {
            AllPostTask allPostTask = new AllPostTask(AllPostActivity.this);
            allPostTask.execute(null, null, null);
        }
    }

    class AllPostTask extends AsyncTask<Void, Void, TweetTimelineListAdapter> {
        private Context mContext;
        private ProgressDialog mProgressBar;

        public AllPostTask(Context context) {
            mContext = context;
            mProgressBar = new ProgressDialog(mContext);
            mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressBar.setIndeterminate(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.show();
        }

        @Override
        protected TweetTimelineListAdapter doInBackground(Void... voids) {
            timeLine = (ListView) findViewById(R.id.list);
            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName(mUserName)
                    .build();
            final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(mContext)
                    .setTimeline(userTimeline)
                    .build();
            return adapter;
        }

        @Override
        protected void onPostExecute(TweetTimelineListAdapter adapter) {
            super.onPostExecute(adapter);
            timeLine.setAdapter(adapter);
            mProgressBar.dismiss();
        }
    }
}
