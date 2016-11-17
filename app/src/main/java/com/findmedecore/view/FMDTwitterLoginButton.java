package com.findmedecore.view;

import android.content.Context;
import android.util.AttributeSet;

import com.findmedecore.R;
import com.findmedecore.app.AppConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class FMDTwitterLoginButton extends TwitterLoginButton {
    private Context mContext;
    public FMDTwitterLoginButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public FMDTwitterLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FMDTwitterLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (isInEditMode()){
            return;
        }
        setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable
                .ic_signin_twitter), null, null, null);
        setBackgroundResource(R.drawable.sign_up_button);
        setTextSize(20);
        setPadding(30, 0, 10, 0);
        setTextColor(getResources().getColor(R.color.tw__blue_default));
//        setTypeface(AppConfig.getTypeface(mContext));
    }
}
