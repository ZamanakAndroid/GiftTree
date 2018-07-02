package com.zamanak.gifttree.utils;

import android.os.Environment;

import com.zamanak.gifttree.controller.App;
import com.zamanak.gifttreelibrary.R;

import java.io.File;

/**
 * Created by PirFazel on 11/21/2016.
 */

public class Constants {

    /*Layout directions*/
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;

    /*API Key*/


    /*Arrays*/
    public static String[] searchZone = new String[]{
            App.getAppContext().getString(R.string.five_hundred),
            App.getAppContext().getString(R.string.one_km),
            App.getAppContext().getString(R.string.two_km),
            App.getAppContext().getString(R.string.five_km),
            App.getAppContext().getString(R.string.ten_km)
    };

    public static String[] workInterval = new String[]{
            App.getAppContext().getString(R.string.all),
            App.getAppContext().getString(R.string.daily),
            App.getAppContext().getString(R.string.full_time),
            App.getAppContext().getString(R.string.morning),
            App.getAppContext().getString(R.string.afternoon)};
    public static String[] healthItemType = new String[]{
            App.getAppContext().getString(R.string.all),
            App.getAppContext().getString(R.string._private),
            App.getAppContext().getString(R.string.hospital),
            App.getAppContext().getString(R.string.educative),
            App.getAppContext().getString(R.string.clinic),
            App.getAppContext().getString(R.string.non_private),
            App.getAppContext().getString(R.string.sepah),
            App.getAppContext().getString(R.string.army),
            App.getAppContext().getString(R.string.naja),
            App.getAppContext().getString(R.string.red_crescent),
            App.getAppContext().getString(R.string.tamin),
            App.getAppContext().getString(R.string.organization)
    };
    public static String[] workIntervalValue = new String[]{
            "00", "11", "12", "13", "14"};
    public static String[] healthItemTypeValue = new String[]{
            "00", "21", "22", "23", "24", "25", "31", "32", "33", "34", "35", "36"
    };
    public static int[] searchZoneValue = new int[]{
            500, 1000, 2000, 5000, 10000};
    public static int[] cameraZoneValue = new int[]{
            15, 14, 13, 12, 11};
    public static String[] bloodType = new String[]{
            App.getAppContext().getString(R.string.select),
            App.getAppContext().getString(R.string.negative_o),
            App.getAppContext().getString(R.string.positive_o),
            App.getAppContext().getString(R.string.negative_a),
            App.getAppContext().getString(R.string.positive_a),
            App.getAppContext().getString(R.string.negative_b),
            App.getAppContext().getString(R.string.positive_b),
            App.getAppContext().getString(R.string.negative_ab),
            App.getAppContext().getString(R.string.positive_ab),
    };

    /*Key*/
    public static final String PHARMACY_CODE = "pharmacyCode";
    public static final String PHARMACY_NAME = "pharmacyName";
    public static final String PRESCRIPTION_CODE = "prescriptionCode";
    public static final String TRACKING_CODE = "trackingCode";
    public static final String ID = "id";
    public static final String BASE64 = "b64";
    public static final String CONTACT_PHARMACY_MODEL = "contactPharmacyModel";
    public static final String CONTACT_PHARMACY = "contactPharmacy";
    public static final String HAS_PROFILE = "hasProfile";
    public static final String BUNDLE = "BUNDLE";
    public static final String ARRAYLIST = "ARRAYLIST";
    public static final String SUBSCRIPTION = "subscription";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String PRESCRIPTION_KEY_OBJECT = "prescription_key_obj";
    public static final String BUNDLE_KEY = "BUNDLE";
    public static final String PROFILE_KEY_OBJECT = "profile_key_obj";
    public static final String PHONE = "phone";
    public static final String NOT_REGISTERED = "not_registered";
    public static final String ERROR = "error";
    public static final String ERROR_MSG = "errorMsg";
    public static final String PRODUCT = "product";
    public static final String RESULT = "result";
    public static final String PARTY_TYPE = "PartyType";
    public static final String PARTY_TYPE2 = "General";
    public static final String MESSAGE = "message";

    /*Activities*/
    public static final String ABOUT_ACTIVITY = "AboutActivity";
    public static final String ENTRY_ACTIVITY = "EntryActivity";
    public static final String HEALTH_DOCUMENT_ACTIVITY = "HealthDocActivity";
    public static final String HOME_ACTIVITY = "HomeActivity";
    public static final String MAP_ACTIVITY = "MapActivity";
    public static final String MY_REQUEST_ACTIVITY = "RequestsActivity";
    public static final String PRESCRIPTION_FINDER_ACTIVITY = "PrescriptionFinderActivity";
    public static final String PROFILE_ACTIVITY = "ProfileActivity";
    public static final String REPORT_ACTIVITY = "ReportActivity";
    public static final String SPLASH_ACTIVITY = "SplashActivity";
    public static final String INTRO_ACTIVITY = "SplashActivity";
    public static final String HEALTH_FINDER_ACTIVITY = "SplashActivity";
    public static final String PHR_ACTIVITY = "PhrActivity";

    /*Requests Code*/
    public static final int REQUEST_CALL_PHONE = 1;
    public static final int REQUEST_LOCATION = 2;

    /*Permissions*/
    public static final String CAMERA_PERMISSION = "android.permission.CAMERA";
    public static final String SEND_SMS_PERMISSION = "android.permission.SEND_SMS";
    public static final String RECEIVE_SMS_PERMISSION = "android.permission.RECEIVE_SMS";
    public static final String WRITE_EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /*Header*/
    public static final String HEADER_API_KEY = "X-Zamanak-Api-Key";
    public static final String HEADER_TOKEN = "X-Zamanak-Session-Token";

    /*Type Item*/
    public static final int HEALTH_DOC_TYPE = 1;
    public static final int HOME_TYPE = 2;
    public static final String HEALTH_DOCUMENT_TYPE = "health_document_type";
    public static final String HEALTH_DOCUMENT_PHR_TYPE = "health_document_phr_type";
    public static final String HEALTH_DOCUMENT_PRESCRIPTION_TYPE = "health_document_prescription_type";

    /*Market Type*/
    public static final String MARKET_BAZAAR = "bazaar";
    public static final String MARKET_PLAY_STORE = "play_store";

    // Register Notification
    public static final String OS = "android";
    public static final String TOKEN = "token";
    public static final String APP_NAME = "ShamimSalamat";

    //Permissions
    public static final int PERMISSION_READ_SMS = 9;
    public static final int PERMISSION_RECEIVE_SMS = 10;
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 11;
    public static final int PERMISSION_LOCATION = 12;

    public static File storage_Dir_Image = new File(Environment.getExternalStoragePublicDirectory(Environment.getRootDirectory().getParent()), "/shamim/Images");
    public static File storage_Dir_File = new File(Environment.getExternalStoragePublicDirectory(Environment.getRootDirectory().getParent()), "/shamim/Files");

    public static void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

}
