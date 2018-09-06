package com.zamanak.shamimsalamat.activity;

import android.animation.Animator;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.zamanak.shamimsalamat.GiftTreeSDK;
import com.zamanak.shamimsalamat.api.ApiErrorCB;
import com.zamanak.shamimsalamat.api.ApiSuccessCB;
import com.zamanak.shamimsalamat.api.BaseApi;
import com.zamanak.shamimsalamat.api.object.ShakeReminderObject;
import com.zamanak.shamimsalamat.api.request.RequestGetChanceResult;
import com.zamanak.shamimsalamat.api.result.ResultGetChanceRes;
import com.zamanak.shamimsalamat.api.result.SModel;
import com.zamanak.shamimsalamat.events.ShakeDetector;
import com.zamanak.shamimsalamat.receiver.MyReceiver;
import com.zamanak.shamimsalamat.utils.FirebaseLogUtils;
import com.zamanak.shamimsalamat.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private int mCurrentHours, mCurrentMinutees;
    private ShakeReminderObject obj;


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
                                shakeReminder();
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



    private void shakeReminder() {
        String startHourStr, startMinStr, timeShakeStr, setTimeShakeStr;
        mCurrentHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mCurrentMinutees = Calendar.getInstance().get(Calendar.MINUTE);
        Log.d("shakeReminder: ", mCurrentHours + " " + ":" + mCurrentMinutees + " ");
        if (mCurrentHours < 10) {
            startHourStr = "0" + mCurrentHours;
        } else {
            startHourStr = mCurrentHours + "";
        }
        if (mCurrentMinutees == 0) {
            startMinStr = "00";
        } else {
            startMinStr = "" + mCurrentMinutees;
        }
        timeShakeStr = startHourStr + ":" + startMinStr;
        Log.d("timeShakeStr: ", timeShakeStr);

        setTimeShakeStr = calcNewTime(timeShakeStr);
        Log.d("setTimeShakeStr: ", setTimeShakeStr);

        obj = new ShakeReminderObject();

        int hourPassed = mCurrentHours + 24;
        if (hourPassed > 24) {
            hourPassed = hourPassed - 24;
        }
        //save hourPassed
        obj.setHousr(hourPassed);
        int minutePassed = mCurrentMinutees + 60;
        if (minutePassed > 60) {
            minutePassed = minutePassed - 60;
        }
        // save minutes
        //int testMin = minutePassed+2;
        obj.setMinutes(minutePassed);

        shakeManager();

    }

    private void shakeManager() {
        Intent notifyIntent = new Intent(TryChanceActivity.this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TryChanceActivity.this, 0, notifyIntent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //Calendar calendar = Calendar.getInstance();
        //int mCurrentHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        //int mCurrentMinutees = Calendar.getInstance().get(Calendar.MINUTE);
        /*if (obj.getHousr() != 0 && obj.getMinutes() != 0) {
            calendar.set(Calendar.HOUR_OF_DAY, obj.getHousr());
            calendar.set(Calendar.MINUTE, obj.getMinutes());
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    86400000,
                    pendingIntent);

        }*/
        // repeat every 2 min - > 2*60*1000
        // for repeating in every 24 hours - > 86400000

        /*Calendar firingCal = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();

        firingCal.set(Calendar.HOUR,   17);
        firingCal.set(Calendar.MINUTE, 8);
        firingCal.set(Calendar.SECOND, 0);

        long intendedTime = firingCal.getTimeInMillis();
        long currentTime = currentCal.getTimeInMillis();

        if (intendedTime >= currentTime) {
            // set from today
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            // set from next day
            // you might consider using calendar.add() for adding one day to the current day
            firingCal.add(Calendar.DAY_OF_MONTH, 1);
            intendedTime = firingCal.getTimeInMillis();

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }*/
        Calendar firingCal = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();
        firingCal.setTimeInMillis(System.currentTimeMillis());
        firingCal.set(Calendar.HOUR_OF_DAY, obj.getHousr());
        firingCal.set(Calendar.MINUTE, obj.getMinutes());
        firingCal.set(Calendar.SECOND, 0);
        long fireTime = firingCal.getTimeInMillis();
        long currentTime = currentCal.getTimeInMillis();
        if (fireTime >= currentTime) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, fireTime, 1440 * 60 * 1000, // for repeating// in every 24// hours
                    pendingIntent);
        } else {
            firingCal.add(Calendar.DAY_OF_MONTH, 1);
            fireTime = firingCal.getTimeInMillis();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, fireTime, 1440 * 60 * 1000, // for repeating// in every 24// hours
                    pendingIntent);
        }

    }

    private String calcNewTime(String startTime) {
        String myTime = startTime;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);

            //double amount = diffrenceOfUsage*60;


            //cal.add(Calendar.MINUTE, (int) amount);
            String newTime = df.format(cal.getTime());
            return newTime;
        } catch (ParseException e) {

            e.printStackTrace();
            return startTime;
        }

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
        }, GiftTreeSDK.getBaseApiKey(), queryUrl).execute();
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
