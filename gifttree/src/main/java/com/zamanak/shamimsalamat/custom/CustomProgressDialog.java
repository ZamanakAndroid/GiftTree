package com.zamanak.shamimsalamat.custom;

import android.graphics.Color;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by a.Raghibdoust on 11/6/2017.
 */

public class CustomProgressDialog {

    public static void showProgressDialog(ProgressBar pbLoading) {
        if (pbLoading != null) pbLoading.setVisibility(VISIBLE);
    }

    public static void finishProgressDialog(ProgressBar pbLoading) {
        if (pbLoading != null) pbLoading.setVisibility(GONE);
    }

    public static void showProgressDialog(ProgressBar pbLoading, Button btn) {
        pbLoading.setVisibility(VISIBLE);
        btn.setEnabled(false);
        btn.setBackgroundColor(Color.parseColor("#9b9b9b"));

    }

    public static void finishProgressDialog(ProgressBar pbLoading, Button btn) {
        pbLoading.setVisibility(GONE);
        btn.setEnabled(true);
        btn.setBackgroundColor(Color.parseColor("#303F9F"));
    }

    public static void finishProgressDialogPass(ProgressBar pbLoading, Button btn) {
        pbLoading.setVisibility(GONE);
        btn.setEnabled(true);
        btn.setBackgroundColor(Color.parseColor("#21ba45"));
    }

    public static void showProgressDialog(ProgressBar pbLoading, Button btn1, Button btn2, RelativeLayout rlLoading) {
        pbLoading.setVisibility(VISIBLE);
        btn1.setEnabled(false);
        btn1.setBackgroundColor(Color.parseColor("#9b9b9b"));
        btn2.setEnabled(false);
        btn2.setBackgroundColor(Color.parseColor("#9b9b9b"));
        rlLoading.setBackgroundColor(Color.parseColor("#769e9e9e"));

    }

    public static void showProgressDialog(ProgressBar pbLoading, Button btn1, RelativeLayout rlLoading) {
        pbLoading.setVisibility(VISIBLE);
        btn1.setEnabled(false);
        btn1.setBackgroundColor(Color.parseColor("#9b9b9b"));
        rlLoading.setBackgroundColor(Color.parseColor("#769e9e9e"));

    }

    public static void finishProgressDialog(ProgressBar pbLoading, Button btn1, Button btn2, RelativeLayout rlLoading) {
        pbLoading.setVisibility(GONE);
        btn1.setEnabled(true);
        btn1.setBackgroundColor(Color.parseColor("#303F9F"));
        btn2.setEnabled(true);
        btn2.setBackgroundColor(Color.parseColor("#21ba45"));
        rlLoading.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public static void finishProgressDialog(ProgressBar pbLoading, Button btn1, RelativeLayout rlLoading) {
        pbLoading.setVisibility(GONE);
        btn1.setEnabled(true);
        btn1.setBackgroundColor(Color.parseColor("#303F9F"));
        rlLoading.setBackgroundColor(Color.parseColor("#ffffff"));
    }

}
