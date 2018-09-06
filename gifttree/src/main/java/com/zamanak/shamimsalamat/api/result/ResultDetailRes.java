package com.zamanak.shamimsalamat.api.result;

import java.util.ArrayList;

/**
 * Created by zamanak on 6/12/2018.
 */

public class ResultDetailRes {

    private String image;
    private String mainText;
    private ArrayList<ScoresList> scores;
    private String totalScore;
    private String link;
    private boolean subscribed;
    private String buttonText;
    private boolean canShake;
    private SModel sModel;

    public String getImage() {
        return image;
    }

    public String getMainText() {
        return mainText;
    }

    public ArrayList<ScoresList> getScores() {
        return scores;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public String getButtonText() {
        return buttonText;
    }

    public boolean isCanShake() {
        return canShake;
    }

    public String getLink() {
        return link;
    }

    public SModel getsModel() {
        return sModel;
    }
}
