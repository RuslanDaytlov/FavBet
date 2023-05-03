package com.sport.sport;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesHelper implements  PreferencesHelperS {

    private final SharedPreferences mPrefs;

    private static final String PREF_KEY_API_URL = "PREF_KEY_API_URL";

    public AppPreferencesHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

   @Override
    public  String getAPIUrl() {
        return mPrefs.getString(PREF_KEY_API_URL, null);
    }


    @Override
    public void setAPIUrl(String url) {
        mPrefs.edit().putString(PREF_KEY_API_URL, url).apply();
    }
}
