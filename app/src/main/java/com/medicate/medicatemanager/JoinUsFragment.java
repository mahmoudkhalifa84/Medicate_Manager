package com.medicate.medicatemanager;

import android.app.Dialog;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class JoinUsFragment extends Fragment implements ItemsTextAdapter.onCLickLis2 , Messages.OkClicked{
    View view;
    public static final String TAG = "JOINUS";
    Statics statics;
    LoadingDiag loadingDiag;
    LinearLayout doctor, phar, clinic, dig;
    String e_1_name,e_2_name,e_3_name,e_4_name,a_1_name,a_2_name,a_3_name,a_4_name;
    BottomSheetDialog bottomSheetDialog;
    Context context;
    int clicked = 0;
    String[] country = new String[]{
            "Afghanistan",
            "Albania",
            "Algeria",
            "American Samoa",
            "Andorra",
            "Angola",
            "Anguilla",
            "Antarctica",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Aruba",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bermuda",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "British Indian Ocean Territory",
            "British Virgin",
            "Brunei",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cambodia",
            "Cameroon ",
            "Canada",
            "Cape Verde",
            "Cayman Islands",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Christmas Island",
            "Cocos Islands",
            "Colombia",
            "Comoros",
            "Cook Islands",
            "Costa Rica",
            "Croatia",
            "Cuba ",
            "Curacao",
            "Cyprus ",
            "Czech Republic",
            "Democratic Republic of the Congo",
            "Denmark ",
            "Djibouti ",
            "Dominica ",
            "Dominican Republic",
            "East Timor",
            "Ecuador",
            "Egypt ",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea ",
            "Estonia ",
            "Ethiopia ",
            "Falkland Islands ",
            "Faroe Islands ",
            "Fiji ",
            "Finland ",
            "France",
            "French Polynesia",
            "Gabon ",
            "Gambia",
            "Georgia ",
            "Germany ",
            "Ghana ",
            "Gibraltar",
            "Greece ",
            "Greenland",
            "Grenada ",
            "Guam ",
            "Guatemala",
            "Guernsey ",
            "Guinea ",
            "Guinea-Bissau",
            "Guyana",
            "Haiti ",
            "Honduras ",
            "Hong Kong ",
            "Hungary ",
            "Iceland ",
            "India ",
            "Indonesia ",
            "Iran ",
            "Iraq ",
            "Ireland ",
            "Isle of Man ",
            "Israel",
            "Italy ",
            "Ivory Coast",
            "Jamaica ",
            "Japan ",
            "Jersey ",
            "Jordan ",
            "Kazakhstan",
            "Kenya ",
            "Kiribati",
            "Kosovo",
            "Kuwait ",
            "Kyrgyzstan",
            "Laos ",
            "Latvia",
            "Lebanon ",
            "Lesotho ",
            "Liberia ",
            "Libya ",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macau ",
            "Macedonia",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives ",
            "Mali ",
            "Malta ",
            "Marshall Islands ",
            "Mauritania",
            "Mauritius ",
            "Mayotte ",
            "Mexico ",
            "Micronesia",
            "Moldova ",
            "Monaco ",
            "Mongolia",
            "Montenegro",
            "Montserrat",
            "Morocco ",
            "Mozambique",
            "Myanmar ",
            "Namibia ",
            "Nauru ",
            "Nepal ",
            "Netherlands",
            "Netherlands Antilles",
            "New Caledonia ",
            "New Zealand ",
            "Nicaragua ",
            "Niger ",
            "Nigeria",
            "Niue ",
            "North Korea",
            "Northern Mariana Islands",
            "Norway ",
            "Oman ",
            "Pakistan",
            "Palau ",
            "Palestine",
            "Panama ",
            "Papua New Guinea",
            "Paraguay ",
            "Peru ",
            "Philippines ",
            "Pitcairn ",
            "Poland ",
            "Portugal",
            "Puerto Rico",
            "Qatar ",
            "Republic of the Congo",
            "Reunion ",
            "Romania ",
            "Russia",
            "Rwanda ",
            "Saint Barthelemy",
            "Saint Helena",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Martin",
            "Saint Pierre and Miquelon",
            "Saint Vincent and the Grenadines",
            "Samoa ",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia ",
            "Senegal ",
            "Serbia ",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Sint Maarten",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia ",
            "South Africa",
            "South Korea ",
            "South Sudan ",
            "Spain ",
            "Sri Lanka",
            "Sudan ",
            "Suriname",
            "Svalbard and Jan Mayen ",
            "Swaziland ",
            "Sweden ",
            "Switzerland",
            "Syria ",
            "Taiwan",
            "Tajikistan",
            "Tanzania ",
            "Thailand ",
            "Togo ",
            "Tokelau ",
            "Tonga ",
            "Trinidad and Tobago ",
            "Tunisia ",
            "Turkey ",
            "Turkmenistan",
            "Turks and Caicos Islands",
            "Tuvalu ",
            "U.S. Virgin Islands",
            "Uganda ",
            "Ukraine ",
            "United Arab Emirates",
            "United Kingdom ",
            "United States ",
            "Uruguay ",
            "Uzbekistan ",
            "Vanuatu ",
            "Vatican ",
            "Venezuela ",
            "Vietnam ",
            "Wallis and Futuna",
            "Western Sahara",
            "Yemen ",
            "Zambia",
            "Zimbabwe"};

    String[] code = new String[]{"93",
            "355",
            "213",
            "1-684",
            "376",
            "244",
            "1-264",
            "672",
            "1-268",
            "54",
            "374",
            "297",
            "61",
            "43",
            "994",
            "1-242",
            "973",
            "880",
            "1-246",
            "375",
            "32",
            "501",
            "229",
            " 1-441",
            "975",
            "591",
            " 387",
            "267",
            "55",
            "246",
            "1-284",
            "673",
            "359",
            "226",
            "257",
            " 855",
            "237",
            "1",
            "238",
            " 1-345",
            "236",
            "235",
            " 56",
            "86",
            " 61",
            "61",
            "57",
            "269",
            "682",
            " 506",
            "385",
            "53",
            "599",
            "357",
            "420",
            " 243",
            " 45",
            "253",
            "1-767",
            "1-809",
            "670",
            "593",
            "20",
            "503",
            "240",
            "291",
            "372",
            "251",
            "500",
            "298",
            "679",
            "358",
            "33",
            "689",
            "241",
            "220",
            "995",
            "49",
            "233",
            " 350",
            "30",
            "299",
            "1-473",
            "1-671",
            "502",
            "44-1481",
            "224",
            "245",
            "592",
            "509",
            "504",
            "852",
            "36",
            "354",
            "91",
            "62",
            "98",
            "964",
            "353",
            "44-1624",
            "972",
            "39",
            "225",
            "1-876",
            "81",
            "44-1534",
            "962",
            "7",
            "254",
            "686",
            "383",
            "965",
            "996",
            "856",
            "371",
            "961",
            "266",
            "231",
            "218",
            "423",
            "370",
            "352",
            "853",
            "389",
            "261",
            "265",
            "60",
            "960",
            "223",
            "356",
            "692",
            "222",
            "230",
            "262",
            "52",
            "691",
            "373",
            "377",
            "976",
            "382",
            "1-664",
            "212",
            "258",
            "95",
            "264",
            "674",
            "977",
            "31",
            "599",
            "687",
            "64",
            "505",
            "227",
            "234",
            "683",
            "850",
            "1-670",
            "47",
            "968",
            "92",
            "680",
            "970",
            "507",
            "675",
            "595",
            "51",
            "63",
            "64",
            "48",
            "351",
            "1-787",
            "974",
            "242",
            "262",
            "40",
            "7",
            "250",
            "590",
            "290",
            "1-869",
            "1-758",
            "590",
            "508",
            "1-784",
            "685",
            "378",
            "239",
            "966",
            "221",
            "381",
            "248",
            "232",
            "65",
            "1-721",
            "421",
            "386",
            "677",
            "252",
            "27",
            "82",
            "211",
            "34",
            "94",
            "249",
            "597",
            "47",
            "268",
            "46",
            "41",
            "963",
            "886",
            "992",
            "255",
            "66",
            "228",
            "690",
            "676",
            "1-868",
            "216",
            "90",
            "993",
            "1-649",
            "688",
            "1-340",
            "256",
            "380",
            "971",
            "44",
            "1",
            "598",
            "998",
            "678",
            "379",
            "58",
            "84",
            "681",
            "212",
            "967",
            "260",
            "263"};
    List<TextItems> items;
    ItemsTextAdapter adapter;
    RecyclerView recyclerView;
    TextInputEditText work_country, j_doc_jansia, gendar, degray, shpa, count;
    TextView clinc_img, doc_img, dig_img, phar_img;
    JoinUsItems person;
    // FOR DIG
    TextInputEditText j_dig_name, j_dig_admin_name, j_dig_admin_email, j_dig_admin_phone, j_dig_erya, j_dig_city, j_dig_country;
    TextView j_dig_ok;
    // FOR PHAR
    TextInputEditText j_p_name, j_p_admin_name, j_p_admin_email, j_p_admin_phone, j_p_country, j_p_city, j_p_arya;
    TextView j_p_ok;
    // FOR CLINIC
    TextInputEditText j_c_name, j_c_admin_name, j_c_admin_email, j_c_admin_phone, j_c_country, j_c_city, j_c_arya;
    TextView j_c_ok;
    // FOR Doc
    TextInputEditText j_doc_name_ar, j_doc_name_en, j_doc_usr_name, j_doc_email, j_doc_work_cont, j_doc_sex , j_doc_work_num, j_doc_deggray, j_doc_univarsty,
            j_doc_univarsty_year, j_doc_shap , j_doc_about , j_doc_country , j_doc_city , j_doc_place , j_doc_dob_year , j_doc_dob_month , j_doc_dob_day;
    CheckBox j_doc_lang_en , j_doc_lang_ar , j_doc_lang_fr , j_doc_lang_tr ;
    TextView j_doc_ok;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.join_us_layout, container, false);
        statics = new Statics(getActivity());
        person = new JoinUsItems();
        recyclerView = new RecyclerView(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        items = new ArrayList<>();
        context = getActivity();
        loadingDiag = new LoadingDiag(context,R.style.PauseDialog);
        ///////////////////////
       /* clinc_img = view.findViewById(R.id.join_phto_cli);
        doc_img = view.findViewById(R.id.join_phto_doc);
        dig_img = view.findViewById(R.id.join_photo_dig);
        phar_img = view.findViewById(R.id.join_photo_phar);
        clinc_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choase_by_pic();
            }
        });
        doc_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choase_by_pic();
            }
        });
        dig_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choase_by_pic();
            }
        });
        phar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choase_by_pic();
            }
        });*/
        //////////////////////
        work_country = view.findViewById(R.id.j_doc_work_cont);
        j_doc_jansia = view.findViewById(R.id.j_doc_jansia);
        gendar = view.findViewById(R.id.j_doc_sex);
        degray = view.findViewById(R.id.j_doc_deggray);
        shpa = view.findViewById(R.id.j_doc_shap);
        count = view.findViewById(R.id.j_doc_country);
        work_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClicked(work_country);
            }
        });
        j_doc_jansia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClicked(j_doc_jansia);
            }
        });
        gendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClicked(gendar);
            }
        });
        degray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClicked(degray);
            }
        });
        shpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClicked(shpa);
            }
        });
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClicked(count);
            }
        });
        ///////////////////////
        doctor = view.findViewById(R.id.ll_for_doctors);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setCancelable(true);
        clinic = view.findViewById(R.id.ll_for_clinic);
        dig = view.findViewById(R.id.ll_for_dig);
        phar = view.findViewById(R.id.ll_for_phar);
        doctor.setVisibility(View.GONE);
        clinic.setVisibility(View.GONE);
        dig.setVisibility(View.GONE);
        phar.setVisibility(View.GONE);
        switch (statics.restorePrefData("join_place")) {
            case "d":
                doctor.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
                doctor.setVisibility(View.VISIBLE);
                setupDoc();
                break;
            case "p":
                phar.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
                phar.setVisibility(View.VISIBLE);
                setupPhar();
                break;
            case "g":
                dig.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
                dig.setVisibility(View.VISIBLE);
                setupDig();
                break;
            case "c":
                clinic.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
                clinic.setVisibility(View.VISIBLE);
                setupCLinic();
                break;
        }
        ///  ok Button's handler
        view.findViewById(R.id.imageView2).setOnClickListener((q)-> getActivity().onBackPressed());
        return view;
    }

    private void setupDoc() {
        j_doc_name_ar = view.findViewById(R.id.j_doc_name_ar);
        j_doc_name_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PikName(1);
            }
        });
        j_doc_name_en = view.findViewById(R.id.j_doc_name_en);
        j_doc_name_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PikName(2);
            }
        });
        j_doc_usr_name = view.findViewById(R.id.j_doc_usr_name);
        j_doc_email = view.findViewById(R.id.j_doc_email);
        j_doc_work_cont = view.findViewById(R.id.j_doc_work_cont);
        j_doc_sex = view.findViewById(R.id.j_doc_sex);
        j_doc_work_num = view.findViewById(R.id.j_doc_work_num);
        j_doc_deggray = view.findViewById(R.id.j_doc_deggray);
        j_doc_univarsty = view.findViewById(R.id.j_doc_univarsty);
        j_doc_univarsty_year = view.findViewById(R.id.j_doc_univarsty_year);
        j_doc_shap = view.findViewById(R.id.j_doc_shap);
        j_doc_about = view.findViewById(R.id.j_doc_about);
        j_doc_country = view.findViewById(R.id.j_doc_country);
        j_doc_place = view.findViewById(R.id.j_doc_place);
        j_doc_dob_year = view.findViewById(R.id.j_doc_dob_year);
        j_doc_dob_month = view.findViewById(R.id.j_doc_dob_month);
        j_doc_jansia = view.findViewById(R.id.j_doc_jansia);
        j_doc_dob_day = view.findViewById(R.id.j_doc_dob_day);
        j_doc_city = view.findViewById(R.id.j_doc_city);
        j_doc_lang_en = view.findViewById(R.id.j_doc_lang_en);
        j_doc_lang_ar = view.findViewById(R.id.j_doc_lang_ar);
        j_doc_lang_fr = view.findViewById(R.id.j_doc_lang_fr);
        j_doc_lang_tr = view.findViewById(R.id.j_doc_lang_tr);
        j_doc_ok = view.findViewById(R.id.j_doc_ok);
        j_doc_ok.setOnClickListener(a -> JoinSend("d"));
    }

    private void PikName(int i) {
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog );
        dialog.setContentView(R.layout.name_entring);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (i == 1) { // ar
            ((TextView)dialog.findViewById(R.id.textView38)).setText(getString(R.string.ar_ent_name));
            dialog.findViewById(R.id.textView39).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a_1_name = ((TextInputEditText) dialog.findViewById(R.id.txt_f_name)).getText().toString().trim();
                    a_2_name = ((TextInputEditText) dialog.findViewById(R.id.txt_s_name)).getText().toString().trim();
                    a_3_name = ((TextInputEditText) dialog.findViewById(R.id.txt_t_name)).getText().toString().trim();
                    a_4_name = ((TextInputEditText) dialog.findViewById(R.id.txt_n_name)).getText().toString().trim();
                    if (!a_1_name.isEmpty() && !a_2_name.isEmpty() &&!a_3_name.isEmpty() &&!a_4_name.isEmpty()){
                        dialog.dismiss();
                        j_doc_name_ar.setText(a_1_name.concat(" ").concat(a_2_name).concat(" ").concat(a_3_name).concat(" ").concat(a_4_name));
                    }else{
                        ShowCenterTost(getString(R.string.ar_ent_name));
                    }
                }
            });
        }
        else {
            ((TextView)dialog.findViewById(R.id.textView38)).setText(getString(R.string.en_name_e));
            dialog.findViewById(R.id.textView39).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e_1_name = ((TextInputEditText) dialog.findViewById(R.id.txt_f_name)).getText().toString().trim();
                    e_2_name = ((TextInputEditText) dialog.findViewById(R.id.txt_s_name)).getText().toString().trim();
                    e_3_name = ((TextInputEditText) dialog.findViewById(R.id.txt_t_name)).getText().toString().trim();
                    e_4_name = ((TextInputEditText) dialog.findViewById(R.id.txt_n_name)).getText().toString().trim();
                    if (!e_1_name.isEmpty() && !e_2_name.isEmpty() &&!e_3_name.isEmpty() &&!e_4_name.isEmpty()){
                        dialog.dismiss();
                        j_doc_name_en.setText(e_1_name.concat(" ").concat(e_2_name).concat(" ").concat(e_3_name).concat(" ").concat(e_4_name));
                    }else{
                        ShowCenterTost(getString(R.string.en_name_e));
                    }
                }
            });
        }
    }
    private void ShowCenterTost(String s){
        Toast toast = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    private void setupDig() {
        j_dig_name = view.findViewById(R.id.j_dig_name);
        j_dig_admin_name = view.findViewById(R.id.j_dig_admin_name);
        j_dig_admin_email = view.findViewById(R.id.j_dig_admin_email);
        j_dig_admin_phone = view.findViewById(R.id.j_dig_admin_phone);
        j_dig_erya = view.findViewById(R.id.j_dig_erya);
        j_dig_city = view.findViewById(R.id.j_dig_city);
        j_dig_country = view.findViewById(R.id.j_dig_country);
        j_dig_ok = view.findViewById(R.id.j_dig_ok);
        j_dig_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinSend("g");
            }
        });
    }

    private void setupCLinic() {
        j_c_name = view.findViewById(R.id.j_c_name);
        j_c_admin_name = view.findViewById(R.id.j_c_admin_name);
        j_c_admin_email = view.findViewById(R.id.j_c_admin_email);
        j_c_admin_phone = view.findViewById(R.id.j_c_admin_phone);
        j_c_arya = view.findViewById(R.id.j_c_erya);
        j_c_city = view.findViewById(R.id.j_c_city);
        j_c_country = view.findViewById(R.id.j_c_country);
        j_c_ok = view.findViewById(R.id.j_c_ok);
        j_c_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinSend("c");
            }
        });
    }

    private void setupPhar() {
        j_p_name = view.findViewById(R.id.j_p_name);
        j_p_admin_name = view.findViewById(R.id.j_p_admin_name);
        j_p_admin_email = view.findViewById(R.id.j_p_admin_email);
        j_p_admin_phone = view.findViewById(R.id.j_p_admin_phone);
        j_p_arya = view.findViewById(R.id.j_p_arya);
        j_p_city = view.findViewById(R.id.j_p_city);
        j_p_country = view.findViewById(R.id.j_p_country);
        j_p_ok = view.findViewById(R.id.j_p_ok);
        j_p_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinSend("p");
            }
        });
    }

    private void JoinSend(String d) {
        switch (d){
            case "p" :
                person.setName(j_p_name.getText().toString().trim());
                person.setAdmin_name(j_p_admin_name.getText().toString().trim());
                person.setAdmin_email(j_p_admin_email.getText().toString().trim());
                person.setAdmin_phone(j_p_admin_phone.getText().toString().trim());
                person.setCountry(j_p_country.getText().toString().trim());
                person.setCity(j_p_city.getText().toString().trim());
                person.setErya(j_p_arya.getText().toString().trim());
                SendDrictEmail("صيدلية");
                break;
            case "g" :
                person.setName(j_dig_name.getText().toString().trim());
                person.setAdmin_name(j_dig_admin_name.getText().toString().trim());
                person.setAdmin_email(j_dig_admin_email.getText().toString().trim());
                person.setAdmin_phone(j_dig_admin_phone.getText().toString().trim());
                person.setCountry(j_dig_country.getText().toString().trim());
                person.setCity(j_dig_city.getText().toString().trim());
                person.setErya(j_dig_erya.getText().toString().trim());
                SendDrictEmail("مركز تشخيص");
                break;
            case "c" :
                person.setName(j_c_name.getText().toString().trim());
                person.setAdmin_name(j_c_admin_name.getText().toString().trim());
                person.setAdmin_email(j_c_admin_email.getText().toString().trim());
                person.setAdmin_phone(j_c_admin_phone.getText().toString().trim());
                person.setCountry(j_c_country.getText().toString().trim());
                person.setCity(j_c_city.getText().toString().trim());
                person.setErya(j_c_arya.getText().toString().trim());
                SendDrictEmail("مصحة");
                break;
            case "d" :
                person.setDoc_name_ar(j_doc_name_ar.getText().toString().trim());
                person.setDoc_name_en(j_doc_name_en.getText().toString().trim());
                person.setDoc_user_name(j_doc_usr_name.getText().toString().trim());
                person.setDoc_email(j_doc_email.getText().toString().trim());
                person.setDoc_work_country(j_doc_work_cont.getText().toString().trim());
                person.setDoc_sex(j_doc_sex.getText().toString().trim());
                person.setDoc_work_number(j_doc_work_num.getText().toString().trim());
                person.setDoc_degray(j_doc_deggray.getText().toString().trim());
                person.setDoc_univirsty(j_doc_univarsty.getText().toString().trim());
                person.setDoc_univirsty_year(j_doc_univarsty_year.getText().toString().trim());
                person.setDoc_sphashal(j_doc_shap.getText().toString().trim());
                person.setDoc_about(j_doc_about.getText().toString().trim());
                person.setDoc_country(j_doc_country.getText().toString().trim());
                person.setDoc_city(j_doc_city.getText().toString().trim());
                person.setDoc_erya(j_doc_place.getText().toString().trim());
                person.setJansia(j_doc_jansia.getText().toString().trim());
                person.setDoc_dob(j_doc_dob_year.getText().toString().trim() + " - " + j_doc_dob_month.getText().toString().trim() + " - " + j_doc_dob_day.getText().toString().trim());
                if (j_doc_lang_en.isChecked()) person.setDoc_en("نعم");
                if (j_doc_lang_ar.isChecked()) person.setDoc_ar("نعم");
                if (j_doc_lang_fr.isChecked()) person.setDoc_fr("نعم");
                if (j_doc_lang_tr.isChecked()) person.setDoc_tr("نعم");
                SendDrictEmailDoctor();
                break;
        }


    }

    private void SendDrictEmail(String type) {
        if (CheckConnection.isNetworkConnected(context)) {
            StringBuilder data = new StringBuilder();
            String ddata = "<p style=\"direction: rtl;\">" +
                    "نوع مزود الخدمة : " + type + "\n" +
                    "أسم المفوض : " + person.getAdmin_name() + "\n" +
                    "البريد الإلكتروني الخاص بالمفوض : " + person.getAdmin_email()+ "\n" +
                    "رقم الهاتف الخاص بالمفوض : " + person.getAdmin_phone() + "\n" +
                    "الدولة - المدينةk - المنطقة : " + person.getCountry() + " - " +  person.getCity() + " - " + person.getErya() + "\n" +
                    "تم ﻹرسال هذا الطلب من خلال تطبيق ميديكيت مانجر " + "\n" +
                    "</p>";
       /*     data.append("نوع مزود الخدمة : ").append("\n").append(type).append("\n\n");
            data.append("أسم المفوض : ").append("\n").append(person.getAdmin_name()).append("\n\n");
            data.append("البريد الإلكتروني الخاص بالمفوض : ").append("\n").append(person.getAdmin_email()).append("\n").append("\n");
            data.append("رقم الهاتف الخاص بالمفوض : ").append("\n").append(person.getAdmin_phone()).append("\n").append("\n");
            data.append("الدولة - المدينة - المنطقة : ").append("\n").append(person.getCountry()).append(" - ").append(person.getCity()).append(" - ").append(person.getErya()).append("\n").append("\n");
            data.append("تم ﻹرسال هذا الطلب من خلال تطبيق ميديكيت مانجر ");*/


            JavaMailAPI api = new JavaMailAPI(context, type, person, loadingDiag);
            api.execute();
        }else Messages.Dialog(getString(R.string.no_internet_con),context);
    }

    private void SendDrictEmailDoctor() {
        if (CheckConnection.isNetworkConnected(context)) {
            StringBuilder data = new StringBuilder();
            data.append("نوع مزود الخدمة : ").append("طبيب").append("\n\n");
            data.append("الاسم بالعربي : ").append(person.getDoc_name_ar()).append("\n\n");
            data.append("الاسم بلأنجليزية : ").append(person.getDoc_name_en()).append("\n\n");
            data.append("البريد الإلكتروني : ").append(person.getDoc_email()).append("\n\n");
            data.append("دولة مزاولة العمل : ").append(person.getDoc_work_country()).append("\n\n");
            data.append("الجنسية : ").append(person.getJansia()).append("\n\n");
            data.append("الجنس : ").append(person.getDoc_sex()).append("\n\n");
            data.append("رقم رخصة مزاولة العمل : ").append(person.getDoc_work_number()).append("\n\n");
            data.append("الدرجة العلمية : ").append(person.getDoc_degray()).append("\n\n");
            data.append("الجامعة المتخرج منها : ").append(person.getDoc_univirsty()).append("\n\n");
            data.append("سنة التخرج : ").append(person.getDoc_univirsty_year()).append("\n\n");
            data.append("التخصص الرئيسي : ").append(person.getDoc_sphashal()).append("\n\n");
            data.append("العنوان الحالي : ").append(person.getDoc_country()).append(" - ").append(person.getDoc_city()).append(" - ").append(person.getDoc_erya()).append("\n").append("\n");
            data.append("تاريخ الميلاد : ").append(person.getDoc_dob()).append("\n").append("\n");
            data.append("نبذة عن الطبيب : ").append(person.getDoc_about()).append("\n").append("\n");
            data.append("هل يجيد اللغة الأنجليزية : ").append(person.getDoc_en()).append("\n\n");
            data.append("هل يجيد اللغة العربية : ").append(person.getDoc_ar()).append("\n\n");
            data.append("هل يجيد اللغة الفرنسية : ").append(person.getDoc_fr()).append("\n\n");
            data.append("هل يجيد اللغة التركية : ").append(person.getDoc_tr()).append("\n\n");
            data.append("تم ﻹرسال هذا الطلب من خلال تطبيق ميديكيت مانجر \t");

        /*    JavaMailAPI api = new JavaMailAPI(context, "طبيب", data.toString(), loadingDiag);
            api.execute();*/
        }else Messages.Dialog(getString(R.string.no_internet_con),context);
    }

    public void choase_by_pic() {
        bottomSheetDialog.setContentView(R.layout.camera_or_galary);
        bottomSheetDialog.findViewById(R.id.c_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 123);
            }
        });
        bottomSheetDialog.findViewById(R.id.c_galry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 100);

            }
        });
        bottomSheetDialog.show();
    }

    public void setClicked(View view1) {
        view.clearFocus();
        if (recyclerView.getParent() != null) {
            ((ViewGroup) recyclerView.getParent()).removeView(recyclerView); // <- fix
        }
        items.clear();
        switch (view1.getId()) {
            case R.id.j_doc_work_cont:
                clicked = 1; ///////////
                for (int i = 0; i < country.length; i++)
                    items.add(new TextItems(" (" + code[i] + ") " + country[i]));
                adapter = new ItemsTextAdapter(getActivity(), items, this);
                recyclerView.setAdapter(adapter);
                bottomSheetDialog.setContentView(recyclerView);
                bottomSheetDialog.show();
                break;
            case R.id.j_doc_jansia:
                clicked = 2; ///////////
                for (int i = 0; i < country.length; i++)
                    items.add(new TextItems(country[i]));
                adapter = new ItemsTextAdapter(getActivity(), items, this);
                recyclerView.setAdapter(adapter);
                bottomSheetDialog.setContentView(recyclerView);
                bottomSheetDialog.show();
                break;
            case R.id.j_doc_sex:
                clicked = 3; ///////////
                items.add(new TextItems(getString(R.string.male)));
                items.add(new TextItems(getString(R.string.female)));
                adapter = new ItemsTextAdapter(getActivity(), items, this);
                recyclerView.setAdapter(adapter);
                bottomSheetDialog.setContentView(recyclerView);
                bottomSheetDialog.show();
                break;
            case R.id.j_doc_deggray:
                String[] array = getResources().getStringArray(R.array.doc_deggry);
                clicked = 4; ///////////
                for (int i = 0; i < array.length; i++)
                    items.add(new TextItems(array[i]));
                adapter = new ItemsTextAdapter(getActivity(), items, this);
                recyclerView.setAdapter(adapter);
                bottomSheetDialog.setContentView(recyclerView);
                bottomSheetDialog.show();
                break;
            case R.id.j_doc_shap:
                clicked = 5; ///////////
                recyclerView.setAdapter(addItems());
                bottomSheetDialog.setContentView(recyclerView);
                bottomSheetDialog.show();
                break;
            case R.id.j_doc_country:
                clicked = 6;
                for (int i = 0; i < country.length; i++)
                    items.add(new TextItems(country[i]));
                adapter = new ItemsTextAdapter(getActivity(), items, this);
                recyclerView.setAdapter(adapter);
                bottomSheetDialog.setContentView(recyclerView);
                bottomSheetDialog.show();
                break;
        }
    }

    private ItemsTextAdapter addItems() {
        items.add(new TextItems(getString(R.string.sp_1), R.drawable.s_1));
        items.add(new TextItems(getResources().getString(R.string.sp_2), R.drawable.s_2));
        items.add(new TextItems(getResources().getString(R.string.sp_3), R.drawable.s_3));
        items.add(new TextItems(getResources().getString(R.string.sp_19), R.drawable.s_19));
        items.add(new TextItems(getResources().getString(R.string.sp_4), R.drawable.s_4));
        items.add(new TextItems(getResources().getString(R.string.sp_5), R.drawable.s_5));
        items.add(new TextItems(getResources().getString(R.string.sp_6), R.drawable.s_6));
        items.add(new TextItems(getResources().getString(R.string.sp_7), R.drawable.s_7));
        items.add(new TextItems(getResources().getString(R.string.sp_8), R.drawable.s_8));
        items.add(new TextItems(getResources().getString(R.string.sp_9), R.drawable.s_9));
        items.add(new TextItems(getResources().getString(R.string.sp_10), R.drawable.s_10));
        items.add(new TextItems(getResources().getString(R.string.sp_11), R.drawable.s_11));
        items.add(new TextItems(getResources().getString(R.string.sp_12), R.drawable.s_12));
        items.add(new TextItems(getResources().getString(R.string.sp_13), R.drawable.s_13));
        items.add(new TextItems(getResources().getString(R.string.sp_14), R.drawable.s_14));
        items.add(new TextItems(getResources().getString(R.string.sp_15), R.drawable.s_15));
        items.add(new TextItems(getResources().getString(R.string.sp_16), R.drawable.s_16));
        items.add(new TextItems(getResources().getString(R.string.sp_17), R.drawable.s_17));
        items.add(new TextItems(getResources().getString(R.string.sp_18), R.drawable.s_18));
        items.add(new TextItems(getResources().getString(R.string.sp_20), R.drawable.s_20));
        items.add(new TextItems(getResources().getString(R.string.sp_21), R.drawable.s_21));
        // items.add(new TextItems(getResources().getString(R.string.sp_22), R.drawable.s_22));
        items.add(new TextItems(getResources().getString(R.string.sp_23), R.drawable.s_23));
        items.add(new TextItems(getResources().getString(R.string.sp_24), R.drawable.s_24));
        items.add(new TextItems(getResources().getString(R.string.sp_26), R.drawable.s_26));
        items.add(new TextItems(getResources().getString(R.string.sp_27), R.drawable.s_27));
        items.add(new TextItems(getResources().getString(R.string.sp_28), R.drawable.s_28));
        items.add(new TextItems(getResources().getString(R.string.sp_29), R.drawable.s_29));
        items.add(new TextItems(getResources().getString(R.string.sp_30), R.drawable.s_30));
        items.add(new TextItems(getResources().getString(R.string.sp_31), R.drawable.s_31));
        items.add(new TextItems(getResources().getString(R.string.sp_32), R.drawable.s_32));
        items.add(new TextItems(getResources().getString(R.string.sp_33), R.drawable.s_33));
        items.add(new TextItems(getResources().getString(R.string.sp_34), R.drawable.s_34));
        // items.add(new TextItems(getResources().getString(R.string.sp_35), R.drawable.s_35));
        items.add(new TextItems(getResources().getString(R.string.sp_36), R.drawable.s_36));
        items.add(new TextItems(getResources().getString(R.string.sp_37), R.drawable.s_37));
        items.add(new TextItems(getResources().getString(R.string.sp_38), R.drawable.s_38));
        // items.add(new TextItems(getResources().getString(R.string.sp_39), R.drawable.s_39));
        items.add(new TextItems(getResources().getString(R.string.sp_41), R.drawable.s_41));
        items.add(new TextItems(getResources().getString(R.string.sp_40), R.drawable.s_40));
        items.add(new TextItems(getResources().getString(R.string.sp_42), R.drawable.s_42));
        items.add(new TextItems(getResources().getString(R.string.sp_43), R.drawable.s_43));
        items.add(new TextItems(getResources().getString(R.string.sp_44), R.drawable.s_44));
        items.add(new TextItems(getResources().getString(R.string.sp_45), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_46), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_47), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_48), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_49), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_50), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_51), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_52), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_53), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_54), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_55), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_56), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_57), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_56), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_57), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_58), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_59), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_60), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_61), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_62), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_63), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_64), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_65), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_66), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_67), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_68), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_69), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_70), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_71), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_72), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_73), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_74), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_75), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_76), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_77), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_78), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_79), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_80), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_81), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_82), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_83), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_84), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_85), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_86), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_87), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_88), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_89), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_90), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_91), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_92), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_93), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_94), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_95), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_96), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_97), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_98), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_99), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_100), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_101), R.drawable.s_45));
        items.add(new TextItems(getResources().getString(R.string.sp_102), R.drawable.s_45));
        return (adapter = new ItemsTextAdapter(getActivity(), items, this::onCLick2));
    }

    @Override
    public void onCLick2(int p) {
        bottomSheetDialog.dismiss();
        switch (clicked) {
            case 1: // work country
                work_country.setText(items.get(p).getTxt());
                break;
            case 2: // jansia
                j_doc_jansia.setText(items.get(p).getTxt());
                break;
            case 3: // sex
                gendar.setText(items.get(p).getTxt());
                break;
            case 4: // degary
                degray.setText(items.get(p).getTxt());
                break;
            case 5: // shpa
                shpa.setText(items.get(p).getTxt());
                break;
            case 6: // country
                count.setText(items.get(p).getTxt());
                break;
        }

    }


    @Override
    public void isClicked() {
        statics.setMY_PLACE("المنزل");
        startActivity(new Intent(context,MainActivity.class));
    }
}
