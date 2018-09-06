package com.zamanak.shamimsalamat.api.result;

import java.util.ArrayList;

/**
 * Created by zamanak on 6/12/2018.
 */

public class ResultGetWinnersArround {

    private String error;
    private String code;
    ArrayList<RecentWinners> result;

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<RecentWinners> getResult() {
        return result;
    }
}
