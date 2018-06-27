package com.zamanak.gifttree.api.request;

import android.content.Context;

import com.zamanak.gifttree.GiftTreeSDK;
import com.zamanak.gifttree.api.ApiErrorCB;
import com.zamanak.gifttree.api.ApiSuccessCB;
import com.zamanak.gifttree.api.BaseApi;
import com.zamanak.gifttree.api.Urls;

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
