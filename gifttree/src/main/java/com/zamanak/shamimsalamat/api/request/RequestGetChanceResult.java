package com.zamanak.shamimsalamat.api.request;

import android.content.Context;

import com.zamanak.shamimsalamat.GiftTreeSDK;
import com.zamanak.shamimsalamat.api.ApiErrorCB;
import com.zamanak.shamimsalamat.api.ApiSuccessCB;
import com.zamanak.shamimsalamat.api.BaseApi;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PirFazel on 12/25/2016.
 */

public class RequestGetChanceResult extends BaseApi {

    public RequestGetChanceResult(Context context, ApiSuccessCB outerSuccessCB,
                                  ApiErrorCB outerErrorCB, String api_key, String url) {

        super(context, GiftTreeSDK.getBaseUrl(), url, outerSuccessCB, outerErrorCB,
                false,true);
        this.api_key = GiftTreeSDK.getBaseApiKey();
        this.token   = GiftTreeSDK.getTOKEN();
    }

    @Override
    protected void prepareRequest() throws JSONException {
    }

    public JSONObject getResponse() {
        return resJson;
    }
}
