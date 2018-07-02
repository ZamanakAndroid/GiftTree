package com.zamanak.gifttree.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.zamanak.gifttree.GiftTreeSDK;
import com.zamanak.gifttree.activity.TryChanceActivity;
import com.zamanak.gifttree.adapter.ChanceTreeAdapter;
import com.zamanak.gifttree.api.ApiErrorCB;
import com.zamanak.gifttree.api.ApiSuccessCB;
import com.zamanak.gifttree.api.BaseApi;
import com.zamanak.gifttree.api.Urls;
import com.zamanak.gifttree.api.request.RequestGetPageDetail;
import com.zamanak.gifttree.api.request.RequestGetWinnersArround;
import com.zamanak.gifttree.api.result.RecentWinners;
import com.zamanak.gifttree.api.result.ResultDetailRes;
import com.zamanak.gifttree.api.result.ResultGetDetailRes;
import com.zamanak.gifttree.api.result.ResultGetWinnersArround;
import com.zamanak.gifttree.api.result.SModel;
import com.zamanak.gifttree.custom.CustomProgressDialog;
import com.zamanak.gifttree.custom.DividerItemDecoration;
import com.zamanak.gifttree.dialog.OnceTryChanceDialog;
import com.zamanak.gifttree.events.BooleanEventBus;
import com.zamanak.gifttree.interfaces.OnceTryChanceListener;
import com.zamanak.gifttree.utils.Constants;
import com.zamanak.gifttree.utils.FirebaseLogUtils;
import com.zamanak.gifttree.utils.LocationUtils;
import com.zamanak.gifttreelibrary.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PirFazel on 6/13/2017.
 */

public class ChanceTreeFragment extends BaseFragmentNew implements View.OnClickListener {

    private RecyclerView rvWinners;
    private AppCompatButton btnTryChance, btnInviteFriend;
    private ProgressBar progress_bar_list, pbarFirst;
    private NestedScrollView nestedScrollView;
    private AppCompatImageView ivHeaderPic;
    private AppCompatTextView tvEmptyWinnersArround;
    private boolean isSubscribed = false;
    private boolean isCanShake = false;
    private ResultGetDetailRes res;
    private double longitude, latitude;
    private String link;
    private LatLng currentPoint = null;


    @Override
    protected void setListener() {
        btnTryChance.setOnClickListener(this);
        btnInviteFriend.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        if (mActivity.isNetworkConnected()) {
            return R.layout.fragment_chance_tree;
        } else {
            return R.layout.fragment_no_connection;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mActivity.initCustomToolbar();
        if (mActivity.isNetworkConnected()) {
            mActivity.initToolbar(mActivity.getString(R.string.chance_tree_tittle), 0, true);
            return;
        } else {
            mActivity.initToolbar(mActivity.getString(R.string.chance_tree_tittle), 0, true);
            return;
        }

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        FirebaseLogUtils.logEvent(mActivity, "amazingTree_visited");

        ivHeaderPic = getViewById(R.id.iv_header_pic);
        btnInviteFriend = getViewById(R.id.btn_invite_friend);
        btnTryChance = getViewById(R.id.btn_try_chance);
        progress_bar_list = getViewById(R.id.progress_bar_list);
        pbarFirst = getViewById(R.id.progress_bar_first);
        nestedScrollView = getViewById(R.id.nested_scroll_view);
        rvWinners = getViewById(R.id.rv_winners);
        tvEmptyWinnersArround = getViewById(R.id.tv_empty_winner_list);

        getBundle();
        CustomProgressDialog.showProgressDialog(pbarFirst);
        callGetDetailApi();
    }

    private void getBundle() {
        HashMap<String, Double> long_lat = LocationUtils.getLatLong(mActivity);
        if (long_lat != null && long_lat.size() > 0) {
            currentPoint = new LatLng(long_lat.get("latitude"), long_lat.get("longitude"));
            longitude = currentPoint.longitude;
            latitude = currentPoint.latitude;
        }
    }

    private void callGetDetailApi() {

        new RequestGetPageDetail(mActivity, new ApiSuccessCB() {
            @Override
            public void onSuccess(BaseApi service) {
                res = new Gson().fromJson(service.resJson.toString(), ResultGetDetailRes.class);
                if ("Ok".equals(res.getCode()) && res.getResult() != null) {
                    initPage(res.getResult());
                }
            }
        }, new ApiErrorCB() {
            @Override
            public void onError(Exception e) {

            }
        }, GiftTreeSDK.getBaseApiKey(), Urls.GET_DETAIL).execute();
    }


    private void initPage(ResultDetailRes result) {
        try {
            if (result != null) {
                CustomProgressDialog.finishProgressDialog(pbarFirst);
                nestedScrollView.setVisibility(View.VISIBLE);
                Glide.with(mActivity).load(result.getImage()).into(ivHeaderPic);
                isSubscribed = result.isSubscribed();
                btnTryChance.setText(result.getButtonText());
                isCanShake = result.isCanShake();
                link = result.getLink();
                if (!result.isSubscribed()) {
                    SModel vas = result.getsModel();
                    if (vas != null) {
                        //TODO Write VasDialogNew for your use
                        /*VasDialogNew.newInstance(vas.getService(),
                                vas.getUrl(), vas.getImageUrl(),
                                vas.getTitle(), vas.getDetail(),
                                vas.getbText())
                                .show(getFragmentManager());*/
                    }
                }
                CustomProgressDialog.showProgressDialog(progress_bar_list);
                callWinnersArroundApi();

            } else {
                CustomProgressDialog.finishProgressDialog(pbarFirst);
                Toast.makeText(mActivity, "اطلاعات صفحه قرعه کشی موجود نمی باشد", Toast.LENGTH_SHORT).show();
                //TODO creat a page with sad icon to show the page of chance not available
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callWinnersArroundApi() {
        //CustomProgressDialog.showProgressDialog(pbarLoading);
        final String queryUrl = GiftTreeSDK.getWinnersArround() + "?lat=" + latitude + "&long=" + longitude;

        new RequestGetWinnersArround(mActivity, new ApiSuccessCB() {
            @Override
            public void onSuccess(BaseApi service) {
                try {
                    ResultGetWinnersArround res = new Gson().fromJson(service.resJson.toString(), ResultGetWinnersArround.class);
                    if ("Ok".equals(res.getCode()) && res.getResult() != null) {
                        if (res.getResult().size() > 0) {
                            setRecentWinnersList(res.getResult());
                        } else {
                            CustomProgressDialog.finishProgressDialog(progress_bar_list);
                            tvEmptyWinnersArround.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new ApiErrorCB() {
            @Override
            public void onError(Exception e) {

            }
        }, GiftTreeSDK.getBaseApiKey(), queryUrl).execute();
    }

    private void setRecentWinnersList(ArrayList<RecentWinners> result) {
        CustomProgressDialog.finishProgressDialog(progress_bar_list);
        rvWinners.setVisibility(View.VISIBLE);
        rvWinners.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager llm = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvWinners.setLayoutManager(llm);
        rvWinners.setHasFixedSize(true);
        rvWinners.setNestedScrollingEnabled(false);
        ChanceTreeAdapter adapter = new ChanceTreeAdapter(mActivity, result);
        rvWinners.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == btnTryChance.getId()) {
            FirebaseLogUtils.logEvent(mActivity, "amazingTree_TryBTN");
            if (isCanShake) {
                intent = new Intent(mActivity, TryChanceActivity.class);
                if (isSubscribed) {
                    intent.putExtra("chanceType", "special");
                } else {
                    /**
                     * the page of TryChance is different for users who register in app and who ones not registered it.
                     */
                    intent.putExtra("chanceType", "ordinary");
                    intent.putExtra("sModel", res.getResult().getsModel());
                }
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                startActivity(intent);
            } else {
                //Toast.makeText(mActivity, "هر روز فقط یکبار میتوانید امتحان کنید، فردا منتظرتان هستیم", Toast.LENGTH_SHORT).show();
                final OnceTryChanceDialog dialog = new OnceTryChanceDialog(mActivity);
                FirebaseLogUtils.logEvent(mActivity, "amazingTree_chanceFinished");
                dialog.setListener(new OnceTryChanceListener() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }


        } else if (v.getId() == btnInviteFriend.getId()) {
            FirebaseLogUtils.logEvent(mActivity, "amazingTree_inviteBTN");
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, link);
            startActivity(Intent.createChooser(i, mActivity.getString(R.string.invite_friends)));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
       if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
       }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //TODO when use VasDialog you should this codes
        /*if (VasDialog.getFragment() != null) {
            VasDialog.getFragment().dismissDialog();
        }*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BooleanEventBus event) {
        isCanShake = event.isCanShake();
    }


}
