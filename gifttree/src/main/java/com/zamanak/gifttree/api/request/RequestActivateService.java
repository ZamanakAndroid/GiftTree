package com.zamanak.gifttree.api.request;

import android.content.Context;

import com.zamanak.gifttree.GiftTreeSDK;
import com.zamanak.gifttree.api.ApiErrorCB;
import com.zamanak.gifttree.api.ApiSuccessCB;
import com.zamanak.gifttree.api.BaseApi;


public class RequestActivateService extends BaseApi {

    public RequestActivateService(Context context, String endPoint,
                                  ApiSuccessCB apiSuccessCB, ApiErrorCB apiErrorCB) {

        super(context, null, null, apiSuccessCB, apiErrorCB,
                false, true);
        this.endPoint = endPoint;
        this.api_key = GiftTreeSDK.getUserApiKey();
        this.token = GiftTreeSDK.getTOKEN();
    }
}
