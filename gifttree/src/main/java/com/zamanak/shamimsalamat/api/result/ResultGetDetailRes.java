package com.zamanak.shamimsalamat.api.result;

/**
 * Created by zamanak on 6/12/2018.
 */

public class ResultGetDetailRes {

    private String error;
    private String code;
    private ResultDetailRes result;

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    public ResultDetailRes getResult() {
        return result;
    }
}
