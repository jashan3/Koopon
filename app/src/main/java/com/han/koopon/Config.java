package com.han.koopon;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class Config extends Application {
    public final static String AUTO_LOGIN_ID = "AUTO_LOGIN_ID";

    public final static String INTENT_EXTRA_TITLE = "INTENT_EXTRA_TITLE";
    public final static String INTENT_EXTRA_CURRENT_COUNT = "INTENT_EXTRA_CURRENT_COUNT";
    @Override
    public void onCreate() {
        super.onCreate();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("koopon")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
