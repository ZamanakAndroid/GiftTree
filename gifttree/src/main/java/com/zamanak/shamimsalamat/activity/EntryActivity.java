package com.zamanak.shamimsalamat.activity;

import android.os.Bundle;

import com.zamanak.shamimsalamat.utils.Constants;
import com.zamanak.shamimsalamat.utils.FirebaseLogUtils;

public class EntryActivity extends BaseActivityNew {


    protected String getActivityName() {
        return Constants.ENTRY_ACTIVITY;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        FirebaseLogUtils.logEvent(this, "login_begin");
        //pushFragment(SendCodeFragment.class, R.id.fragment_entry);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        /**
         * added by abozar for set custom toolbar
         */
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        AppCompatTextView tvToolbarTile = (AppCompatTextView) findViewById(R.id.tvMainToolbar);
        tvToolbarTile.setText("ارسال کد فعال سازی");*/
    }

    @Override
    protected int getLayoutResource() {
        return 0;
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
    public void onBackPressed() {

        super.onBackPressed();
    }
}
