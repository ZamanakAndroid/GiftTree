package com.zamanak.shamimsalamat.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zamanak.shamimsalamat.activity.BaseActivity;
import com.zamanak.shamimsalamat.interfaces.OnDismiss;
import com.zamanak.shamimsalamat.tools.BaseException;
import com.zamanak.shamimsalamat.tools.ErrorTags;
import com.zamanak.shamimsalamat.tools.HandleNoConnectionError;
import com.zamanak.shamimsalamat.utils.Constants;
import com.zamanak.shamimsalamat.utils.CustomProgressDialog;
import com.zamanak.shamimsalamat.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static com.zamanak.shamimsalamat.utils.JsonUtils.trimMessage;

public class BaseApi implements OnDismiss {

    protected Context context;
    protected JSONObject reqJson = new JSONObject();
    public JSONObject resJson;
    protected String phone;
    protected String token ="";
    protected String api_key;
    protected String url;
    protected String path;
    protected String code;
    protected ApiSuccessCB apiSuccessCB;
    protected ApiErrorCB apiErrorCB;
   // private SharedPreferences prefs;
    private boolean isPost;
    private boolean hasProgressDialog;
    private CustomProgressDialog customProgressDialog = null;
    private JsonObjectRequest jsObjRequest;
    protected String endPoint = null;

    public BaseApi(Context context, String url, String path,
                   ApiSuccessCB apiSuccessCB, ApiErrorCB apiErrorCB,
                   boolean isPost, boolean hasProgressDialog) {

        this.context = context;
        this.isPost = isPost;
        this.hasProgressDialog = hasProgressDialog;
        this.url = url;
        this.path = path;
        this.apiSuccessCB = apiSuccessCB;
        this.apiErrorCB = apiErrorCB;
        //TODO GET TOKEN AND SET HERE
        //this.token =LandOfHealthSDK.getTOKEN();
        instantiateProgressDialog();
        handleLocalData();
        handleToken();
    }

    private void handleToken() {
        if (token != null) {
            token = token.toLowerCase();
        }
    }

    private void handleLocalData() {

        /*prefs = context.getSharedPreferences(App.PREFS_LOGIN, MODE_PRIVATE);
        token = prefs.getString("token", null);*/
    }

    private void instantiateProgressDialog() {
        if (hasProgressDialog) {
            customProgressDialog = new CustomProgressDialog(context, this);
        }
    }

    protected void prepareRequest() throws JSONException {
    }

    public void execute() {

        try {
            if (isProgressEnable()) {
                customProgressDialog.showProgressDialogWithCancelButton();
            }
            prepareRequest();
            if (endPoint == null) {
                url += path;
            } else {
                url = endPoint;
            }
            Log.v("_url",url);
        } catch (JSONException e) {
            BaseException baseException = new BaseException(ErrorTags.ERROR_SEND_REQUEST,
                    e.getMessage(), context);
            baseException.setStackTrace(e.getStackTrace());
            throw baseException;
        }
        if (isPost) {
            sendRequest(Request.Method.POST);
            return;
        }
        sendRequest(Request.Method.GET);
    }

    protected Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            try {
                Log.e("VolleyError", error.toString());
                if (isProgressEnable()) {
                    customProgressDialog.getProgressDialog().dismiss();
                    int msg = HandleNoConnectionError.msgForVolleyError(error);
                    if (msg != 0) {
                        ((BaseActivity) context).onError(context.getString(msg));
                    }
                }
                handleErrorResponse(error);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void handleErrorResponse(VolleyError error) {


        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            String jsonObj = new String(response.data);
            String json = trimMessage(jsonObj, "message");
            Log.v("statusCode", "" + response.statusCode);
            if (json != null) {
                apiErrorCB.onError(new BaseException(response.statusCode + "", json, context));
            } else {
                Log.v("statusCode", "" + response.statusCode);
                if (isProgressEnable()) {
                    ((BaseActivity) context).onError(context.getString(R.string.plz_try_again));
                }
            }
        } else {
            apiErrorCB.onError(error);
        }
    }

    private void sendRequest(int method) {

        JSONObject jsonRequest = null;
        if (method == Request.Method.POST) {
            jsonRequest = reqJson;
        }
        jsObjRequest = new JsonObjectRequest(method, url, jsonRequest, onResponse, onError) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                BaseApi.this.parseNetworkResponse(response);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return BaseApi.this.getHeaders();
            }
        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000,
                2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    private void parseNetworkResponse(NetworkResponse response) {

        String token = response.headers.get("Authorization");
        if (token != null && token.length() > 8) {
            //prefs.edit().putString("token", token).apply();
        }
    }

    private Map<String, String> getHeaders() {

        Map<String, String> params = new HashMap<>();
      //  String token = prefs.getString("token", null);
        if (token != null) {
            params.put(Constants.HEADER_TOKEN, token);
            Log.v("session_token", token);
        }
        if (api_key != null) {
            params.put(Constants.HEADER_API_KEY, api_key);
            Log.v("api_key", api_key);
        }
        return params;
    }

    private Response.Listener onResponse = new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            if (isProgressEnable()) {
                customProgressDialog.getProgressDialog().dismiss();
            }
            resJson = response;
            successOnResponse();
        }
    };

    private void successOnResponse() {

        if (apiSuccessCB != null) {
            apiSuccessCB.onSuccess(this);
        }
    }

    private boolean isProgressEnable() {
        return hasProgressDialog && customProgressDialog != null;
    }

    @Override
    public void onDismiss() {
        jsObjRequest.cancel();
    }
}