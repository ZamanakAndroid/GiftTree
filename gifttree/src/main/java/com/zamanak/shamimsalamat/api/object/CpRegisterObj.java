package com.zamanak.shamimsalamat.api.object;


public class CpRegisterObj {

    public CpRegisterObj(String code, String service) {
        this.code = code;
        this.service = service;
    }

    public String getCode() {
        return code;
    }

    public String getService() {
        return service;
    }

    private String code;
    private String service;
}
