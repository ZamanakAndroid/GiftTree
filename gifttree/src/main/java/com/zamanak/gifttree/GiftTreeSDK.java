package com.zamanak.gifttree;

import android.app.Activity;
import android.content.Intent;

import com.zamanak.gifttree.activity.ScoreActivity;

public class GiftTreeSDK {

    public static final String BASE_URL = "http://avasdp.shamimsalamat.ir/api/v2";
    public static final String GET_DETAIL = "/gift-tree/get-detail";
    public static final String WINNERS_ARROUND = "/gift-tree/winners-around";
    public static final String GET_CHANCE_RESULT = "/gift-tree/get-chance-result";

    private static  String BASE_API_KEY ;
    private static String TOKEN ;

    private static int colorAccent;
    private static int colorPrimary;
    private static int colorPrimaryDark;

    private static volatile GiftTreeSDK sdk = new GiftTreeSDK();

    public GiftTreeSDK() {

    }

    public static GiftTreeSDK sharedLandOfHealth(){
        return sdk;
    }

    public static void setTOKEN(String TOKEN) {
        GiftTreeSDK.TOKEN = TOKEN;
    }

    public void setApiAppKey(String apiAppKey) {
        this.BASE_API_KEY = apiAppKey;
    }

    public static String getBaseApiKey() {
        return BASE_API_KEY;
    }

    public static String getTOKEN() {
        return TOKEN;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getGetDetail() {
        return GET_DETAIL;
    }

    public static String getWinnersArround() {
        return WINNERS_ARROUND;
    }

    public static String getGetChanceResult() {
        return GET_CHANCE_RESULT;
    }

    public static void setColorAccent(int colorAccent) {
        GiftTreeSDK.colorAccent = colorAccent;
    }

    public static void setColorPrimary(int colorPrimary) {
        GiftTreeSDK.colorPrimary = colorPrimary;
    }

    public static void setColorPrimaryDark(int colorPrimaryDark) {
        GiftTreeSDK.colorPrimaryDark = colorPrimaryDark;
    }

    public static int getColorAccent() {
        return colorAccent;
    }

    public static int getColorPrimary() {
        return colorPrimary;
    }

    public static int getColorPrimaryDark() {
        return colorPrimaryDark;
    }

    public void startScoreActivity(Activity ctx, String token, String baseApiKey){
        setTOKEN(token);
        setApiAppKey(baseApiKey);
        Intent intent = new Intent(ctx , ScoreActivity.class);
        ctx.startActivity(intent);
    }
}