package com.zamanak.shamimsalamat.api.request;

import android.content.Context;

import com.zamanak.shamimsalamat.GiftTreeSDK;
import com.zamanak.shamimsalamat.api.ApiErrorCB;
import com.zamanak.shamimsalamat.api.ApiSuccessCB;
import com.zamanak.shamimsalamat.api.BaseApi;

import org.json.JSONException;


public class RequestValidateMTN extends BaseApi {

    private ValidateMtnRequest validateMtnRequest;

    public RequestValidateMTN(Context context, ApiSuccessCB apiSuccessCB,
                              ApiErrorCB apiErrorCB, ValidateMtnRequest validateMtnRequest) {

        super(context, GiftTreeSDK.getBaseUrl(), GiftTreeSDK.getValidateMtn(), apiSuccessCB,
                apiErrorCB, true, true);
        this.validateMtnRequest = validateMtnRequest;
        this.api_key = GiftTreeSDK.getUserApiKey();
        this.token = GiftTreeSDK.getTOKEN();
    }

    @Override
    protected void prepareRequest() throws JSONException {
        reqJson.put("packageName", validateMtnRequest.getPackageName());
        reqJson.put("subscriptionId", validateMtnRequest.getSubscriptionId());
        reqJson.put("token", validateMtnRequest.getToken());
    }
}
