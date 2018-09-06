package com.zamanak.shamimsalamat;

import net.jhoobin.jhub.CharkhoneSdkApp;

public class CharkhooneApp extends CharkhoneSdkApp {

    @Override
    public String[] getSecrets() {
        return getResources().getStringArray(R.array.secrets);
    }
}
