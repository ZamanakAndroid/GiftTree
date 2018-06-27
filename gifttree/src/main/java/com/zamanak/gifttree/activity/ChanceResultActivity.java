package com.zamanak.gifttree.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.bumptech.glide.Glide;
import com.zamanak.gifttree.events.BooleanEventBus;
import com.zamanak.gifttree.utils.FirebaseLogUtils;
import com.zamanak.gifttree.utils.Utility;
import com.zamanak.gifttreelibrary.R;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zamanak on 6/11/2018.
 */

public class ChanceResultActivity extends BaseActivityNew implements View.OnClickListener {

    private AppCompatTextView tvPrizeType, tvPrizeDesc;
    private CircleImageView ivPrizeIcon;
    private AppCompatButton btnPrizeAction;
    private AppCompatImageView ivBack;
    private String closureType, closureMedia, closureText;
    public Context context;
    private boolean isCanShake=false;

    @Override
    protected void setListener() {

        ivBack.setOnClickListener(this);
        btnPrizeAction.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        if (!this.isNetworkConnected()) {
            return R.layout.fragment_no_connection;

        } else {
            return R.layout.activity_chance_result;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        context = this;
        tvPrizeType = getViewById(R.id.tv_prize_type);
        tvPrizeDesc = getViewById(R.id.tv_prize_desc);
        ivPrizeIcon = getViewById(R.id.iv_prize_icon);
        btnPrizeAction = getViewById(R.id.btn_prize_action);
        ivBack = getViewById(R.id.iv_back);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        FirebaseLogUtils.logEvent(mActivity, "amazingTree_giftVisited");
        getBundle();

    }


    @Override
    protected boolean isRtl() {
        return true;
    }

    @Override
    protected boolean isPortrait() {
        return true;
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("title");
            String des = bundle.getString("description");
            closureType = bundle.getString("closureType");
            closureMedia = bundle.getString("closureMedia");
            closureText = bundle.getString("closureText");
            String iconUrl = bundle.getString("image");
            String textOfButton = bundle.getString("closureButtonText");
            Glide.with(this).load(iconUrl).into(ivPrizeIcon);
            tvPrizeType.setText(title);
            tvPrizeDesc.setText(des);
            if (!"none".equals(closureType)) {
                btnPrizeAction.setVisibility(View.VISIBLE);
                btnPrizeAction.setText(textOfButton);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ivBack.getId()) {
            EventBus.getDefault().post(new BooleanEventBus(isCanShake));
            this.finish();
        } else if (v.getId() == btnPrizeAction.getId()) {
            if ("share".equals(closureType)) {
                FirebaseLogUtils.logEvent(mActivity, "amazingTree_closureBTN");
                Utility.shareTextUrl(closureText, "", this);
            } else if ("link".equals(closureType)) {
                Intent intent = new Intent(this, AdvancedWebViewActivity.class);
                intent.putExtra("link", closureText);
                startActivity(intent);
            } else if ("call".equals(closureType)) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + closureText));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new BooleanEventBus(isCanShake));
        finish();
    }

}
