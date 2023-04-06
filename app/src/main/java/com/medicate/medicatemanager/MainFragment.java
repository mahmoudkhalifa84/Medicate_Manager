package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

public class MainFragment extends Fragment {
    View view;
    Statics statics;
    TextView but;
    EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_main, container, false);
        statics = new Statics(getActivity());
        editText =  view.findViewById(R.id.main_edit_text);
        but =  view.findViewById(R.id.main_send_but);
        but.setVisibility(View.GONE);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statics.setMainTxt(editText.getText().toString().trim());
                login(5);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    if (but.getVisibility() != View.VISIBLE) {
                        but.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.network_bob));
                        but.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    but.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                    but.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        view.findViewById(R.id.ll_user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(1);
            }
        });
        view.findViewById(R.id.ll_contr_panal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(2);
            }
        });
        view.findViewById(R.id.ll_notifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(3);
            }
        });
        view.findViewById(R.id.ll_m_q).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               login(4);
            }
        });
        view.findViewById(R.id.ll_c_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               login(5);
            }
        });

        return view ;
    }

    private void login(int i) {
        if (statics.getID().equals("null")) {
            Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.fragment_dailog);
            TextView textView = dialog.findViewById(R.id.text_dig);
            textView.setText(R.string.login_to_contune);
            dialog.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    statics.setMY_PLACE("تسجيل الدخول");
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
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
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else if (i == 2) {
                statics.setMY_PLACE("لوحة المراقبة");
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else if (i == 3) {
                statics.setMY_PLACE("الاشعارات");
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else if (i == 4){
                statics.setMY_PLACE("الاستشارات الطبية");
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else if (i == 5){
                statics.setMY_PLACE("التواصل معنا");
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        }
    }
}