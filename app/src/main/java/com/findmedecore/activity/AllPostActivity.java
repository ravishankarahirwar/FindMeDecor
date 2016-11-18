package com.findmedecore.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.findmedecore.R;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class AllPostActivity extends AppCompatActivity {
    private ListView timeLine;
    private String mUserName;
    private boolean isSearchTimeLine;
    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_post);

        timeLine = (ListView) findViewById(R.id.list);

        mUserName = getIntent().getStringExtra("user_name");
        isSearchTimeLine = getIntent().getBooleanExtra("isSearch", false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mUserName != null && !isSearchTimeLine) {
            setTitle("Timeline");
            AllPostTask allPostTask = new AllPostTask(AllPostActivity.this);
            allPostTask.execute(null, null, null);
        } else if(isSearchTimeLine) {
            String query = getIntent().getStringExtra("search_query");
            setTitle("Search : " + query);
            if(query != null && query.length() > 0) {
                SearchTimeLineTask searchTimeLineTask = new SearchTimeLineTask(AllPostActivity.this);
                searchTimeLineTask.execute(query, null, null);
            }
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

    class SearchTimeLineTask extends AsyncTask<String, Void, TweetTimelineListAdapter> {
        private Context mContext;
        private ProgressDialog mProgressBar;
        private String mQuery;

        public SearchTimeLineTask(Context context) {
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
        protected TweetTimelineListAdapter doInBackground(String... queries) {
            mQuery = queries[0].toString();
            final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                    .query(mQuery)
                    .build();
            final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(mContext)
                    .setTimeline(searchTimeline)
                    .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
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
