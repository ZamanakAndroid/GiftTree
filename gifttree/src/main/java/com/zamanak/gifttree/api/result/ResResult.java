package com.zamanak.gifttree.api.result;

import java.io.Serializable;

/**
 * Created by zamanak on 6/11/2018.
 */

public class ResResult implements Serializable {
    private ResGift gift;
    private boolean success;

    public ResGift getGift() {
        return gift;
    }

    public boolean isSuccess() {
        return success;
    }
}
