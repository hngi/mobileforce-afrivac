package com.michael.afrivac.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String> getSinglePlace(JSONObject googlePlaceJSON){
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String NameOfPlace = "-NA-";
        String Vicinity = "-NA-";
        String Latitude = "";
        String Longitude = "";
        String reference = "";

        try {
            if(googlePlaceJSON.isNull("name")){
                NameOfPlace = googlePlaceJSON.getString("name");
            }
            if(googlePlaceJSON.isNull("vicinity")){
                Vicinity = googlePlaceJSON.getString("vicinity");
            }
            if(googlePlaceJSON.isNull("reference")){
                reference = googlePlaceJSON.getString("reference");
            }

            Latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            Longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");

            googlePlaceMap.put("name", NameOfPlace);
            googlePlaceMap.put("vicinity", Vicinity);
            googlePlaceMap.put("lat", Latitude);
            googlePlaceMap.put("lng", Longitude);
            googlePlaceMap.put("reference", reference);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }

    private List<HashMap<String, String>> getAllNearbyPLaces(JSONArray jsonArray){
        int counter = jsonArray.length();
        List<HashMap<String, String>> nearbyPLacesList = new ArrayList<>();
        HashMap<String,  String> nearbyPlaceMap = null;

        for(int i=0; i<counter; i++){
            try {
                nearbyPlaceMap = getSinglePlace((JSONObject) jsonArray.get(i));
                nearbyPLacesList.add(nearbyPlaceMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return nearbyPLacesList;
    }

    public List<HashMap<String, String>> parse(String JSONdata){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(JSONdata);
            jsonArray = jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllNearbyPLaces(jsonArray);
    }
}
