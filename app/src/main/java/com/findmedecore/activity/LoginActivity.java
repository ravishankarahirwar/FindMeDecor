package com.findmedecore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.findmedecore.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class LoginActivity extends Activity {

    private TwitterLoginButton twitterButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        setUpViews();
    }

    private void setUpViews() {
        setUpTwitterButton();
    }

    private void setUpTwitterButton() {
        twitterButton = (TwitterLoginButton) findViewById(R.id.twitter_button);
        twitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                startOptionChooserActivity();
            }

            @Override
            public void failure(TwitterException exception) {
                if (exception.getMessage().equalsIgnoreCase("Authorize failed.")) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.toast_twitter_signin_fail) + exception.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    requestInstallTwitter();
                }


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterButton.onActivityResult(requestCode, resultCode, data);
    }

    private void startOptionChooserActivity() {
        final Intent themeChooserIntent = new Intent(LoginActivity.this,
                OptionChooserActivity.class);
        startActivity(themeChooserIntent);
        finish();
    }

    private void requestInstallTwitter() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(mContext.getString(R.string.title_text_to_image));
        alertDialog.setIcon(R.drawable.ic_signin_twitter);
        alertDialog.setMessage(mContext.getString(R.string.desc_text_to_image));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            } });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mContext.getString(R.string.install), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent  intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.twitter.android"));
                mContext.startActivity(intent);
            }});
        alertDialog.show();
    }
}
