
package com.zamanak.shamimsalamat.utils;

import android.content.Context;
import android.widget.Toast;


public abstract class ToastUtils extends Toast {

    public ToastUtils(Context context) {
        super(context);
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msg) {
        Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show();
    }
}

