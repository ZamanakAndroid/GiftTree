package com.zamanak.gifttree.interfaces;

import android.support.annotation.StringRes;

/**
 * Created by PIRI on 11/5/2017.
 */

public interface ViewBehavior {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
