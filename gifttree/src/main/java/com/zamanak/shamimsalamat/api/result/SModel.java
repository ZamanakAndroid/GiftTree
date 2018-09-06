package com.zamanak.shamimsalamat.api.result;

import java.io.Serializable;

/**
 * Created by PIRI on 11/12/2017.
 */

public class SModel implements Serializable {

    private String bText;
    private String title;
    private String detail;
    private String imageUrl;
    private String url;
    private String service;
    private String op;


    public String getbText() {
        return bText;
    }

    public void setbText(String bText) {
        this.bText = bText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOp() {
        return op;
    }
}
