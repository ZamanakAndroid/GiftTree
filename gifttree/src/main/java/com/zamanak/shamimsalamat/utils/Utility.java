package com.zamanak.shamimsalamat.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.zamanak.shamimsalamat.activity.EntryActivity;
import com.zamanak.shamimsalamat.controller.App;
import com.zamanak.shamimsalamat.R;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by PirFazel on 11/21/2016.
 */

public final class Utility {

    public static boolean isMarshmallowOrAbove() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static void runActivity(Activity current, Class destination) {

        runActivity(current, destination, false);
    }

    public static void runActivity(Activity current, Class destination, boolean finish) {

        Intent intent = new Intent(current, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        current.startActivity(intent);
        current.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        if (finish) {
            current.finish();
        }
    }

    public static void runActivityWithBundle(Activity current, Class destination, Bundle bundle, boolean finish) {

        Intent intent = new Intent(current, destination);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        current.startActivity(intent);
        current.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        if (finish) {
            current.finish();
        }
    }

    public static void runActivityAndFinishAllActivities(Activity current, Class destination) {

        Intent intent = new Intent(current, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        current.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        current.startActivity(intent);
    }

    public static String getApplicationName(Context context) {

        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ?
                applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    @SuppressLint("DefaultLocale")
    public static String setCommaSeparatorToPrice(int price) {

        return String.format("%,d", price);
    }

    @SuppressLint("WrongConstant")
    public static void logout(Activity activity) {

        FirebaseLogUtils.logEvent(activity, "logout");
        SharedPreferences preferencesLogin =
                activity.getSharedPreferences(App.PREFS_LOGIN, 0);
        //SharedPreferences preferencesSms = activity.getSharedPreferences(AppDetailedRes.PREF_SMS, 0);
        preferencesLogin.edit().clear().apply();
        //preferencesSms.edit().clear().apply();
        Intent intent = new Intent(activity, EntryActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public static Uri getImageUri(Context context, Bitmap bitmap) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String fileName = "img_" + getCurrentDate().replaceAll("\\s", "");
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,
                    fileName, null);
            return Uri.parse(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getCurrentDate() {
        return DateFormat.getDateInstance().format(new Date());
    }

    public static void showExitAlert(final Activity activity) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        //alertDialog.setMessage(FontUtils.textWithFont(activity, activity.getString(R.string.r_u_sure_to_quit)));
        //alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, FontUtils.textWithFont(activity, activity.getString(R.string.ok)),
               /* new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });*/
        //alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, FontUtils.textWithFont(activity, activity.getString(R.string.no)),
               /* new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });*/
        alertDialog.show();
    }

    public static String getVersionName(Context context) {

        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            return info.versionName;
        } else {
            return "0";
        }
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void shareTextUrl(String title, String text,Context context) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, text);

        context.startActivity(Intent.createChooser(share, "Share link!"));
    }
}
