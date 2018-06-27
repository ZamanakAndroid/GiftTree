package com.zamanak.gifttree.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.zamanak.gifttree.GiftTreeSDK;
import com.zamanak.gifttree.api.ApiErrorCB;
import com.zamanak.gifttree.api.ApiSuccessCB;
import com.zamanak.gifttree.api.BaseApi;
import com.zamanak.gifttree.api.Urls;
import com.zamanak.gifttree.api.request.RequestGetChanceResult;
import com.zamanak.gifttree.api.result.ResultGetChanceRes;
import com.zamanak.gifttree.api.result.SModel;
import com.zamanak.gifttree.events.ShakeDetector;
import com.zamanak.gifttree.utils.Constants;
import com.zamanak.gifttree.utils.FirebaseLogUtils;
import com.zamanak.gifttreelibrary.R;

public class TryChanceActivity extends BaseActivityNew implements View.OnClickListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private LottieAnimationView lottieAnimationView;
    private AppCompatTextView tvTxt1, tvTxt2;
    private MediaPlayer mpShakeTree, mpWin;
    private boolean isFirstTimeToShake = false;
    private AppCompatImageView ivWebView;
    private AppCompatButton btnSpecialChance;
    private SModel sModel;
    private double longitude, latitude;
    private boolean isCanShake;



    @Override
    protected void setListener() {
        btnSpecialChance.setOnClickListener(this);
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
    protected int getLayoutResource() {
        if (!this.isNetworkConnected()) {
            return R.layout.fragment_no_connection;

        } else {
            return R.layout.activity_try_chance;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mpShakeTree = MediaPlayer.create(this, R.raw.tree_shaking);
        mpWin = MediaPlayer.create(this, R.raw.win);

        btnSpecialChance = getViewById(R.id.btn_special_chance);
        tvTxt1 = findViewById(R.id.tv_txt_1);
        tvTxt2 = findViewById(R.id.tv_txt_2);
        tvTxt1.setTypeface(Typeface.createFromAsset(this.getResources().getAssets(), "fonts/iransansmobilefanum.ttf"));
        tvTxt2.setTypeface(Typeface.createFromAsset(this.getResources().getAssets(), "fonts/iransansmobilefanum.ttf"));
        lottieAnimationView = findViewById(R.id.animation_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String chanceType = bundle.getString("chanceType");
            sModel = (SModel) bundle.getSerializable("sModel");
            if ("special".equals(chanceType)) {
                lottieAnimationView.setAnimation(R.raw.data);
            } else if ("ordinary".equals(chanceType)) {
                // noe data lottie animation taghir mikonad
                btnSpecialChance.setVisibility(View.VISIBLE);
                lottieAnimationView.setAnimation(R.raw.small_tree);
            }
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
        } else {
            Toast.makeText(mActivity, "محتوای باندل خالی است", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                if (!isFirstTimeToShake) {
                    isFirstTimeToShake = true;
                    if (count == 1) {
                        mpShakeTree.start();
                        lottieAnimationView.playAnimation();
                        FirebaseLogUtils.logEvent(mActivity, "amazingTree_shaking");
                        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mpShakeTree.stop();
                                    }
                                }, 500);
                                mpWin.start();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mpWin.stop();
                                    }
                                }, 1500);
                                callGetChanceResult();
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                    } else {
                        return;
                    }
                } else {
                    //Toast.makeText(TryChanceActivity.this, "فقط یکبار میتوانید گوشی خود را تکان دهید", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }

    private void callGetChanceResult() {
        final String queryUrl = GiftTreeSDK.getGetChanceResult() + "?lat=" + latitude + "&long=" + longitude;
        new RequestGetChanceResult(this, new ApiSuccessCB() {
            @Override
            public void onSuccess(BaseApi service) {
                final ResultGetChanceRes res = new Gson().fromJson(service.resJson.toString(), ResultGetChanceRes.class);
                if ("Ok".equals(res.getCode()) && res.getResult() != null) {
                    //Toast.makeText(TryChanceActivity.this, "لطفا منتظر بمانید", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(TryChanceActivity.this, ChanceResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", res.getResult().getGift().getTitle());
                            bundle.putString("image", res.getResult().getGift().getImage());
                            bundle.putString("description", res.getResult().getGift().getDescription());
                            bundle.putString("closureType", res.getResult().getGift().getClosureType());
                            bundle.putString("closureButtonText", res.getResult().getGift().getClosureButtonText());
                            bundle.putString("closureMedia", res.getResult().getGift().getClosureMedia());
                            bundle.putString("closureText", res.getResult().getGift().getClosureText());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                }
            }
        }, new ApiErrorCB() {
            @Override
            public void onError(Exception e) {

            }
        }, Constants.BASE_API_KEY, queryUrl).execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        if (mSensorManager != null) {
            mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(mShakeDetector);
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnSpecialChance.getId()) {
            FirebaseLogUtils.logEvent(mActivity, "amazingTree_subscribeBTN");
            SModel vas = sModel;
            if (vas != null) {
                //TODO if you want to use this code you should add VasDialogNew in your project
              /*  VasDialogNew.newInstance(vas.getService(),
                        vas.getUrl(), vas.getImageUrl(),
                        vas.getTitle(), vas.getDetail(),
                        vas.getbText())
                        .show(getSupportFragmentManager());*/
            }
        }
    }



}
