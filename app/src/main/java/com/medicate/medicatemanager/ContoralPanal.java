package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ContoralPanal extends Fragment implements ItemsTextAdapter.onCLickLis2 {
    View view;
    Statics statics;
    String TAG = "CONROLPANAL";
    TextView con_type;
    BottomSheetDialog bottomSheetDialog;
    List<TextItems> list;
    BarChart barChart;
    Dialog dailog, no_int_did;
    int selected_contract = 0, index;
    PieChart pai;
    boolean hasData = false;
    int comp_type = 0;
    double total_value = 0.0;
    List<DataItems> xray_items, opr_items, hartopr_items, iwa_items, polia_items, nisa_items;
    List<DataItems> p_drug_items, p_mozmna_items, p_awram_items, d_dep_items, d_ser_items;
    String DATA_COMPANY_URL = "https://www.medicateint.com/data2/CompanySumation/";
    String DATA_URL = "https://www.medicateint.com/data2/CompDetailis/";
    String D_DEP_URL = "https://www.medicateint.com/data2/DentalSubparent/";
    String D_SERVICS_URL = "https://www.medicateint.com/data2/ChartDental/";
    String HART_URL = "https://www.medicateint.com/data2/BenDataHeart/";
    String IWA_URL = "https://www.medicateint.com/data2/BenDataAdmation/";
    String NISA_URL = "https://www.medicateint.com/data2/BenDataGyne/";
    String OPR_URL = "https://www.medicateint.com/data2/BenDataoperations/";
    String POLIA_URL = "https://www.medicateint.com/data2/BenDataAUrinaryTract/";
    String P_AMAZON_URL = "https://www.medicateint.com/data2/DataDrugsChronic/";
    String P_AWRAM_URL = "https://www.medicateint.com/data2/DataDrugsAnticancel/";
    String P_DRUG_URL = "https://www.medicateint.com/data2/DataDrugs/";
    String XRAY_URL = "https://www.medicateint.com/data2/BenData/";
    PieChart pai2, iwa_pie, hart_pie, nisa_pie, polia_pie, p_drug, p_mozmna, p_awram, d_servics, d_dep;
    PieDataSet xray_dataset, opr_dataset, hartopr_dataset, iwa_dataset, polia_dataset, nisa_dataSet;
    PieDataSet p_drug_dataset, p_mozmna_dataset, p_awram_dataset, d_servics_dataset, d_dep_dataset;
    PieData xray_data, opr_data, hartopr_data, iwa_data, polia_data, nisa_data;
    PieData p_drug_data, p_mozmna_data, p_awram_data, d_servics_data, d_dep_data;
    boolean internet;
    boolean pi1 = false, pi2 = false, ba1 = false;
    List<PricesDataItems> pricesDataItems;
    NumberFormat formater = new DecimalFormat("#.000");

    public static String getDecimalFormattedString(String value) {

            if (!value.contains(".")) {
                return value.substring(0, value.indexOf(","));
            }


        if (Double.parseDouble(value) == 0.0)
            return "000.000";
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt(-1 + str1.length()) == '.') {
            j--;
            str3 = ".";
        }
        for (int k = j; ; k--) {
            if (k < 0) {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3) {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contoral_panal, container, false);

        pricesDataItems = new ArrayList<>();
        con_type = view.findViewById(R.id.textView6);
        switch (comp_type) {
            case 1:
            case 2:
            case 6:
            case 7:
            case 8:
                con_type.setVisibility(View.VISIBLE);
                FirstSetup();
                break;
            case 3:
            case 5:
            case 4:
                con_type.setVisibility(View.GONE);
                ManagerSetup();
                break;

        }


        view.findViewById(R.id.linearLayout7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        con_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView recyclerView = new RecyclerView(getActivity());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                list = new ArrayList<>();
                list.add(new TextItems("", 0));
                list.add(new TextItems(getString(R.string.Qabeda_company), 1));
                list.add(new TextItems(getString(R.string.Zawwya_company_typ), 2));
                list.add(new TextItems(getString(R.string.Dig_company), 3));
                list.add(new TextItems(getString(R.string.Zawwya_company), 4));
                list.add(new TextItems(getString(R.string.Qtr_company), 5));
                list.add(new TextItems(getString(R.string.Akakos_company), 6));
                bottomSheetDialog.setCancelable(true);
                if (recyclerView.getParent() != null) {
                    ((ViewGroup) recyclerView.getParent()).removeView(recyclerView); // <- fix
                }
                recyclerView.setAdapter(new ItemsTextAdapter(getActivity(), list, ContoralPanal.this));
                bottomSheetDialog.setContentView(recyclerView);
                bottomSheetDialog.show();
            }
        });
        if (internet) {
            loadData();
        } else
            NoInternet();
        return view;
    }

    private void ManagerSetup() {
    }

    public void FirstSetup() {
        Log.d(TAG, "FirstSetup: SETUP COMPTYPE " + comp_type);
        switch (comp_type) {
            case 1 :
            case 7 :
            case 8 :
                barChart = view.findViewById(R.id.barchart);
                pai = view.findViewById(R.id.piechart);
                pai2 = view.findViewById(R.id.piechart2);
                iwa_pie = view.findViewById(R.id.piechartiwa);
                hart_pie = view.findViewById(R.id.piecharthart);
                nisa_pie = view.findViewById(R.id.piechart_nisa);
                polia_pie = view.findViewById(R.id.piechartmsalk);
                xray_items = new ArrayList<>();
                opr_items = new ArrayList<>();
                nisa_items = new ArrayList<>();
                hartopr_items = new ArrayList<>();
                polia_items = new ArrayList<>();
                iwa_items = new ArrayList<>();
                break;
            case 2:
                p_drug = view.findViewById(R.id.p_bast_5_med);
                p_mozmna = view.findViewById(R.id.p_mozmna);
                p_awram = view.findViewById(R.id.p_awram);
                p_drug_items = new ArrayList<>();
                p_mozmna_items = new ArrayList<>();
                p_awram_items = new ArrayList<>();
                break;
            case 6:
                d_dep = view.findViewById(R.id.d_dep);
                d_servics = view.findViewById(R.id.d_serv);
                d_dep_items = new ArrayList<>();
                d_ser_items = new ArrayList<>();
                break;
        }
    }

    private void loadData() {
        Log.d(TAG, "loadData: COMP");
        switch (comp_type) {
            case 1 :
            case 7 :
            case 8 :
                getJSON((DATA_URL.concat(statics.getCompanyID())));
                view.findViewById(R.id.for_phar_ly).setVisibility(View.GONE);
                view.findViewById(R.id.for_d_ly).setVisibility(View.GONE);
                getJSON((XRAY_URL.concat(statics.getCompanyID())));
                getJSON((HART_URL.concat(statics.getCompanyID())));
                getJSON((POLIA_URL.concat(statics.getCompanyID())));
                getJSON((IWA_URL.concat(statics.getCompanyID())));
                getJSON((NISA_URL.concat(statics.getCompanyID())));
                getJSON((OPR_URL.concat(statics.getCompanyID())));
                break;
            case 2:
                getJSON((DATA_URL.concat(statics.getCompanyID())));
                view.findViewById(R.id.for_hos_ly).setVisibility(View.GONE);
                view.findViewById(R.id.for_d_ly).setVisibility(View.GONE);
                getJSON((P_DRUG_URL.concat(statics.getCompanyID())));
                getJSON((P_AWRAM_URL.concat(statics.getCompanyID())));
                getJSON((P_AMAZON_URL.concat(statics.getCompanyID())));
                break;
            case 6:
                getJSON((DATA_URL.concat(statics.getCompanyID())));
                view.findViewById(R.id.for_phar_ly).setVisibility(View.GONE);
                view.findViewById(R.id.for_hos_ly).setVisibility(View.GONE);
                getJSON((D_SERVICS_URL.concat(statics.getCompanyID())));
                getJSON((D_DEP_URL.concat(statics.getCompanyID())));
                break;
            case 3:
            case 4:
            case 5:
                getJSON((DATA_COMPANY_URL.concat(statics.getConNumber())));
                Log.d(TAG, "loadData: " + DATA_COMPANY_URL.concat(statics.getConNumber()));
                view.findViewById(R.id.for_phar_ly).setVisibility(View.GONE);
                view.findViewById(R.id.for_hos_ly).setVisibility(View.GONE);
                view.findViewById(R.id.for_d_ly).setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statics = new Statics(getActivity());
        comp_type = (Integer.parseInt(statics.getCompanyType()));
        Log.d(TAG, "CompanyType: " + comp_type);

        if (CheckConnection.isNetworkConnected(getActivity())) {
            internet = true;
        }
        LoadingDigSetup();

    }

    public void NoInternet() {
        ((ScrollView) view.findViewById(R.id.sssdsd)).setVisibility(View.GONE);
        no_int_did = new Dialog(getActivity(), R.style.PauseDialog);
        no_int_did.setCancelable(false);
        no_int_did.setContentView(R.layout.fragment_dailog);
        ((TextView) no_int_did.findViewById(R.id.di_ok)).setText(getString(R.string.try_agin));
        ((TextView) no_int_did.findViewById(R.id.text_dig)).setText(getString(R.string.no_internet_con));
        no_int_did.findViewById(R.id.di_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_int_did.dismiss();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        no_int_did.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_int_did.dismiss();
                getActivity().onBackPressed();
            }
        });
        no_int_did.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        no_int_did.show();
    }

    public void LoadingDigSetup() {
        dailog = new Dialog(getActivity());
        dailog.setContentView(R.layout.loading_layout);
        dailog.setCancelable(true);
        dailog.setCanceledOnTouchOutside(false);
        dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dailog.dismiss();
                getActivity().onBackPressed();
            }
        });
        dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }



    private void loadD_SERV(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    d_ser_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadD_SERV: " + obj.getInt("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else d_ser_items.clear();
        }
    }

    private void loadD_DEP(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    d_dep_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadD_DEP: " + obj.getString("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else d_dep_items.clear();

            FillData();
        }
    }


    private void loadP_Drugs(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    p_drug_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadP_Drugs: " + obj.getInt("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else p_drug_items.clear();
        }
    }

    private void loadP_Mozmna(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    p_mozmna_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadP_Mozmna: " + obj.getString("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else p_mozmna_items.clear();

            FillData();
        }
    }

    private void loadP_Awram(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    p_awram_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadP_Awram: " + obj.getInt("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else p_awram_items.clear();
        }
    }


    private void loadXrays(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    xray_items.add(new DataItems(obj.getInt("CountService"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadXrays: " + obj.getInt("CountService") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else xray_items.clear();
        }
    }

    private void loadXOpr(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    opr_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    total_value += obj.getDouble("ClinicServiceAmount");
                    Log.d(TAG, "loadXOpr: " + obj.getString("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else opr_items.clear();

            FillData();
        }
    }

    private void loadIwa(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    iwa_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadIwa: " + obj.getInt("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else iwa_items.clear();
        }
    }

    private void loadPolia(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    polia_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadPolia: " + obj.getInt("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else polia_items.clear();
        }
    }

    private void loadNisa(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    nisa_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadNisa: " + obj.getInt("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else nisa_items.clear();
        }
    }

    private void loadHart(String json) throws JSONException {
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    hartopr_items.add(new DataItems(obj.getInt("ClinicServiceAmount"), obj.getString("ServiceArabicName")));
                    Log.d(TAG, "loadHart: " + obj.getInt("ClinicServiceAmount") + "  -  " + obj.getString("ServiceArabicName"));
                }
            } else hartopr_items.clear();
        }
    }

    private void loadIntoList(String json) throws JSONException {
        PricesDataItems prices;
        if (!(json == null)) {
            if (json.length() > 10) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    prices = new PricesDataItems();
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if (!obj.isNull("CompAmount"))
                        prices.setCompAmount(obj.getDouble("CompAmount")); // 0
                    else
                        prices.setCompAmount(000.000); // 0

                    if (!obj.isNull("PercentageComp"))
                        prices.setPercentageComp(obj.getDouble("PercentageComp")); // 1
                    else
                        prices.setPercentageComp(000.000); // 1

                    if (!obj.isNull("PercentageClinic"))
                        prices.setPercentageClinic(obj.getDouble("PercentageClinic")); // 2
                    else
                        prices.setPercentageClinic(000.000); // 2

                    if (!obj.isNull("PercentageClinicBenClinic"))
                        prices.setPercentageClinicBenClinic(obj.getDouble("PercentageClinicBenClinic")); // 3

                    else
                        prices.setPercentageClinicBenClinic(000.000); // 3

                    if (!obj.isNull("CompAmountApproved"))
                        prices.setCompAmountApproved(obj.getDouble("CompAmountApproved")); // 4
                    else
                        prices.setCompAmountApproved(000.000); // 4


                    if (!obj.isNull("CompAmountReject"))
                        prices.setCompAmountReject(obj.getDouble("CompAmountReject")); // 5
                    else
                        prices.setCompAmountReject(000.000); // 5


                    if (!obj.isNull("CompAmountRecipient"))
                        prices.setCompAmountRecipient(obj.getDouble("CompAmountRecipient")); // 6
                    else
                        prices.setCompAmountRecipient(000.000); // 6


                    if (!obj.isNull("CompAmountPaid"))
                        prices.setCompAmountPaid(obj.getDouble("CompAmountPaid")); // 7

                    else
                        prices.setCompAmountPaid(000.000); // 7


                    if (!obj.isNull("CompAmountInReview"))
                        prices.setCompAmountInReview(obj.getDouble("CompAmountInReview")); // 8
                    else
                        prices.setCompAmountInReview(000.000); // 8


                    if (!obj.isNull("ClaimAmountOutstanding"))
                        prices.setClaimAmountOutstanding(obj.getDouble("ClaimAmountOutstanding")); // 9
                    else
                        prices.setClaimAmountOutstanding(000.000); // 9


                    if (!obj.isNull("PercentageClinicBenComp"))
                        prices.setPercentageClinicBenComp(obj.getDouble("PercentageClinicBenComp")); // 10

                    else
                        prices.setPercentageClinicBenComp(000.000); // 10

                    prices.setContractID(obj.getInt("ContractID"));
                    Log.d(TAG, "ContractID > " + prices.getContractID());
                    pricesDataItems.add(prices);
                }
                if (statics.getCompanyType().trim().equals("7") || statics.getCompanyType().trim().equals("4") ||
                        statics.getCompanyType().trim().equals("5")) {
                    Log.d(TAG, "loadIntoList: putPrice > true");
                    putPrices(true);

                }
                else {
                    Log.d(TAG, "loadIntoList: putPrice > false");
                    putPrices(false);
                }
            }

        } else Messages.Dialog(getString(R.string.cheack_int_con), getActivity());
    }
    public void putPrices(boolean bool) {
        if (selected_contract != 0 || bool) {
            Log.d(TAG, "ITS OK" + selected_contract);
            view.findViewById(R.id.textView37).setVisibility(View.GONE);

                ((TextView) view.findViewById(R.id.compAmount)).setText(String.valueOf(pricesDataItems.get(index).getCompAmount()));
                ((TextView) view.findViewById(R.id.compAmountRecpient)).setText(String.valueOf(pricesDataItems.get(index).getCompAmountRecipient()));
                ((TextView) view.findViewById(R.id.compnon_r)).setText(String.valueOf(pricesDataItems.get(index).getCompAmountRecipient() - ((pricesDataItems.get(index).getCompAmountApproved()) + (pricesDataItems.get(index).getCompAmountReject()))));
                ((TextView) view.findViewById(R.id.compAmountinReview)).setText(String.valueOf(pricesDataItems.get(index).getCompAmountInReview()));
                ((TextView) view.findViewById(R.id.compdone_reiv)).setText(String.valueOf(pricesDataItems.get(index).getCompAmountApproved()) + (pricesDataItems.get(index).getCompAmountReject()));
                ((TextView) view.findViewById(R.id.compAmountApproved)).setText(String.valueOf(pricesDataItems.get(index).getCompAmountApproved()).replace(",", "."));
                ((TextView) view.findViewById(R.id.compAmountReject)).setText(String.valueOf(pricesDataItems.get(index).getCompAmountReject()).replace(",", "."));
                ((TextView) view.findViewById(R.id.claimAmountoutStanding)).setText(String.valueOf(pricesDataItems.get(index).getClaimAmountOutstanding()).replace(",", "."));
                ((TextView) view.findViewById(R.id.precintg_clinic)).setText(String.valueOf(pricesDataItems.get(index).getPercentageClinic()).replace(",", "."));
                ((TextView) view.findViewById(R.id.perClinicBenClinic)).setText(String.valueOf(pricesDataItems.get(index).getPercentageClinicBenClinic()).replace(",", "."));
                ((TextView) view.findViewById(R.id.perClinicBenComp)).setText(String.valueOf(pricesDataItems.get(index).getPercentageClinicBenComp()).replace(",", "."));
                ((TextView) view.findViewById(R.id.perClinicBenComp)).setText((" " + getString(R.string.company).concat(((TextView) view.findViewById(R.id.perClinicBenComp)).getText().toString())));
                ((TextView) view.findViewById(R.id.total_clear_value)).setText(String.valueOf(pricesDataItems.get(index).getCompAmountApproved() - (pricesDataItems.get(index).getPercentageClinic())));

            if (dailog.isShowing()) {
                Log.d(TAG, "DISSMISS");
                dailog.dismiss();
            }
            if (((TextView) view.findViewById(R.id.total_clear_value)).getText().equals("000.000") &&
                    ((TextView) view.findViewById(R.id.compAmount)).getText().equals("000.000")){
                view.findViewById(R.id.for_phar_ly).setVisibility(View.GONE);
                view.findViewById(R.id.for_d_ly).setVisibility(View.GONE);
                view.findViewById(R.id.for_hos_ly).setVisibility(View.GONE);
            }
            else{
                switch (comp_type) {
                    case 1:
                    case 7:
                    case 8:
                        view.findViewById(R.id.for_hos_ly).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.for_phar_ly).setVisibility(View.GONE);
                        view.findViewById(R.id.for_d_ly).setVisibility(View.GONE);
                        break;
                    case 2:
                        view.findViewById(R.id.for_hos_ly).setVisibility(View.GONE);
                        view.findViewById(R.id.for_d_ly).setVisibility(View.GONE);
                        view.findViewById(R.id.for_phar_ly).setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        view.findViewById(R.id.for_phar_ly).setVisibility(View.GONE);
                        view.findViewById(R.id.for_hos_ly).setVisibility(View.GONE);
                        view.findViewById(R.id.for_d_ly).setVisibility(View.VISIBLE);
                        break;
                }
            }
            ((ScrollView) view.findViewById(R.id.sssdsd)).setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.cont_panal));
            ((ScrollView) view.findViewById(R.id.sssdsd)).setVisibility(View.VISIBLE);

        } else {
            Log.d(TAG, "ITS no");
            view.findViewById(R.id.textView37).setVisibility(View.VISIBLE);
            ((ScrollView) view.findViewById(R.id.sssdsd)).setVisibility(View.GONE);
        }

    }

    private void DTop5SERV() { /// 1
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < d_ser_items.size(); i++) {
            test1 = "الخدمة : " + d_ser_items.get(i).getArabic() + "\n" + "إجمالي القيمة  : " + d_ser_items.get(i).getPrice();
            pieEntryArrayList.add(new PieEntry(d_ser_items.get(i).getPrice(), d_ser_items.get(i).getArabic(), (test1)));
        }
        d_servics_dataset = new PieDataSet(pieEntryArrayList, "");
        d_servics_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        d_servics_dataset.setAutomaticallyDisableSliceSpacing(false);
        d_servics_dataset.setSliceSpace(1f);
        d_servics_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        d_servics_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        d_servics_dataset.setValueTextSize(18f);
        d_servics_data = new PieData(d_servics_dataset);
        d_servics.setNoDataText(getString(R.string.no_data));
        d_servics.setDrawHoleEnabled(true);
        d_servics.setHoleRadius(1f);
        d_servics.setData(d_servics_data);
        d_servics.setDrawRoundedSlices(false);
        d_servics.setDrawSlicesUnderHole(false);
        d_servics.setDrawCenterText(false);
        d_servics.setTransparentCircleRadius(5f);
        d_servics.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        d_servics.getDescription().setEnabled(false);
        d_servics.setNoDataTextColor(R.color.cont_red);
        d_servics.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (d_ser_items.isEmpty())
            d_servics.setData(null);
        d_servics.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void DTop5DEP() { // 2
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < d_dep_items.size(); i++) {
            test1 = "الخدمة : " + d_dep_items.get(i).getArabic() + "\n" + "إجمالي القيمة : " + d_dep_items.get(i).getPrice();
            pieEntryArrayList.add(new PieEntry(d_dep_items.get(i).getPrice(), d_dep_items.get(i).getArabic(), test1));
        }
        d_dep_dataset = new PieDataSet(pieEntryArrayList, "");
        d_dep_dataset.setValues(pieEntryArrayList);
        d_dep_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        d_dep_dataset.setAutomaticallyDisableSliceSpacing(false);
        d_dep_dataset.setSliceSpace(1f);
        d_dep_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        d_dep_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        d_dep_dataset.setValueTextSize(18f);
        d_dep_data = new PieData(d_dep_dataset);
        d_dep.setNoDataText(getString(R.string.no_data));
        d_dep.setDrawHoleEnabled(true);
        d_dep.setHoleRadius(1f);
        d_dep.setData(d_dep_data);
        d_dep.setDrawRoundedSlices(false);
        d_dep.setDrawSlicesUnderHole(false);
        d_dep.setDrawCenterText(false);
        d_dep.setTransparentCircleRadius(5f);
        d_dep.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        d_dep.getDescription().setEnabled(false);
        d_dep.setNoDataTextColor(R.color.cont_red);
        d_dep.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (d_dep_items.isEmpty())
            d_dep.setData(null);
        d_dep.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void fillBarChat() {
        if (!xray_items.isEmpty()) {
            ArrayList<BarEntry> list = new ArrayList<>();
            list.add(new BarEntry(1, xray_items.get(0).getPrice()));
            BarDataSet barDataSet = new BarDataSet(list, xray_items.get(0).getArabic());
            ArrayList<BarEntry> list2 = new ArrayList<>();
            list2.add(new BarEntry(2, xray_items.get(1).getPrice()));
            BarDataSet barDataSet2 = new BarDataSet(list2, xray_items.get(1).getArabic());
            ArrayList<BarEntry> list3 = new ArrayList<>();
            list3.add(new BarEntry(3, xray_items.get(2).getPrice()));
            BarDataSet barDataSet3 = new BarDataSet(list3, xray_items.get(2).getArabic());
            ArrayList<BarEntry> list4 = new ArrayList<>();
            list4.add(new BarEntry(4, xray_items.get(3).getPrice()));
            BarDataSet barDataSet4 = new BarDataSet(list4, xray_items.get(3).getArabic());
            ArrayList<BarEntry> list5 = new ArrayList<>();
            list5.add(new BarEntry(5, xray_items.get(4).getPrice()));
            BarDataSet barDataSet5 = new BarDataSet(list5, xray_items.get(4).getArabic());
            barDataSet.setColor(Color.rgb(193, 37, 82));
            barDataSet2.setColor(Color.rgb(255, 102, 0));
            barDataSet3.setColor(Color.rgb(245, 199, 0));
            barDataSet4.setColor(Color.rgb(106, 150, 31));
            barDataSet5.setColor(Color.rgb(179, 100, 53));
            barChart.getXAxis().setEnabled(false);
            BarData barData = new BarData(barDataSet, barDataSet2, barDataSet3, barDataSet4, barDataSet5);
            barChart.setData(barData);
        }
        barChart.setFitBars(true);
        barChart.getBarData();
        barChart.setNoDataText(getString(R.string.no_data));
        barChart.setClickable(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setNoDataTextColor(R.color.cont_red);
        barChart.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (xray_items.isEmpty())
            barChart.setData(null);
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //  Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        barChart.setBackgroundColor(Color.WHITE);
        barChart.getDescription().setText("");

    }

    private void Top5Opra() { /// 1
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < opr_items.size(); i++) {
            test1 = "الخدمة : " + opr_items.get(i).getArabic() + "\n" + "إجمالي القيمة  : " + opr_items.get(i).getPrice();
            pieEntryArrayList.add(new PieEntry(opr_items.get(i).getPrice(), opr_items.get(i).getArabic(), (test1)));
        }
        opr_dataset = new PieDataSet(pieEntryArrayList, "");
        opr_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        opr_dataset.setAutomaticallyDisableSliceSpacing(false);
        opr_dataset.setSliceSpace(1f);
        opr_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        opr_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        opr_dataset.setValueTextSize(18f);
        opr_data = new PieData(opr_dataset);
        pai2.setNoDataText(getString(R.string.no_data));
        pai2.setDrawHoleEnabled(true);
        pai2.setHoleRadius(1f);
        pai2.setData(opr_data);
        pai2.setDrawRoundedSlices(false);
        pai2.setDrawSlicesUnderHole(false);
        pai2.setDrawCenterText(false);
        pai2.setTransparentCircleRadius(5f);
        pai2.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        pai2.getDescription().setEnabled(false);
        pai2.setNoDataTextColor(R.color.cont_red);
        pai2.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (opr_items.isEmpty())
            pai2.setData(null);
        pai2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void Top5XRAY() { // 2
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < xray_items.size(); i++) {
            test1 = "الخدمة : " + xray_items.get(i).getArabic() + "\n" + "العدد : ";
            pieEntryArrayList.add(new PieEntry(xray_items.get(i).getPrice(), xray_items.get(i).getArabic(), (test1.concat("" + xray_items.get(i).getPrice()))));
        }
        xray_dataset = new PieDataSet(pieEntryArrayList, "");
        xray_dataset.setValues(pieEntryArrayList);
        xray_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        xray_dataset.setAutomaticallyDisableSliceSpacing(false);
        xray_dataset.setSliceSpace(1f);
        xray_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        xray_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        xray_dataset.setValueTextSize(18f);
        xray_data = new PieData(xray_dataset);
        pai.setNoDataText(getString(R.string.no_data));
        pai.setDrawHoleEnabled(true);
        pai.setHoleRadius(1f);
        pai.setData(xray_data);
        pai.setDrawRoundedSlices(false);
        pai.setDrawSlicesUnderHole(false);
        pai.setDrawCenterText(false);
        pai.setTransparentCircleRadius(5f);
        pai.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        pai.getDescription().setEnabled(false);
        pai.setNoDataTextColor(R.color.cont_red);
        pai.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (xray_items.isEmpty())
            pai.setData(null);
        pai.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void Top5NIsa() { // 2
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < nisa_items.size(); i++) {
            test1 = "الخدمة : " + nisa_items.get(i).getArabic() + "\n" + "إجمالي القيمة : ";
            pieEntryArrayList.add(new PieEntry(nisa_items.get(i).getPrice(), nisa_items.get(i).getArabic(), (test1.concat("" + nisa_items.get(i).getPrice()))));
        }
        nisa_dataSet = new PieDataSet(pieEntryArrayList, "");
        nisa_dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        nisa_dataSet.setAutomaticallyDisableSliceSpacing(false);
        nisa_dataSet.setSliceSpace(1f);
        nisa_dataSet.setValueTextColor(Color.parseColor("#ffffff"));
        nisa_dataSet.setValueLineColor(Color.parseColor("#ffffff"));
        nisa_dataSet.setValueTextSize(18f);
        nisa_data = new PieData(nisa_dataSet);

        nisa_pie.setNoDataText(getString(R.string.no_data));
        nisa_pie.setDrawHoleEnabled(true);
        nisa_pie.setHoleRadius(1f);
        nisa_pie.setData(nisa_data);
        nisa_pie.setDrawRoundedSlices(false);
        nisa_pie.setDrawSlicesUnderHole(false);
        nisa_pie.setDrawCenterText(false);
        nisa_pie.setTransparentCircleRadius(5f);
        nisa_pie.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        nisa_pie.getDescription().setEnabled(false);
        nisa_pie.setNoDataTextColor(R.color.cont_red);
        nisa_pie.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (nisa_items.isEmpty())
            nisa_pie.setData(null);
        nisa_pie.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void Top5Iwa() { // 2
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < iwa_items.size(); i++) {
            test1 = "الخدمة : " + iwa_items.get(i).getArabic() + "\n" + "إجمالي القيمة : ";
            pieEntryArrayList.add(new PieEntry(iwa_items.get(i).getPrice(), iwa_items.get(i).getArabic(), (test1.concat("" + iwa_items.get(i).getPrice()))));
        }
        iwa_dataset = new PieDataSet(pieEntryArrayList, "");
        iwa_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        iwa_dataset.setAutomaticallyDisableSliceSpacing(false);
        iwa_dataset.setSliceSpace(1f);
        iwa_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        iwa_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        iwa_dataset.setValueTextSize(18f);
        iwa_data = new PieData(iwa_dataset);
        iwa_pie.setNoDataText(getString(R.string.no_data));
        iwa_pie.setDrawHoleEnabled(true);
        iwa_pie.setHoleRadius(1f);
        iwa_pie.setData(iwa_data);
        iwa_pie.setDrawRoundedSlices(false);
        iwa_pie.setDrawSlicesUnderHole(false);
        iwa_pie.setDrawCenterText(false);
        iwa_pie.setTransparentCircleRadius(5f);
        iwa_pie.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        iwa_pie.getDescription().setEnabled(false);
        iwa_pie.setNoDataTextColor(R.color.cont_red);
        iwa_pie.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (iwa_items.isEmpty())
            iwa_pie.setData(null);
        iwa_pie.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void Top5Polia() { // 2
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < polia_items.size(); i++) {
            test1 = "الخدمة : " + polia_items.get(i).getArabic() + "\n" + "إجمالي القيمة : ";
            pieEntryArrayList.add(new PieEntry(polia_items.get(i).getPrice(), polia_items.get(i).getArabic(), (test1.concat("" + polia_items.get(i).getPrice()).replace(",", "."))));
        }
        polia_dataset = new PieDataSet(pieEntryArrayList, "");

        polia_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        polia_dataset.setAutomaticallyDisableSliceSpacing(false);
        polia_dataset.setSliceSpace(1f);
        polia_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        polia_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        polia_dataset.setValueTextSize(18f);
        polia_data = new PieData(polia_dataset);
        polia_pie.setNoDataText(getString(R.string.no_data));
        polia_pie.setDrawHoleEnabled(true);
        polia_pie.setHoleRadius(1f);
        polia_pie.setData(polia_data);
        polia_pie.setDrawRoundedSlices(false);
        polia_pie.setDrawSlicesUnderHole(false);
        polia_pie.setDrawCenterText(false);
        polia_pie.setTransparentCircleRadius(5f);
        polia_pie.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        polia_pie.getDescription().setEnabled(false);
        polia_pie.setNoDataTextColor(R.color.cont_red);
        polia_pie.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (polia_items.isEmpty())
            polia_pie.setData(null);
        polia_pie.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void Top5Hart() { // 2
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < hartopr_items.size(); i++) {
            test1 = "الخدمة : " + hartopr_items.get(i).getArabic() + "\n" + "إجمالي القيمة : ";
            pieEntryArrayList.add(new PieEntry(hartopr_items.get(i).getPrice(), hartopr_items.get(i).getArabic(), (test1.concat("" + hartopr_items.get(i).getPrice()).replace(",", "."))));
        }
        hartopr_dataset = new PieDataSet(pieEntryArrayList, "");
        hartopr_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        hartopr_dataset.setAutomaticallyDisableSliceSpacing(false);
        hartopr_dataset.setSliceSpace(1f);
        hartopr_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        hartopr_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        hartopr_dataset.setValueTextSize(18f);
        hartopr_data = new PieData(hartopr_dataset);
        hart_pie.setData(hartopr_data);
        hart_pie.setNoDataText(getString(R.string.no_data));
        hart_pie.setNoDataTextColor(R.color.cont_red);
        hart_pie.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (hartopr_items.isEmpty())
            hart_pie.setData(null);
        hart_pie.setDrawHoleEnabled(true);
        hart_pie.setHoleRadius(1f);


        hart_pie.setDrawRoundedSlices(false);
        hart_pie.setDrawSlicesUnderHole(false);
        hart_pie.setDrawCenterText(false);
        hart_pie.setTransparentCircleRadius(5f);
        hart_pie.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        hart_pie.getDescription().setEnabled(false);
        hart_pie.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    public void FillData() {
        if (hasData) {
            switch (comp_type) {
                case 1:
                case 7:
                case 8:
                    Top5Opra();
                    Top5XRAY();
                    Top5NIsa();
                    Top5Iwa();
                    Top5Polia();
                    Top5Hart();
                    fillBarChat();
                    break;
                case 2:
                    P_Top5Awram();
                    P_Top5Drug();
                    P_Top5Mozmna();
                    break;
                case 6:
                    DTop5SERV();
                    DTop5DEP();
                    break;
            }
            dailog.dismiss();

        } else {
            NoInternet();
        }

    }

    private void P_Top5Drug() { /// 1
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < p_drug_items.size(); i++) {
            test1 = "الخدمة : " + p_drug_items.get(i).getArabic() + "\n" + "إجمالي القيمة  : " + p_drug_items.get(i).getPrice();
            pieEntryArrayList.add(new PieEntry(p_drug_items.get(i).getPrice(), p_drug_items.get(i).getArabic(), (test1)));
        }
        p_drug_dataset = new PieDataSet(pieEntryArrayList, "");
        p_drug_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        p_drug_dataset.setAutomaticallyDisableSliceSpacing(false);
        p_drug_dataset.setSliceSpace(1f);
        p_drug_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        p_drug_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        p_drug_dataset.setValueTextSize(18f);
        p_drug_data = new PieData(p_drug_dataset);
        p_drug.setNoDataText(getString(R.string.no_data));
        p_drug.setDrawHoleEnabled(true);
        p_drug.setHoleRadius(1f);
        p_drug.setData(p_drug_data);
        p_drug.setDrawRoundedSlices(false);
        p_drug.setDrawSlicesUnderHole(false);
        p_drug.setDrawCenterText(false);
        p_drug.setTransparentCircleRadius(5f);
        p_drug.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        p_drug.getDescription().setEnabled(false);
        p_drug.setNoDataTextColor(R.color.cont_red);
        p_drug.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (p_drug_items.isEmpty())
            p_drug.setData(null);
        p_drug.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void P_Top5Mozmna() { /// 1
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < p_mozmna_items.size(); i++) {
            test1 = "الخدمة : " + p_mozmna_items.get(i).getArabic() + "\n" + "إجمالي القيمة  : " + p_mozmna_items.get(i).getPrice();
            pieEntryArrayList.add(new PieEntry(p_mozmna_items.get(i).getPrice(), p_mozmna_items.get(i).getArabic(), (test1)));
        }
        p_mozmna_dataset = new PieDataSet(pieEntryArrayList, "");
        p_mozmna_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        p_mozmna_dataset.setAutomaticallyDisableSliceSpacing(false);
        p_mozmna_dataset.setSliceSpace(1f);
        p_mozmna_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        p_mozmna_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        p_mozmna_dataset.setValueTextSize(18f);
        p_mozmna_data = new PieData(p_mozmna_dataset);
        p_mozmna.setNoDataText(getString(R.string.no_data));
        p_mozmna.setDrawHoleEnabled(true);
        p_mozmna.setHoleRadius(1f);
        p_mozmna.setData(p_mozmna_data);
        p_mozmna.setDrawRoundedSlices(false);
        p_mozmna.setDrawSlicesUnderHole(false);
        p_mozmna.setDrawCenterText(false);
        p_mozmna.setTransparentCircleRadius(5f);
        p_mozmna.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        p_mozmna.getDescription().setEnabled(false);
        p_mozmna.setNoDataTextColor(R.color.cont_red);
        p_mozmna.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (p_mozmna_items.isEmpty())
            p_mozmna.setData(null);
        p_mozmna.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void P_Top5Awram() { /// 1
        String test1 = "";
        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();
        for (int i = 0; i < p_awram_items.size(); i++) {
            test1 = "الخدمة : " + p_awram_items.get(i).getArabic() + "\n" + "إجمالي القيمة  : " + p_awram_items.get(i).getPrice();
            pieEntryArrayList.add(new PieEntry(p_awram_items.get(i).getPrice(), p_awram_items.get(i).getArabic(), (test1)));
        }
        p_awram_dataset = new PieDataSet(pieEntryArrayList, "");
        p_awram_dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        p_awram_dataset.setAutomaticallyDisableSliceSpacing(false);
        p_awram_dataset.setSliceSpace(1f);
        p_awram_dataset.setValueTextColor(Color.parseColor("#ffffff"));
        p_awram_dataset.setValueLineColor(Color.parseColor("#ffffff"));
        p_awram_dataset.setValueTextSize(18f);
        p_awram_data = new PieData(p_awram_dataset);
        p_awram.setNoDataText(getString(R.string.no_data));
        p_awram.setDrawHoleEnabled(true);
        p_awram.setHoleRadius(1f);
        p_awram.setData(p_awram_data);
        p_awram.setDrawRoundedSlices(false);
        p_awram.setDrawSlicesUnderHole(false);
        p_awram.setDrawCenterText(false);
        p_awram.setTransparentCircleRadius(5f);
        p_awram.setEntryLabelTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        p_awram.getDescription().setEnabled(false);
        p_awram.setNoDataTextColor(R.color.cont_red);
        p_awram.setNoDataTextTypeface(ResourcesCompat.getFont(getActivity(), R.font.pung));
        if (p_awram_items.isEmpty())
            p_awram.setData(null);
        p_awram.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Messages.Dialog(e.getData().toString(), getActivity());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    @Override
    public void onCLick2(int p) {
        Log.d(TAG, "CLiked INDEX > " + selected_contract);
        if (isOk(list.get(p).getId())) {
            con_type.setText(list.get(p).getTxt());
            selected_contract = list.get(p).getId();
            index = p;
            bottomSheetDialog.dismiss();
            putPrices(true);
        } else {
            Messages.Dialog(getString(R.string.no_data_con) + list.get(p).getTxt(), getActivity());
            bottomSheetDialog.dismiss();
        }
    }

    public boolean isOk(int p) {
        for (int i = 0; i < pricesDataItems.size(); i++) {
            Log.d(TAG, "FOR > " + p + " | " + pricesDataItems.get(i).getContractID());
            if (pricesDataItems.get(i).getContractID() == p)
                return true;
        }
        return false;
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
                if (null != getActivity()) {
                    if (null != s) {
                        if (urlWebService.contains(OPR_URL)) {
                            try {
                                loadXOpr(s);
                            } catch (JSONException e) {
                                Log.d(TAG, "ERROR ! OPR_URL > " + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(HART_URL)) {
                            try {
                                loadHart(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > HART_URL" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(D_DEP_URL)) {
                            try {
                                loadD_DEP(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > loadD_DEP" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(D_SERVICS_URL)) {
                            try {
                                loadD_SERV(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > loadD_SERV" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(POLIA_URL)) {
                            try {
                                loadPolia(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > POLIA_URL" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(NISA_URL)) {
                            try {
                                loadNisa(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > NISA_URL" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(IWA_URL)) {
                            try {
                                loadIwa(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > IWA_URL" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(XRAY_URL)) {
                            try {
                                loadXrays(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > XRAY_URL" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(P_AMAZON_URL)) {
                            try {
                                loadP_Mozmna(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > loadP_Mozmna" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(P_AWRAM_URL)) {
                            try {
                                loadP_Awram(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > P_AWRAM_URL" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(P_DRUG_URL)) {
                            try {
                                loadP_Drugs(s);
                            } catch (Exception e) {
                                Log.d(TAG, "ERROR ! > P_DRUG_URL" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else if (urlWebService.contains(DATA_URL) || urlWebService.contains(DATA_COMPANY_URL)) {
                            try {
                                Log.d(TAG, "> DATA_URL >" + s);
                                loadIntoList(s);
                                if (s.length() > 10)
                                    hasData = true;
                            } catch (JSONException e) {
                                Log.d(TAG, "ERROR ! > DATA_URL " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    } else {
                        dailog.dismiss();
                        NoInternet();
                    }
                } else {
                    cancel(true);
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
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
                    Log.d(TAG, sb.toString());
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.d(TAG, statics.getCompanyID() + " /// " + e.getMessage());
                    return null;
                }
            }
        }
        GetJSON_Hospital getJSON = new GetJSON_Hospital();
        getJSON.execute();
    }
}
