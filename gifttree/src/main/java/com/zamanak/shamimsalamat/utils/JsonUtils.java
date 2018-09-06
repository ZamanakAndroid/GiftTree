package com.zamanak.shamimsalamat.utils;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PirFazel on 2/4/2017.
 */

public abstract class JsonUtils {
    public static JSONObject createAnswer(double longitude, double latitude, double radius, String activityType) {
        JSONObject answer = new JSONObject();
        try {
            answer.put("Longitude", longitude);
            answer.put("Latitude", latitude);
            answer.put("Radius", radius);
            answer.put("ActivityType", activityType);
            Log.v("answer", answer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answer;
    }
    public static JSONObject createAnswerToGetHealthItems(double latitude, double longitude, double radius, String activityType, String orgType) {
        JSONObject answer = new JSONObject();
        try {
            answer.put("longitude", String.valueOf(longitude));
            answer.put("latitude", String.valueOf(latitude));
            answer.put("radius", radius);
            answer.put("activityType", activityType);
            answer.put("partyType", orgType);
            Log.v("answer", answer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answer;
    }
    public static JSONObject createAnswer(double longitude, double latitude, double radius, String activityType, String orgType) {
        JSONObject answer = new JSONObject();
        try {
            answer.put("longitude", longitude);
            answer.put("latitude", latitude);
            answer.put("radius", radius);
            answer.put("activityType", activityType);
            answer.put("partyType", orgType);
            Log.v("answer", answer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public static JsonElement convertStringToJsonObj(String jsonAsString) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(jsonAsString);
    }

    public static String trimMessage(String json, String key) {
        String trimmedString;
        JSONObject obj;
        try {
            obj = new JSONObject(json);
            trimmedString = obj.getJSONObject("error").getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trimmedString;

    }
}
