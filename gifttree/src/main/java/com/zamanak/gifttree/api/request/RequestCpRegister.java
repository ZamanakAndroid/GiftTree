package com.zamanak.gifttree.api.request;

import android.content.Context;

import com.zamanak.gifttree.GiftTreeSDK;
import com.zamanak.gifttree.api.ApiErrorCB;
import com.zamanak.gifttree.api.ApiSuccessCB;
import com.zamanak.gifttree.api.BaseApi;
import com.zamanak.gifttree.api.object.CpRegisterObj;

import org.json.JSONException;


public class RequestCpRegister extends BaseApi {

    private CpRegisterObj cpRegisterObj;

    public RequestCpRegister(Context context, ApiSuccessCB apiSuccessCB,
                             ApiErrorCB apiErrorCB, CpRegisterObj cpRegisterObj) {

        super(context, GiftTreeSDK.getBaseUrl(), GiftTreeSDK.getCpRegister(), apiSuccessCB,
                apiErrorCB, true, true);
        this.cpRegisterObj = cpRegisterObj;
        this.api_key = GiftTreeSDK.getUserApiKey();
        this.token = GiftTreeSDK.getTOKEN();
    }

    @Override
    protected void prepareRequest() throws JSONException {
        reqJson.put("code", cpRegisterObj.getCode());
        reqJson.put("service", cpRegisterObj.getService());
    }
}
