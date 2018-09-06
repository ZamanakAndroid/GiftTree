package com.zamanak.shamimsalamat.api.request;

import android.content.Context;

import com.zamanak.shamimsalamat.GiftTreeSDK;
import com.zamanak.shamimsalamat.api.ApiErrorCB;
import com.zamanak.shamimsalamat.api.ApiSuccessCB;
import com.zamanak.shamimsalamat.api.BaseApi;


public class RequestActivateService extends BaseApi {

    public RequestActivateService(Context context, String endPoint,
                                  ApiSuccessCB apiSuccessCB, ApiErrorCB apiErrorCB, String appName) {

        super(context, null, null, apiSuccessCB, apiErrorCB,
                false, true);
        this.endPoint = endPoint;
        if ("zaer".equals(appName)) {
            this.api_key = GiftTreeSDK.getBaseApiKey();
        }else if ("shamim".equals(appName)){
            this.api_key = GiftTreeSDK.getUserApiKey();
        }
        this.token = GiftTreeSDK.getTOKEN();
    }
}
