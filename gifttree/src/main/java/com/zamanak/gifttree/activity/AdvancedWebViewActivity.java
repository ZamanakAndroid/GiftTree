package com.zamanak.gifttree.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.zamanak.gifttree.custom.CustomProgressDialog;
import com.zamanak.gifttree.utils.Utility;
import com.zamanak.gifttreelibrary.R;

import im.delight.android.webview.AdvancedWebView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by zamanak on 6/8/2018.
 */

public class AdvancedWebViewActivity extends AppCompatActivity implements AdvancedWebView.Listener,View.OnClickListener {

    private AdvancedWebView mWebView;
    private TextView web_tv;
    private ProgressBar pb;
    private AppCompatImageView ivBack,ivShare;
    private String title, link, shareText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_advanced);

        web_tv = findViewById(R.id.web_tv);
        pb = findViewById(R.id.pb);
        ivBack = findViewById(R.id.iv_back);
        ivShare = findViewById(R.id.iv_share);

        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);

        mWebView = findViewById(R.id.advanced_web_view);
        mWebView.setListener(this, this);
        mWebView.setGeolocationEnabled(false);
        mWebView.setMixedContentAllowed(true);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
          CustomProgressDialog.showProgressDialog(pb);
            try {
                title = bundle.getString("title");
                link = bundle.getString("link");
                shareText = bundle.getString("shareText");
                web_tv.setText(title);
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        CustomProgressDialog.finishProgressDialog(pb);
                        mWebView.setVisibility(View.VISIBLE);
                    }

                });

                mWebView.setWebChromeClient(new WebChromeClient() {

                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                    }

                });
                mWebView.addHttpHeader("X-Requested-With", "");
                mWebView.loadUrl(link);
            } catch (NullPointerException npe) {
                Log.d("onCreate: ", npe.getMessage());
            }
        }else{
            Toast.makeText(this, "محتوا موجود نمی باشد", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ivBack.getId()) {
            finish();
        } else if (v.getId() == ivShare.getId()) {
            Utility.shareTextUrl(shareText + "\n" + "" + link, "", this);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}



