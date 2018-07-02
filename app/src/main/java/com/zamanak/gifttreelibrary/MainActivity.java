package com.zamanak.gifttreelibrary;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.zamanak.gifttree.GiftTreeSDK;
import com.zamanak.gifttree.activity.BaseActivityNew;

public class MainActivity extends BaseActivityNew {

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        GiftTreeSDK.sharedLandOfHealth().startScoreActivity(this,"","");

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isRtl() {
        return false;
    }

    @Override
    protected boolean isPortrait() {
        return false;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
