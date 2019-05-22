package com.zamanak.gifttree.vas;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zamanak.gifttree.api.ApiErrorCB;
import com.zamanak.gifttree.api.ApiSuccessCB;
import com.zamanak.gifttree.api.BaseApi;
import com.zamanak.gifttree.api.object.CpRegisterObj;
import com.zamanak.gifttree.api.request.RequestActivateService;
import com.zamanak.gifttree.api.request.RequestCpRegister;
import com.zamanak.gifttree.dialog.BaseDialogNew;
import com.zamanak.gifttree.utils.FirebaseLogUtils;
import com.zamanak.gifttree.utils.ToastUtils;
import com.zamanak.gifttreelibrary.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PIRI on 10/29/2017.
 */


public class VasDialogNew extends BaseDialogNew implements View.OnClickListener {

    private static final String TAG = "VasDialog";
    @SuppressLint("StaticFieldLeak")
    static VasDialogNew fragment;
    private String serviceType;
    private String bText;
    private String endPoint;
    private String image;
    private String title;
    private String description;
    private String appName;

    private ImageView image_view;
    private TextView title_view;
    private ImageView closeDialog;
    private TextView description_view;
    private Button regBtn;
    private LinearLayout sendCodeLayout;
    private LinearLayout regLayout;
    private Button sendBtn;
    private EditText regCodeEditText;
    private ScrollView scrollView;

    public static VasDialogNew newInstance(String serviceType, String endPoint,
                                           String image, String title, String description,
                                           String bText, String appName) {

        fragment = new VasDialogNew();
        Bundle bundle = new Bundle();
        bundle.putString("endPoint", endPoint);
        bundle.putString("bText", bText);
        bundle.putString("image", image);
        bundle.putString("serviceType", serviceType);
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putString("appName", appName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {

        super.onActivityCreated(arg0);
        try {
            getDialog().getWindow().getAttributes().
                    windowAnimations = R.style.DialogAnimation;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setUp(View view) {

        image_view = getViewById(R.id.image);
        title_view = getViewById(R.id.title);
        closeDialog = getViewById(R.id.closeDialog);
        description_view = getViewById(R.id.description);
        regBtn = getViewById(R.id.okBtnLib);
        sendCodeLayout = getViewById(R.id.sendCodeLayout);
        regLayout = getViewById(R.id.regLayout);
        sendBtn = getViewById(R.id.sendBtn);
        regCodeEditText = getViewById(R.id.regCodeEditText);
        scrollView = getViewById(R.id.scrollView);

        closeDialog.setOnClickListener(VasDialogNew.this);
        regBtn.setOnClickListener(VasDialogNew.this);
        sendBtn.setOnClickListener(VasDialogNew.this);

        try {
            regBtn.setText(bText);
            title_view.setText(title);
            description_view.setText(description);
            Glide.with(mActivity)
                    .load(image)
                    .into(image_view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        getBundle();
        //FirebaseLogUtils.logVasPageEvent(mActivity, "vasPage_visited", serviceType);
        FirebaseLogUtils.logEvent(mActivity, "vasPage_visited");
        return super.onCreateDialog(savedInstanceState);
    }

    private void getBundle() {
        try {
            Bundle bundle = getArguments();
            endPoint = bundle.getString("endPoint");
            image = bundle.getString("image");
            title = bundle.getString("title");
            description = bundle.getString("description");
            appName = bundle.getString("appName");
            if ("zaer".equals(appName)) {
//                bText = "ارتقا درخت شانس";
                bText = bundle.getString("bText");

            } else if ("shamim".equals(appName)) {
                bText = bundle.getString("bText");
            }
            serviceType = bundle.getString("serviceType");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public static VasDialogNew getFragment() {
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContentView = inflater.inflate(R.layout.dialog_custom_lib, container, false);
        try {
            getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.x = 200;
        getDialog().getWindow().setAttributes(p);
        return mContentView;
    }

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        super.show(fragmentManager, tag);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //dismissAction();
    }

    public void dismissDialog() {
        super.dismiss();
        // dismissAction();
    }

    private void dismissAction() {
        mActivity.finish();
    }

    @Override
    public void onClick(View v) {
        if (v == closeDialog) {
            FirebaseLogUtils.logEvent(mActivity, "vasPage_dismissed");
            //FirebaseLogUtils.logVasPageEvent(mActivity, "vasPage_dismissed", serviceType);
            dismissDialog();
        } else if (v == regBtn) {
            register();
        } else if (v == sendBtn) {
            if (regCodeEditText.getText().toString().isEmpty()) {
                regCodeEditText.setError(mActivity.getString(R.string.reg_field_is_empty));
            } else if (regCodeEditText.getText().toString().length() != 4) {
                regCodeEditText.setError(
                        mActivity.getString(R.string.reg_field_contains_four_char));
            } else {
                cpRegister();
            }
        }
    }

    private void register() {
        FirebaseLogUtils.logVasPageSubscriptionEvent(mActivity, "vasPage_subscribe", serviceType, 1);
        new RequestActivateService(mActivity, endPoint, new ApiSuccessCB() {
            @Override
            public void onSuccess(BaseApi service) {
                Log.v("ActivateService", service.resJson.toString());
                try {
                    if ("zaer".equals(appName)) {
                        JSONObject obj = service.resJson.getJSONObject("result");
                        if (obj != null) {
                            updateVisibility();
                        }

                    } else if ("shamim".equals(appName)) {
                        if (service.resJson.getBoolean("result")) {
                        /*FirebaseLogUtils.logVasPageSubscriptionEvent(mActivity,
                                "vasPage_subscribe", serviceType, 1);*/
                            dismissDialog();
                        } else {
                       /* FirebaseLogUtils.logVasPageSubscriptionEvent(mActivity,
                                "vasPage_subscribe", serviceType, 0);*/
                            updateVisibility();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ApiErrorCB() {
            @Override
            public void onError(Exception e) {
                Log.e("ActivateService", e.getMessage());
                dismissDialog();
            }
        }, appName).execute();
    }

    private void updateVisibility() {

        regLayout.setVisibility(View.GONE);
        sendCodeLayout.setVisibility(View.VISIBLE);

        //TODO
        title_view.setVisibility(View.GONE);
        description_view.setVisibility(View.GONE);
        image_view.setVisibility(View.GONE);
        scrollToEditTextLayout();
    }

    private void scrollToEditTextLayout() {
        try {
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cpRegister() {

        new RequestCpRegister(mActivity, new ApiSuccessCB() {
            @Override
            public void onSuccess(BaseApi service) {
                Log.v("cpRegister", service.resJson.toString());
                try {
                    if (!service.resJson.get("error").equals(null)) {
                        FirebaseLogUtils.logVasPageSubscriptionEvent(mActivity,
                                "vasPage_subscribeSendCodeResult", serviceType, 0);
                        //ToastUtils.showToast(mActivity, service.resJson.getString("error"));
                    } else {
                        if (service.resJson.getBoolean("result")) {
                            FirebaseLogUtils.logVasPageSubscriptionEvent(mActivity,
                                    "vasPage_subscribeSendCodeResult",
                                    serviceType, 1);
                            ToastUtils.showToast(mActivity, mActivity.getString(
                                    R.string.operation_successful));
                            dismissDialog();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ApiErrorCB() {
            @Override
            public void onError(Exception e) {
                Log.e("cpRegister", e.getMessage());
            }
        }, new CpRegisterObj(regCodeEditText.getText().toString(), serviceType)).execute();
    }
}
