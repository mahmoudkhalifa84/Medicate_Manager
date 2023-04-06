package com.medicate.medicatemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Locale;

import static com.medicate.medicatemanager.MainActivity.NOTIFICATION_CHANNEL_ID;

public class SplachScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);
        Statics statics = new Statics(this);
        ((ImageView) findViewById(R.id.imageView4)).setAnimation(AnimationUtils.loadAnimation(this,R.anim.logo_m));
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                if (new Statics(SplachScreen.this).getCompanyID().equals("null")) {
                    statics.setMY_PLACE("تسجيل الدخول");
                }
                else
                    statics.setMY_PLACE("المنزل");
                setLocale("ar",SplachScreen.this);
                startActivity(new Intent(SplachScreen.this,MainActivity.class));

            }
        }).start();
        createNotificationChannel();
    }
    public void setLocale(String lang, Context context) {
        Locale loc = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = loc;
        config.setLayoutDirection(loc);
        res.updateConfiguration(config, displayMetrics);
        Statics statics = new Statics(context);
        statics.savePrefsData("lang", lang);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.Notification);
            String description = getString(R.string.Notification);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setShowBadge(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                channel.setAllowBubbles(true);
            }
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}