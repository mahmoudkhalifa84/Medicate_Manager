package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    Dialog dialog;
    Statics statics;
    String user_name;
    public BackgroundWorker(Context context, Dialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://www.medicateint.com/data2/checkLoginForDocApp";
            try {
                user_name = params[0];
                String password = params[1];
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
                String post_data =
                        URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                            //    + URLEncoder.encode("userType", "UTF-8") + "=" + URLEncoder.encode(userType, "UTF-8");
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
                Messages.Dialog(context.getResources().getString(R.string.email_or_name_error),context);
            else if (name.equals("1")) {
                statics.setID(obj.getString("UserID"));
                statics.setCompanyID(obj.getString("CompanyID"));
                statics.setCompanyType(obj.getString("CompanyType"));
                statics.setCompName(obj.getString("CompanyName"));
              if (!obj.isNull("ContractID"))
                statics.setConNumber(obj.getString("ContractID")); // todo 3 4 5
                statics.setMY_PLACE("المنزل");
                statics.setUserName(user_name);
                context.startActivity(new Intent(context,MainActivity.class));
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
}
