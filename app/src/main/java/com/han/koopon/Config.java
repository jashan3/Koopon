package com.han.koopon;

import android.app.Application;

import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.StringUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.lang.ref.WeakReference;

public class Config extends Application {
    public final static String AUTO_LOGIN_ID = "AUTO_LOGIN_ID";

    public final static String INTENT_EXTRA_TITLE = "INTENT_EXTRA_TITLE";
    public final static String INTENT_EXTRA_DATE = "INTENT_EXTRA_DATE";
    public final static String INTENT_EXTRA_BODY= "INTENT_EXTRA_BODY";
    public final static String INTENT_EXTRA_CURRENT_COUNT = "INTENT_EXTRA_CURRENT_COUNT";


    private static WeakReference<Config> instance;

    private String userID;
    public String getUserID() {
        return userID;
    }

    public static Config getCurrentApplication() {
        if (instance != null)
            return instance.get();
        return null;
    }


    public static void setCurrentApplication(Config paramContext) {
        instance = new WeakReference<>((Config) paramContext.getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("koopon")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        setCurrentApplication(this);

        userID = PFUtil.getPreferenceString(this,PFUtil.AUTO_LOGIN_ID);
        if (userID!=null){
            userID = StringUtil.emailToStringID(userID);
        }
    }
}
