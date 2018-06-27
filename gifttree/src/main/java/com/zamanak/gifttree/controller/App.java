package com.zamanak.gifttree.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.zamanak.gifttree.utils.Constants;
import com.zamanak.gifttreelibrary.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by PirFazel on 11/19/2016.
 */

public class App extends MultiDexApplication {

    public static final String PREFS_LOGIN = "PrefsLogin";
    @SuppressLint("StaticFieldLeak")
    private static App mInstance;
    private static Context mAppContext;
    public static volatile Handler applicationHandler;
    public static String MARKET_NAME =  Constants.MARKET_BAZAAR;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.setAppContext(getApplicationContext());
        //FontUtils.setDefaultFont(this, "DEFAULT", "IRANSansWeb.ttf");
        //FontUtils.setDefaultFont(this, "MONOSPACE", "IRANSansWeb.ttf");
        /**
         * added by abozar for support farsi font and farsi numbers
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iransansmobilefanum.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        applicationHandler = new Handler(mAppContext.getMainLooper());
    }

    /**
     * overide this method in each activity for support farsi font added by abozar
     * @param base
     */
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        App.mAppContext = mAppContext;
    }


}
