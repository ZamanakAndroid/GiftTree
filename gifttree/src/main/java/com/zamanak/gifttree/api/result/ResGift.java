package com.zamanak.gifttree.api.result;

import java.io.Serializable;

/**
 * Created by zamanak on 6/11/2018.
 */

public class ResGift implements Serializable{

    private String title;
    private String image;
    private String description;
    private String closureType;
    private String closureText;
    private String closureMedia;
    private String closureButtonText;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getClosureType() {
        return closureType;
    }

    public String getClosureText() {
        return closureText;
    }

    public String getClosureMedia() {
        return closureMedia;
    }

    public String getImage() {
        return image;
    }

    public String getClosureButtonText() {
        return closureButtonText;
    }
}
