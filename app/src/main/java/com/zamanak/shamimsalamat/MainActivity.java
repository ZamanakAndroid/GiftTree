package com.zamanak.shamimsalamat;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.zamanak.shamimsalamat.activity.BaseActivityNew;

public class MainActivity extends BaseActivityNew {

    private AppCompatTextView tvHello;

    // shamim info
    //private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjg2OTQ4MCwidXNlcl9pZCI6ODY5NDgwLCJlbWFpbCI6IjA5Mzk3Mzg5ODQ3QEczZmU3clU0TnZYczJhUUVkM2ZlNHdUZi5jb20iLCJmb3JldmVyIjpmYWxzZSwiaXNzIjoiaHR0cDpcL1wvYXZhc2RwLnNoYW1pbXNhbGFtYXQuaXJcL2FwaVwvdjJcL2F1dGhcL3JlZ2lzdGVyLWFuZC1sb2dpbiIsImlhdCI6MTUzNjEyNzE4NSwiZXhwIjoxODUxNDg3MTg1LCJuYmYiOjE1MzYxMjcxODUsImp0aSI6IjNlM2E4NjA2OTg2OWNjZjEyYzU3MTQ0NmUxOGRkY2QwIn0.cmk5Tp3rnTemRxEBjTKM6AZmKDuxEi99xyqigsAEp5I";
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjczNiwidXNlcl9pZCI6NzM2LCJlbWFpbCI6Indvcmsuc2gxQGdtYWlsLmNvbSIsImZvcmV2ZXIiOmZhbHNlLCJpc3MiOiJodHRwOlwvXC9hdmFzZHAuc2hhbWltc2FsYW1hdC5pclwvYXBpXC92MlwvYXV0aFwvcmVnaXN0ZXItYW5kLWxvZ2luIiwiaWF0IjoxNTM2NzMwNTE1LCJleHAiOjE4NTIwOTA1MTUsIm5iZiI6MTUzNjczMDUxNSwianRpIjoiZGZlMzdiMDBlOTkxYjViMThjMWM3OTk0MDAzOWY0MTMifQ.Il2KV-6KLEIo6iRaUOGgDVP-AaVxZnyIG3_BHdU8ob8";
    private String baseApiKey = "b8c30f784ba9e0d1c384392cb930f6ad8139d512fec670666d94dbade03fa3f6";
    public static final String BASE_URL = "http://avasdp.shamimsalamat.ir/api/v2";
    private String userAPIKEY = "06b052875b5b7c015db7c63b8a53e5450a34d2714d645945e2ec96a0d0752a93";

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        tvHello = findViewById(R.id.tvHello);
        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftTreeSDK.sharedLandOfHealth().startScoreActivity(MainActivity.this,token,baseApiKey,BASE_URL,userAPIKEY,"shamim");

            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isRtl() {
        return false;
    }

    @Override
    protected boolean isPortrait() {
        return false;
    }

    @Override
    protected void setListener() {

    }

}
