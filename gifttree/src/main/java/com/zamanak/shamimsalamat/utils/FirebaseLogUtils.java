package com.zamanak.shamimsalamat.utils;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by PIRI on 11/14/2017.
 */

public final class FirebaseLogUtils {

    public static final int PERMISSION_REFUSED = 0;
    public static final int PERMISSION_AUTHORIZED = 1;

    public static void logEvent(Context context, String s) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        firebaseAnalytics.logEvent(s, null);
    }

    public static void logHealthLandNameEvent(Context context, String s, String name) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        firebaseAnalytics.logEvent(s, bundle);
    }

    public static void logHealthLandCatEvent(Context context, String s, String cat) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString("category", cat);
        firebaseAnalytics.logEvent(s, bundle);
    }

    public static void logHealthLandAppEvent(Context context, String s, String id) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        firebaseAnalytics.logEvent(s, bundle);
    }

    public static void logTypeEvent(Context context, String s, String orgType) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString("type", orgType);
        firebaseAnalytics.logEvent(s, bundle);
    }

    public static void logTutorialCompleted(Context context, int swipe_count) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putInt("swipe_count", swipe_count);
        firebaseAnalytics.logEvent("tutorial_complete", bundle);
    }

    public static void logBooleanEvent(Context context, String s, boolean bool) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle b = new Bundle();
        b.putBoolean("success", bool);
        firebaseAnalytics.logEvent(s, b);
    }

    public static void logPermissionEvent(Context context, String s, boolean bool) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle b = new Bundle();
        b.putBoolean("access", bool);
        firebaseAnalytics.logEvent(s, b);
    }

    public static void logResultEvent(Context context, String s, int hasResult) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle b = new Bundle();
        b.putInt("result", hasResult);
        firebaseAnalytics.logEvent(s, b);
    }

    public static void logPermissionEvent(Context context, String s, int isAuthorized) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle b = new Bundle();
        b.putInt("access", isAuthorized);
        firebaseAnalytics.logEvent(s, b);
    }

    public static void logPermissionEvent(Context context, String s, String permission) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle b = new Bundle();
        b.putString("access", permission);
        firebaseAnalytics.logEvent(s, b);
    }

    public static void logSendPrescriptionEvent(Context context, boolean bool) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle b = new Bundle();
        b.putBoolean("result", bool);
        firebaseAnalytics.logEvent("noskheyabNewRequest_requestRes", b);
    }

    public static void logIdEvent(Context context, String s, long id) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        firebaseAnalytics.logEvent(s, bundle);
    }

    public static void logSendCodeEvent(Context context, String phone) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        firebaseAnalytics.logEvent("login_otp", bundle);
    }

    public static void logVasPageEvent(Context context, String type, String s) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        firebaseAnalytics.logEvent(s, bundle);
    }

    public static void logVasPageSubscriptionEvent(Context context, String type,
                                                   String s, int success) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putInt("success", success);
        bundle.putString("type", type);
        firebaseAnalytics.logEvent(s, bundle);
    }

    public static void logSalamatYabSelectionEvent(Context context, String type) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        firebaseAnalytics.logEvent("salamatyab_typeSelected", bundle);
    }

    public static void logRateEvent(Context context, String s, int rate) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putInt("rate", rate);
        firebaseAnalytics.logEvent(s, bundle);
    }

}
