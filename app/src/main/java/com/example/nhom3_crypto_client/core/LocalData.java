package com.example.nhom3_crypto_client.core;

import android.content.Context;
import android.content.SharedPreferences;


public class LocalData {
    private static String PREFS_NAME = "startup_map_local_data";

    private SharedPreferences prefs;

    public LocalData(Context content) {
        this.prefs = content.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    public String getString(String key){
        return prefs.getString(key, "");
    }
    public void setString(String key, String value){
        prefs.edit().putString(key,value).apply();
    }
}
