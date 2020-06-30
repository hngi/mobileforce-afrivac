package com.michael.afrivac.sharePref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class SharePref {

    private static final String LAST_LOGGED_IN = "LAST_LOGGED_IN";
    private static final String ID_KEY = "com.project.android_kidstories_ID_KEY";
    private static final String USER_LOGIN_STATE = "isUserLoggedIn";
    private static final String NIGHT_MODE = "NIGHT MODE";
    private static final String ALARM_TIME = "ALARM_TIME";
    public static Context context;
    private static SharePref INSTANCE;
    private static SharedPreferences sharedPreferences;

    private SharePref(SharedPreferences sharedPreferences) {
        SharePref.sharedPreferences = sharedPreferences;

    }

    public SharePref(Context context) {
        SharePref.context = context;
    }

    public static synchronized SharePref getINSTANCE(Context context) {
        if (INSTANCE == null) {
            //noinspection deprecation
            INSTANCE = new SharePref(PreferenceManager.getDefaultSharedPreferences(context));
        }
        return INSTANCE;
    }

    public boolean getNightMode() {
        return sharedPreferences.getBoolean(NIGHT_MODE, false);
    }

    public void setNightMode(boolean nightMode) {
        sharedPreferences.edit().putBoolean(NIGHT_MODE, nightMode).apply();
    }

    public void setString(String key, String data) {
        sharedPreferences.edit().putString(key, data).apply();
    }

    public void setInt(String key, int data) {
        sharedPreferences.edit().putInt(key, data).apply();
    }

    public void setLong(String key, long data) {
        sharedPreferences.edit().putLong(key, data).apply();
    }

    public void setBool(String key, boolean data) {
        sharedPreferences.edit().putBoolean(key, data).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public boolean getBool(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public SharePref getSharePref() {
        return INSTANCE;
    }

    public void saveLoginDetails(String token, String firstname, String lastname, String email) {
        sharedPreferences = context.getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", token);
        editor.putString("Firstname", firstname);
        editor.putString("Lastname", lastname);
        editor.putString("Email", email);
        editor.apply();
    }

    public String getMyToken() {
        sharedPreferences = context.getSharedPreferences("LoginDetails", MODE_PRIVATE);
        return sharedPreferences.getString("Token", "");
    }

    public String getUserFirstname() {
        sharedPreferences = context.getSharedPreferences("LoginDetails", MODE_PRIVATE);
        return sharedPreferences.getString("Firstname", "");
    }

    public String getUserLastname() {
        sharedPreferences = context.getSharedPreferences("LoginDetails", MODE_PRIVATE);
        return sharedPreferences.getString("Lastname", "");
    }

    public String getUserEmail() {
        sharedPreferences = context.getSharedPreferences("LoginDetails", MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }


    public void setLastSunAccess(int hour) {
        sharedPreferences.edit().putInt(LAST_LOGGED_IN, hour).apply();
    }

    public int getLastLoginInHour() {
        return sharedPreferences.getInt(LAST_LOGGED_IN, 1);
    }

    public Long getLoggedUserId() {
        return sharedPreferences.getLong(ID_KEY, -1);
    }

    public void setLoggedUserId(Long id) {
        sharedPreferences.edit().putLong(ID_KEY, id).apply();
    }

    public Boolean getIsUserLoggedIn() {
        return sharedPreferences.getBoolean(USER_LOGIN_STATE, false);
    }

  public Boolean getFirstTime() {
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putBoolean("firstTime",false);
       editor.apply();
       return isFirstTime;
  }

    public void setIsUserLoggedIn(Boolean isUserLoggedIn) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(USER_LOGIN_STATE, isUserLoggedIn).apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt("User Id", 0);
    }

    public void setUserId(Integer id) {

        sharedPreferences.edit().putInt("User Id", id).apply();
    }


}
