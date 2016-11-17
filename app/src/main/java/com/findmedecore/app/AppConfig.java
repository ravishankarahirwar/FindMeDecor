package com.findmedecore.app;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by ravi on 17/11/16.
 */

public class AppConfig {
    private static final String FONT_FILE_NAME = "fonts/Avenir.ttc";
    private static Typeface avenirFont;

    public static Typeface getTypeface(Context context) {
        if (avenirFont == null) {
            extractAvenir(context);
        }
        return avenirFont;
    }

    private static void extractAvenir(Context context) {
        avenirFont = Typeface.createFromAsset(context.getApplicationContext().getAssets(), FONT_FILE_NAME);
    }
}
