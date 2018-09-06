package com.zamanak.shamimsalamat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.billingclient.util.IabHelper;
import com.android.billingclient.util.IabResult;
import com.android.billingclient.util.Purchase;
import com.zamanak.shamimsalamat.activity.BaseActivityNew;
import com.zamanak.shamimsalamat.api.ApiErrorCB;
import com.zamanak.shamimsalamat.api.ApiSuccessCB;
import com.zamanak.shamimsalamat.api.BaseApi;
import com.zamanak.shamimsalamat.utils.FirebaseLogUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;


public class CharkhoneActivity extends BaseActivityNew {


    private static final String TAG = "CharkhoneActivity";

    // The helper object
    private IabHelper mHelper;

    private static final int RC_REQUEST = 10001;

    private static final String SKU = "shamim1";

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        initCharkhoneSdk();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            //mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    @Override
    protected int getLayoutResource() {

        //return R.layout.activity_charkhone;
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

    private void initCharkhoneSdk() {

        String base64PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjKbH3Rh5G3yFIslXR/" +
                "mc/M/JJpTLjZfo7OjubR7FDnEmLmwa0YxIvso06rm9ShXskxfIoAhVpAw0JS/Q8yb6haQba89TUisfy" +
                "HjAUaLoHzCl383o6hEZ7ANiqwabvmpG96L5j8ItJRQkV6bHYmrAT6qbKLuR645NGlITyWWJnowIDAQAB";

        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(mActivity, base64PublicKey);
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");
                if (!result.isSuccess()) {
                    return;
                }
                if (mHelper == null) {
                    return;
                }
                purchase();
            }
        });
    }

    private void purchase() {
        String payload = "";
        try {
            FirebaseLogUtils.logEvent(mActivity, "vasPage_sendPhone");
            mHelper.launchPurchaseFlow(mActivity, SKU, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }

    private void complain(String s) {
        Log.e("complain", s);
    }

    private IabHelper.OnIabPurchaseFinishedListener
            mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {

        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            if (mHelper == null) return;
            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                setWaitScreen(false);
                finish();
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                setWaitScreen(false);
                finish();
                return;
            }
            FirebaseLogUtils.logEvent(mActivity, "vasPage_sendOTP");
            Log.d(TAG, "Purchase successful.");
            if (purchase.getSku().equals(SKU)) {
                alert(mActivity.getString(R.string.successful));
                //finish();
                // TODO call mtn-validate here
                //new ValidateMtnRequest(purchase.getPackageName(), purchase.getSku(), purchase.getToken());
                FirebaseLogUtils.logEvent(mActivity, "vasPage_subscribeSendCodeResult");
                callValidateMTNAPI(purchase);
            }
        }
    };

    private void callValidateMTNAPI(final Purchase purchase) {

        new RequestValidateMTN(this, new ApiSuccessCB() {
            @Override
            public void onSuccess(BaseApi service) {
                Log.v("cpRegister", service.resJson.toString());
                try {

                    if (service.resJson.get("error").equals(null)) {
                        EventBus.getDefault().post(purchase);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ApiErrorCB() {
            @Override
            public void onError(Exception e) {
                Log.e("cpRegister", e.getMessage());
            }
        },new ValidateMtnRequest(purchase.getPackageName(),purchase.getSku(), purchase.getToken())).execute();
    }



    private void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(mActivity);
        bld.setMessage(message);
        bld.setNeutralButton(mActivity.getString(R.string.okBtn), null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    private boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    // Enables or disables the "please wait" screen.
    private void setWaitScreen(boolean b) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null)
            return;
        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            //TODO : handle
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }



}
