package com.example.covofficial.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences_Utils {

    public Preferences_Utils() {
    }

    public static boolean Used(String use, Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.Used,use);
        editor.apply();
        return  true;
    }


    public static String IsUsed(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.Used,null);
    }

}
