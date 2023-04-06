package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ShowUserState extends Fragment {

    View view;
    Statics statics;
    Dialog dailog;
    LinearLayout sho_data;
    String DATA_URL = "https://www.medicateint.com/data2/BeneficiaryDetails/";
    boolean internet = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_show_user_state, container, false);
        sho_data  = view.findViewById(R.id.ll_user_info);
        sho_data.setVisibility(View.GONE);
        view.findViewById(R.id.imageView11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        ((TextInputEditText) view.findViewById(R.id.num)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 0){
                        sho_data.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                        sho_data.setVisibility(View.GONE);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        view.findViewById(R.id.textView20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View n) {
                if (internet){
                    if (((TextInputEditText)view.findViewById(R.id.num)).getText().toString().trim().length()==0)
                        Messages.Dialog(getString(R.string.plz_fill_cardnum),getActivity());
                    else
                    getJSON(DATA_URL.concat(((TextInputEditText)view.findViewById(R.id.num)).getText().toString().trim()));
                }
                else {
              Messages.Dialog(getString(R.string.cheack_int_con),getActivity());
                }
            }
        });

        return  view ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CheckConnection.isNetworkConnected(getActivity())) {
            internet = true;
        }
        LoadingDigSetup();
    }

    public void LoadingDigSetup() {
        dailog = new Dialog(getActivity());
        dailog.setContentView(R.layout.loading_layout);
        dailog.setCancelable(false);
        dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dailog.dismiss();
            }
        });
        dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    private void getJSON(final String urlWebService) {
        class GetJSON_Hospital extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dailog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (!(null == getActivity())) {
                    if (urlWebService.contains(DATA_URL)) {
                        try {
                            chack(s);
                        } catch (Exception e) {
                            Log.d("C123123", "ERROR ! > " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    dailog.dismiss();
                } else {
                    cancel(true);
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    Log.d("C123123", "DATA > " + url.toString());

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(10000);
                    con.setReadTimeout(10000);
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                    Log.d("C123123", "DATA > " + sb.toString().trim());
                    return sb.toString().trim();
                } catch (Exception e) {
                    //  Messages.Dialog(getString(R.string.cheack_int_con),getActivity());
                    return null;
                }
            }
        }
        GetJSON_Hospital getJSON = new GetJSON_Hospital();
        getJSON.execute();
    }

    private void chack(String json) throws JSONException{
        if (json != null) {

            if (json.length() < 10)
                Messages.Dialog(getString(R.string.card_num_error),getActivity());
            else {
                JSONArray obj = new JSONArray(json);
                ((TextView)view.findViewById(R.id.data_name)).setText(obj.getJSONObject(0).getString("Name").trim());
                ((TextView)view.findViewById(R.id.data_card_state)).setText(CardState(obj.getJSONObject(0).getString("CardStatus")));
                if (!obj.getJSONObject(0).getString("CardStatus").equals("1")) {
                    ((TextView) view.findViewById(R.id.data_card_state)).setTextColor(Color.RED);
                    ((TextView) view.findViewById(R.id.card_amount)).setText("-----");
                }
                else {
                    ((TextView) view.findViewById(R.id.card_amount)).setText(obj.getJSONObject(0).getString("amount").concat(" د.ل"));
                    ((TextView) view.findViewById(R.id.data_card_state)).setTextColor(getResources().getColor(R.color.dollar_back_grane));
                }
                sho_data.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.cont_panal));
                sho_data.setVisibility(View.VISIBLE);
            }
        }
        else {
            Messages.Dialog(getString(R.string.no_internet_con),getActivity());
        }
    }
    public String CardState( String a) {
        switch (a) {
            case "0":
                return getString(R.string.card_state_disable);
            case "1":
                return getString(R.string.card_state_enable);
            case "2":
                return getString(R.string.card_state_dispanded);
            case "3":
                return getString(R.string.card_state_discorapted);
        }
        return getString(R.string.uknown_error);
    }

}