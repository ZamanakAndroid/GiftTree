package com.zamanak.shamimsalamat.api.result;

import java.io.Serializable;

/**
 * Created by zamanak on 6/11/2018.
 */

public class ResultGetChanceRes implements Serializable {

    private String error;
    private String code;
    private ResResult result;

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    public ResResult getResult() {
        return result;
    }
}
