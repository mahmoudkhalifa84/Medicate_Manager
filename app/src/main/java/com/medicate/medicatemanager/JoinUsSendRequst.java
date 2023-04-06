package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JoinUsSendRequst extends AsyncTask<String,Void,String> {
    Context context;
    Dialog dialog;
    Statics statics;
    String post_data;
    String userType;
    InterfaceDone done;
    public JoinUsSendRequst(Context context, Dialog dialog,InterfaceDone done) {
        this.context = context;
        this.dialog = dialog;
        this.done = done;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://www.medicateint.com/data2/insertNewComounies";
            try {
                userType = params[0];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                if (userType.equals("1")) { /// Not Doctor
                    post_data =
                            URLEncoder.encode("CompanyArabicName", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                                    + URLEncoder.encode("BusnisName", "UTF-8") + "=" + URLEncoder.encode("-----", "UTF-8") + "&"
                                    + URLEncoder.encode("CompanyEnglishName", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                                    + URLEncoder.encode("CompanyFrenshName", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&"
                                    + URLEncoder.encode("Logo", "UTF-8") + "=" + URLEncoder.encode("-----", "UTF-8") + "&"
                                    + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&"
                                    + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8") + "&"
                                    + URLEncoder.encode("CityID", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8") + "&"
                                    + URLEncoder.encode("AddressLine1", "UTF-8") + "=" + URLEncoder.encode(params[7], "UTF-8") + "&"
                                    + URLEncoder.encode("AddressLine2", "UTF-8") + "=" + URLEncoder.encode("-----", "UTF-8") + "&"
                                    + URLEncoder.encode("Companytype", "UTF-8") + "=" + URLEncoder.encode(params[8], "UTF-8") + "&"
                                    + URLEncoder.encode("GooGleMaps", "UTF-8") + "=" + URLEncoder.encode("-----", "UTF-8") + "&"
                                    + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8") + "&"
                                    + URLEncoder.encode("long", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");
                }else {
                    post_data =
                            URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&"
                                    + URLEncoder.encode("WorkCountry", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&"
                                    + URLEncoder.encode("Nationality", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&"
                                    + URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&"
                                    + URLEncoder.encode("LicenseNumber", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8") + "&"
                                    + URLEncoder.encode("Degree", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8") + "&"
                                    + URLEncoder.encode("University", "UTF-8") + "=" + URLEncoder.encode(params[7], "UTF-8") + "&"
                                    + URLEncoder.encode("UniversityYear", "UTF-8") + "=" + URLEncoder.encode(params[8], "UTF-8") + "&"
                                    + URLEncoder.encode("MainSpecialty", "UTF-8") + "=" + URLEncoder.encode(params[9], "UTF-8") + "&"
                                    + URLEncoder.encode("CountryID", "UTF-8") + "=" + URLEncoder.encode(params[10], "UTF-8") + "&"
                                    + URLEncoder.encode("AddressLine1", "UTF-8") + "=" + URLEncoder.encode(params[11], "UTF-8") + "&"
                                    + URLEncoder.encode("AddressLine2", "UTF-8") + "=" + URLEncoder.encode(params[12], "UTF-8") + "&"
                                    + URLEncoder.encode("AboutDoc", "UTF-8") + "=" + URLEncoder.encode(params[13], "UTF-8") + "&"
                                    + URLEncoder.encode("DOB", "UTF-8") + "=" + URLEncoder.encode(params[14], "UTF-8") + "&"
                                    + URLEncoder.encode("Laungues", "UTF-8") + "=" + URLEncoder.encode(params[15], "UTF-8") + "&"
                                    + URLEncoder.encode("e_1_name", "UTF-8") + "=" + URLEncoder.encode(params[16], "UTF-8")+ "&"
                                    + URLEncoder.encode("e_2_name", "UTF-8") + "=" + URLEncoder.encode(params[17], "UTF-8")+ "&"
                                    + URLEncoder.encode("e_3_name", "UTF-8") + "=" + URLEncoder.encode(params[18], "UTF-8")+ "&"
                                    + URLEncoder.encode("e_4_name", "UTF-8") + "=" + URLEncoder.encode(params[19], "UTF-8")+ "&"
                                    + URLEncoder.encode("a_1_name", "UTF-8") + "=" + URLEncoder.encode(params[20], "UTF-8")+ "&"
                                    + URLEncoder.encode("a_2_name", "UTF-8") + "=" + URLEncoder.encode(params[21], "UTF-8")+ "&"
                                    + URLEncoder.encode("a_3_name", "UTF-8") + "=" + URLEncoder.encode(params[22], "UTF-8")+ "&"
                                    + URLEncoder.encode("a_4_name", "UTF-8") + "=" + URLEncoder.encode(params[23], "UTF-8");
                }

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("LOGIN-res" , "result > " + String.valueOf(result));
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("LOGIN-res" , "ERROR 1 > " + String.valueOf(e.getMessage()));
            } catch (IOException e) {
                Log.d("LOGIN-res" , "ERROR 2 > " + String.valueOf(e.getMessage()));

                e.printStackTrace();
            }
        return null;
    }

    @Override
    protected void onPreExecute() {
        statics = new Statics(context);
        dialog.show();
     }

    @Override
    protected void onPostExecute(String result) {
        try {
            //alertDialog.dismiss();
            if (null != context) {
                dialog.dismiss();
                convertLoginMsg(result);
            }
        } catch (JSONException e) {
            dialog.dismiss();
            e.printStackTrace();
            Log.d("LOGIN-res" , "ERROR 3 > " + String.valueOf(e.getMessage()));

        }
    }
    public void convertLoginMsg(String json) throws JSONException {
        String name;
        Log.d("LOGIN-res" , "data > " + String.valueOf(json));
        if (!(json == null)) {
          JSONObject obj = new JSONObject(json);
            name = obj.getString("msg");
            if (name.equals("0"))
                Messages.Dialog(context.getResources().getString(R.string.no_internet_con),context);
            else if (name.equals("1")) {
                done.Done(1);
                Messages.Dialog(context.getString(R.string.requst_send),context);
            }
        }
        else {
            dialog.dismiss();
            Messages.Dialog(context.getResources().getString(R.string.no_internet_con),context);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        dialog.dismiss();
    }
    public interface InterfaceDone{
        void Done(int i);
    }
}
