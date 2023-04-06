package com.medicate.medicatemanager;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FirebaseMessageHandler extends FirebaseMessagingService {
    String TAG = "FireBASE-MSG";
    Statics statics;
    SimpleDateFormat df;
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        onCreate();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        Log.d(TAG, "REMOVED: ");
        //   onCreate();
    }

    @Override
    public void onCreate() {
        df = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault());
        statics = new Statics(this);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // ...
        String formattedDate = df.format(Calendar.getInstance().getTime());

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "DATA: " + remoteMessage.getData());
        // Check if message contains a data payload.
            try {
                Log.d(TAG, "SEND: " + remoteMessage.getData());

                MainActivity.push(this,remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), (int) Calendar.getInstance().getTimeInMillis());
                    new NotificationsDatabase(this).insertData(remoteMessage.getNotification().getBody(), formattedDate);
            } catch (NullPointerException e) {
                Log.d(TAG, "NULL: " + e.getMessage());

            }
        }

    @Override
    public void onNewToken(@NonNull String s) {

        Log.d(TAG, "Refreshed token: " + s);

    }
}
