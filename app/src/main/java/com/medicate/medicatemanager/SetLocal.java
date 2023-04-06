package com.medicate.medicatemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SetLocal extends AppCompatActivity {

    public void setLocale(String lang, Context context) {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lang",lang);
        editor.apply();
        Locale loc = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = loc;
        config.setLayoutDirection(loc);
        res.updateConfiguration(config, displayMetrics);
        Statics statics = new Statics(context);
    }

    public static String getLong(Context context) {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        if (null != new Statics(context).restorePrefData("lang"))
        return pref.getString("lang", new Statics(context).restorePrefData("lang"));
        else {
            return pref.getString("lang", "ar");

        }
    }
}
