package com.zamanak.gifttreelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zamanak.gifttree.GiftTreeSDK;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GiftTreeSDK.sharedLandOfHealth().startScoreActivity(this,"","");
    }
}
