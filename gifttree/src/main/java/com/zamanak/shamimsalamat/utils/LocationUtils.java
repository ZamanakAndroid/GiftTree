package com.zamanak.shamimsalamat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.zamanak.shamimsalamat.R;

import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.zamanak.shamimsalamat.utils.Constants.LATITUDE;
import static com.zamanak.shamimsalamat.utils.Constants.LONGITUDE;

/**
 * Created by PirFazel on 5/24/2017.
 */

public class LocationUtils {

    public static HashMap<String, Double> getLatLong(Context context) {
        try {
            HashMap<String, Double> hashMap = new HashMap<>();
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                hashMap.put(LONGITUDE, longitude);
                hashMap.put(LATITUDE, latitude);
            }
            return hashMap;
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.e("lat_longSecurityExp", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getLatLongExp", e.getMessage());
        }
        return null;
    }


    public static String getAddress(Context context, double lat, double lng) {
        String address = null, locality = null, subLocality = null, state = null, country = null, postalCode = null, knownName = null;
        CustomProgressDialog customProgressDialog = new CustomProgressDialog(context);
        customProgressDialog.showProgressDialog(context.getString(R.string.plz_wait));
        Locale loc = new Locale("ar");
        Geocoder geocoder = new Geocoder(context, loc);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                locality = addresses.get(0).getLocality();
                subLocality = addresses.get(0).getSubLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postalCode = addresses.get(0).getPostalCode();
                knownName = addresses.get(0).getFeatureName();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        customProgressDialog.getProgressDialog().dismiss();
        if (addresses != null){
            if (subLocality!=null){
                return state+" "+locality+" "+subLocality+" "+knownName;
            }else{
                return state+" "+locality+" "+knownName;
            }
        }

        else return "_";
    }


    public static Map<String, String> loadMap(Context context) {
        Map<String, String> outputMap = new HashMap<String, String>();
        SharedPreferences pSharedPref = context.getSharedPreferences("Sections", Context.MODE_PRIVATE);
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString("map", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }
}
