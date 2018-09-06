package com.zamanak.shamimsalamat.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.zamanak.shamimsalamat.fragment.ChanceTreeFragment;
import com.zamanak.shamimsalamat.R;

public class ScoreActivity extends BaseActivityNew {

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        pushFragment(ChanceTreeFragment.class, R.id.fragment);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_score;
    }

    @Override
    protected boolean isRtl() {

        return true;
    }

    @Override
    protected boolean isPortrait() {

        return true;
    }

    @Override
    protected void setListener() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mActivity.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
            finish();
        return super.onOptionsItemSelected(item);
    }
}
