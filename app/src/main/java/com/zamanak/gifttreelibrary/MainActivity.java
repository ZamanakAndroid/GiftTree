package com.zamanak.gifttreelibrary;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.zamanak.gifttree.GiftTreeSDK;
import com.zamanak.gifttree.activity.BaseActivityNew;

public class MainActivity extends BaseActivityNew {

    private AppCompatTextView tvHello;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        tvHello = findViewById(R.id.tvHello);
        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftTreeSDK.sharedLandOfHealth().startScoreActivity(MainActivity.this,"","","","","");

            }
        });
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

}
