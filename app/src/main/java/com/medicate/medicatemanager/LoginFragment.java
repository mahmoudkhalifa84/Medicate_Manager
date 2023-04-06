package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    View view;
    TextView type;
    Statics statics;
    int user_type = 0;
    FrameLayout login_but;
    Dialog dailog;
    BackgroundWorker backgroundWorker;
    TextInputEditText username,pass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        dailog = new Dialog(getActivity());
        dailog.setContentView(R.layout.loading_layout);
        dailog.setCancelable(true);
        dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                backgroundWorker.cancel(true);
                dailog.dismiss();
            }
        });
        dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        username = view.findViewById(R.id.etUserName);
        pass = view.findViewById(R.id.etPassword);
        login_but = view.findViewById(R.id.btnLogin);
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckConnection.isNetworkConnected(getActivity())) { //returns true if internet available
                    String name = username.getText().toString().trim();
                    String password = pass.getText().toString().trim();
                    if (password.isEmpty() || name.isEmpty()) {
                        Messages.Dialog(getResources().getString(R.string.pass_or_name_error), getActivity());
                        return;
                    }
                    backgroundWorker =
                            new BackgroundWorker(getActivity(), dailog);
                    backgroundWorker.execute(name, password);
                } else {
                    Messages.Dialog(getResources().getString(R.string.cheack_int_con), getActivity());
                }
            }});




        /*type.setText("نوع العقد");
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog select_type = new Dialog(getActivity(), R.style.PauseDialog);
                select_type.setContentView(R.layout.select_type);
                select_type.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                select_type.findViewById(R.id.user_type1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user_type = 1;
                        type.setText(getString(R.string.user_type_1));
                        select_type.dismiss();

                    }
                });
                select_type.findViewById(R.id.user_type2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user_type = 2;
                        type.setText(getString(R.string.user_type_2));
                        select_type.dismiss();

                    }
                });
                select_type.setCancelable(true);
                select_type.show();
            }
        });*/
        view.findViewById(R.id.skip_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.textView10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Statics.OpenMyAppStorePage(getActivity(),"MyMedicate");
            }
        });
        view.findViewById(R.id.textView61).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity(),R.style.PauseDialog);
                dialog.setContentView(R.layout.ser_hotal);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.findViewById(R.id.j_main_c).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        statics.savePrefsData("join_place","c");
                        statics.setMY_PLACE("انضم الينا");
                        startActivity(new Intent(getActivity(),MainActivity.class));
                    }
                });
                dialog.findViewById(R.id.j_main_p).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        statics.savePrefsData("join_place","p");
                        statics.setMY_PLACE("انضم الينا");
                        startActivity(new Intent(getActivity(),MainActivity.class));
                    }
                });
                dialog.findViewById(R.id.j_main_g).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        statics.savePrefsData("join_place","g");
                        statics.setMY_PLACE("انضم الينا");
                        startActivity(new Intent(getActivity(),MainActivity.class));
                    }
                });
                dialog.findViewById(R.id.j_main_d).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        statics.savePrefsData("join_place","d");
                        statics.setMY_PLACE("انضم الينا");
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(),MainActivity.class));
                     /*   dialog.dismiss();
                        Messages.Dialog(getString(R.string.soon),getActivity());*/
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statics = new Statics(getActivity().getApplicationContext());
    }
}
