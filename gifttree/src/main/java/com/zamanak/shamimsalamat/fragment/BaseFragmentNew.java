package com.zamanak.shamimsalamat.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zamanak.shamimsalamat.activity.BaseActivityNew;
import com.zamanak.shamimsalamat.interfaces.ViewBehavior;
import com.zamanak.shamimsalamat.utils.CommonUtils;

/**
 * Created by PIRI on 4/15/2018.
 */

public abstract class BaseFragmentNew extends Fragment implements View.OnClickListener, ViewBehavior {

    protected View mContentView;
    protected ActionBar actionBar;
    protected String fragment_TAG;
    protected BaseActivityNew mActivity;

    private ProgressDialog mProgressDialog;

    protected abstract void setListener();

    protected abstract int getLayoutResource();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void processLogic(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            actionBar = mActivity.getSupportActionBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initToolbar() {
        if (actionBar != null) {
            actionBar.show();
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutResource() != 0) {
            mContentView = LayoutInflater.from(mActivity).inflate(getLayoutResource(), null);
        }
        if (mContentView != null) {
            try {
                initView(savedInstanceState);
                processLogic(savedInstanceState);
                setListener();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mContentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragment_TAG = this.getClass().getSimpleName();
        if (context instanceof BaseActivityNew) {
            BaseActivityNew activity = (BaseActivityNew) context;
            mActivity = (BaseActivityNew) context;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragment_TAG = this.getClass().getSimpleName();
        if (activity instanceof BaseActivityNew) {
            BaseActivityNew baseActivity = (BaseActivityNew) activity;
            mActivity = (BaseActivityNew) activity;
            baseActivity.onFragmentAttached();
        }
    }

    public void onClick(View v) {
        //TODO in subclasses!
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(@IdRes int id) {
        return (T) mContentView.findViewById(id);
    }

    protected int getFmBackStackEntryCount() {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        return fm.getBackStackEntryCount();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onUserVisible();
        } else {
            onUserInVisible();
        }
    }

    public void onUserVisible() {
    }

    public void onUserInVisible() {
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this.getContext());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity.openActivityOnTokenExpire();
        }
    }

    public interface Callback {
        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

}
