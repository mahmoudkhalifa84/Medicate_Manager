package com.medicate.medicatemanager;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final String NOTIFICATION_CHANNEL_ID = "8235195";
    Statics statics;
    Toolbar toolbar;
    boolean remane = false;
    NavigationView navigationView;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statics = new Statics(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer_ind);
        setSupportActionBar(toolbar);
        getSupportFragmentManager();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.coustom_actionbar_title, null);
        ((TextView) v.findViewById(R.id.textView_coustom)).setText(this.getTitle());
        this.getSupportActionBar().setCustomView(v);
        if(!isMyServiceRunning(FirebaseMessageHandler.class,this))
            startService(new Intent(this,FirebaseMessageHandler.class));

    }
    public static boolean isMyServiceRunning(Class<?> serviceClass,Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public void subTopic(){
        if ((!statics.getID().equals("null")) && (statics.getTopic().equals("null")))
        FirebaseMessaging.getInstance().subscribeToTopic(statics.getCompanyType())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                                statics.setTopic("ok");
                                Log.d("MAINFRAAAAAAA", "onComplete: TOPIC OK");
                            }
                        else if (!task.isSuccessful()){
                            Log.d("MAINFRAAAAAAA", "onComplete: TOPIC FAIL");

                        }
                    }
                });
    }
    public static void push(Context context , String t , String c, int i) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(t) ;
        builder.setContentText(c) ;
        builder.setAutoCancel(false);
        builder.setSmallIcon(R.drawable.legal_smal) ;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(i,builder.build());

    }


    protected void onStart() {
        super.onStart();
        statics = new Statics(this);
        subTopic();
        if (statics.getID().equals("null")) {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        }
        else {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        }
        if (remane == false) {
            toolbar.setVisibility(View.VISIBLE);
            if (statics.getMY_PLACE().equals("تسجيل الدخول")) {
                toolbar.setVisibility(View.GONE);
                showHome(new LoginFragment());
            } else if (statics.getMY_PLACE().equals("انضم الينا")) {
                showHome(new JoinUsFragment());
            } else if (statics.getMY_PLACE().equals("المنزل2")) {
                Messages.Dialog("شكراً لك \nتم أرسال البيانات سيتم التواصل معك قريباً", this);
                showHome(new MainFragment());
                statics.setMY_PLACE("المنزل");
            } else if (statics.getMY_PLACE().equals("لوحة المراقبة")) {
                 showHome(new ContoralPanal());
            } else if (statics.getMY_PLACE().equals("التواصل معنا")) {
                 showHome(new ChatFragment());
            } else if (statics.getMY_PLACE().equals("الاشعارات")) {
                 showHome(new NotificationsFragment());
            } else if (statics.getMY_PLACE().equals("الاستشارات الطبية")) {
                 showHome(new MyQFragment());
            } else if (statics.getMY_PLACE().equals("الاستعلام عن مستفيد")) {
                 showHome(new ShowUserState());
            } else {
                statics.setMY_PLACE("المنزل");
                showHome(new MainFragment());
            }
        }
        remane = false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isOpen()){
            drawer.close();
        return;
    }
        toolbar.setVisibility(View.VISIBLE);
        switch (statics.getMY_PLACE()) {
            case "المنزل":
                    Dialog dialog = new Dialog(this, R.style.PauseDialog);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.fragment_dailog);
                    TextView textView = dialog.findViewById(R.id.text_dig);
                    textView.setText(R.string.are_you_suer_close_app);
                    dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finishAffinity();
                        }
                    });
                    dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                break;
            default: {
                statics.setMY_PLACE("المنزل");
                showHome(new MainFragment());
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        remane = true;
    }

    public void showHome(Fragment f) {
        FragmentManager fmgr = getSupportFragmentManager();
        fmgr.beginTransaction()
                .setCustomAnimations(R.anim.network_bob, R.anim.popout)
                .replace(R.id.fragment_container, f, f.getTag()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home :
                statics.setMY_PLACE("المنزل");
                showHome(new MainFragment());
                break;
            case R.id.nav_notifications :
                login(3);
                break;
            case R.id.nav_rate :
                Messages.RateDialog(this);
                break;
            case R.id.nav_login :
                toolbar.setVisibility(View.GONE);
                showHome(new LoginFragment());
                statics.setMY_PLACE("تسجيل دخول");
                break;
            case R.id.nav_logout :
                statics.delAll();
                statics.setMY_PLACE("المنزل");
                navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                Toast.makeText(this, getString(R.string.logout_secc), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
        }
        drawer.close();
        return true;
    }
    private void login(int i) {
        if (statics.getID().equals("null")) {
            Dialog dialog = new Dialog(this, R.style.PauseDialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.fragment_dailog);
            TextView textView = dialog.findViewById(R.id.text_dig);
            textView.setText(R.string.login_to_contune);
            dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    statics.setMY_PLACE("تسجيل الدخول");
                    toolbar.setVisibility(View.GONE);
                    showHome(new LoginFragment());
                }
            });
            dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        else {
            if (i == 1) {
                statics.setMY_PLACE("الاستعلام عن مستفيد");
                showHome(new ShowUserState());
            } else if (i == 2) {
                statics.setMY_PLACE("لوحة المراقبة");
                showHome(new ContoralPanal());
            } else if (i == 3) {
                statics.setMY_PLACE("الاشعارات");
                showHome(new NotificationsFragment());
            } else if (i == 4){
                statics.setMY_PLACE("الاستشارات الطبية");
                showHome(new MyQFragment());
            } else if (i == 5){
                statics.setMY_PLACE("التواصل معنا");
                showHome(new ChatFragment());
            }
        }
    }
}