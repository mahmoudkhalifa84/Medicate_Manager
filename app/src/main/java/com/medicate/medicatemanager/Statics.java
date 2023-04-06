package com.medicate.medicatemanager;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

public class Statics extends Application {
    private Context context;
    public static final  String EMAIL = "heshamthedeveloper@gmail.com";
    public static final String PASSWORD = "svyrvfpjzfatpqcz";
    public static final String SEND_TO_EMAIL = "owyatgroup@protonmail.com";

    public Statics(Context context) {
        this.context = context;
    }


    public String getMY_PLACE() {
        return restorePrefData("place");
    }

    public void setMY_PLACE(String MY_PLACE) {
        savePrefsData("place", MY_PLACE);
    }

    public void setCompanyID(String MY_PLACE) {
        savePrefsData("company_id", MY_PLACE);
    }

    public String getCompanyID() {
        return restorePrefData("company_id");
    }

    public String getConNumber() {
        return restorePrefData("con_num");
    }

    public void setCompanyType(String MY_PLACE) {
        savePrefsData("company_type", MY_PLACE);
    }

    public void setConNumber(String Number) {
        savePrefsData("con_num", Number);
    }

    public String getCompanyType() {
        return restorePrefData("company_type");
    }

    public String getMY_NETWORK() {
        return restorePrefData("network");
    }

    public void setMY_NETWORK(String MY_NETWORK) {
        savePrefsData("network", MY_NETWORK);
    }

    public void setID(String id) {
        savePrefsData("BeneficiaryID", id);
    }

    public String getMY_CONTRY() {
        return restorePrefData("country");
    }

    public String getTxt() {
        return restorePrefData("txt");
    }

    public String getID() {
        String id = restorePrefData("BeneficiaryID");
        return id.isEmpty() ? "null" : id;
    }

    public void setMY_CONTRY(String MY_CONTRY) {
        savePrefsData("country", MY_CONTRY);
    }

    public String getUserName() {
        return restorePrefData("card_number");
    }

    public void setUserName(String card_number) {
        savePrefsData("card_number", card_number);
    }

    public String getCompName() {
        String id = restorePrefData("comp_name");
        return id.isEmpty() ? "null" : id;
    }

    public void setCompName(String name) {
        savePrefsData("comp_name", name);
    }

    public String getTopic() {
        String id = restorePrefData("topic");
        return id.isEmpty() ? "null" : id;
    }


    public void setTopic(String topic) {
        savePrefsData("topic", topic);
    }

    public void delAll() {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
        new NotificationsDatabase(context.getApplicationContext()).DelDate();
    }

    public void setMainTxt(String txt) {
        savePrefsData("txt", txt);
    }


    public String restorePrefData(String s) {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        return pref.getString(s, "null");
    }

    public void savePrefsData(String s, String v) {
        SharedPreferences pref = context.getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(s, v);
        editor.apply();
    }

    public static void OpenMyAppStorePage(@NonNull Context c, String pak) {
        final String appPackageName = c.getPackageName(); // getPackageName() from Context or Activity object
        try {
            if (pak.equals("MyMedicate"))
                c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.medicate_int.mymedicate")));
            else
                c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

        } catch (android.content.ActivityNotFoundException anfe) {
            if (pak.equals("MyMedicate"))
                c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.medicate_int.mymedicate")));
            else
                c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
