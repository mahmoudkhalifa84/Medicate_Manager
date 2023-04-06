package com.medicate.medicatemanager;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.textclassifier.TextLanguage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void, Void, Void> implements Messages.OkClicked {

    private Context context;

    private Session session;
    private String email, subject,type;
    JoinUsItems message;
    LoadingDiag loadingDiag;

    public JavaMailAPI(Context context, String type, JoinUsItems message, LoadingDiag loadingDiag1) {
        this.context = context;
        this.email = Statics.SEND_TO_EMAIL;
        this.subject = "أنضم إلينا - " + type;
        this.type = type;
        this.message = message;
        loadingDiag = loadingDiag1;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDiag.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("charset","utf-8");
        properties.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Statics.EMAIL, Statics.PASSWORD);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            Object any = "<!DOCTYPE html>\n" +
                    "<html charset=\"UTF-8\">\n" + "<head><meta charset=\"utf-8\"/></head>" +
                    "<body>\n" +
                    "<style>\n" +
                    "    table.GeneratedTable {\n" +
                    "      width: 100%;\n" +
                    "      background-color: #ffffff;\n" +
                    "      border-collapse: collapse;\n" +
                    "      border-width: 1px;\n" +
                    "      border-color: #ffffff;\n" +
                    "      border-style: dotted;\n" +
                    "      color: #175700;\n" +
                    "    }\n" +
                    "    \n" +
                    "    table.GeneratedTable td, table.GeneratedTable th {\n" +
                    "      border-width: 1px;\n" +
                    "      border-color: #ffffff;\n" +
                    "      border-style: solid;\n" +
                    "      padding: 8px;\n" +
                    "    }\n" +
                    "    \n" +
                    "    table.GeneratedTable thead {\n" +
                    "      background-color: #ffcc00;\n" +
                    "    }\n" +
                    "    </style>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "<table class=\"GeneratedTable\" style=\"width: 100%; float: right; direction: rtl;\" cellpadding=\"8\" >\n" +
                    "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <td>نوع مزود الخدمة</td>\n" +
                    "        <td>"+type+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>أسم المفوض</td>\n" +
                    "        <td>"+message.getAdmin_name()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>البريد الإلكتروني الخاص بالمفوض</td>\n" +
                    "        <td>"+message.getAdmin_email()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>رقم الهاتف الخاص بالمفوض</td>\n" +
                    "        <td>"+message.getAdmin_phone()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>الدولة - المدينة - المنطقة</td>\n" +
                    "        <td>"+message.getCountry()+ " - " + message.getCity() +" - " + message.getErya() + "</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"2\"style=\"text-align: center\">تم ﻹرسال هذا الطلب من خلال تطبيق ميديكيت مانجر</td>\n" +
                    "    </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n" +
                    "</body>\n" +
                    "</html>";
            Object doctor = "<!DOCTYPE html>\n" +
                    "<html charset=\"UTF-8\">\n" + "<head><meta charset=\"utf-8\"/></head>" +
                    "<body>\n" +
                    "<style>\n" +
                    "    table.GeneratedTable {\n" +
                    "      width: 100%;\n" +
                    "      background-color: #ffffff;\n" +
                    "      border-collapse: collapse;\n" +
                    "      border-width: 1px;\n" +
                    "      border-color: #ffffff;\n" +
                    "      border-style: dotted;\n" +
                    "      color: #175700;\n" +
                    "    }\n" +
                    "    \n" +
                    "    table.GeneratedTable td, table.GeneratedTable th {\n" +
                    "      border-width: 1px;\n" +
                    "      border-color: #ffffff;\n" +
                    "      border-style: solid;\n" +
                    "      padding: 8px;\n" +
                    "    }\n" +
                    "    \n" +
                    "    table.GeneratedTable thead {\n" +
                    "      background-color: #ffcc00;\n" +
                    "    }\n" +
                    "    </style>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "<table class=\"GeneratedTable\" style=\"width: 100%; float: right; direction: rtl;\" cellpadding=\"8\" >\n" +
                    "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <td>4444 مزود الخدمة</td>\n" +
                    "        <td>"+type+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>أسم المفوض</td>\n" +
                    "        <td>"+message.getAdmin_name()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>البريد الإلكتروني الخاص بالمفوض</td>\n" +
                    "        <td>"+message.getAdmin_email()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>رقم الهاتف الخاص بالمفوض</td>\n" +
                    "        <td>"+message.getAdmin_phone()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>الدولة - المدينة - المنطقة</td>\n" +
                    "        <td>"+message.getCountry()+ " - " + message.getCity() +" - " + message.getErya() + "</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"2\"style=\"text-align: center\">تم ﻹرسال هذا الطلب من خلال تطبيق ميديكيت مانجر</td>\n" +
                    "    </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n" +
                    "</body>\n" +
                    "</html>";
           /* String temp = o.toString();
            temp.replace("DDFDD",message.getName()); // مصحة
            temp.replace("ASDA",message.getAdmin_name()); // أسم المفوض
            temp.replace("LKSE",message.getAdmin_email()); // البريد
            temp.replace("LKJA",message.getCountry()); // الدولة
            temp.replace("NDEDD",message.getAdmin_phone()); // العاتف
            o = temp;*/
            mimeMessage.setFrom(new InternetAddress(Statics.EMAIL));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            mimeMessage.setSubject(subject,"UTF-8");
            mimeMessage.setContent(any,"text/html; charset=utf-8");
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Statics.SEND_TO_EMAIL));
           // mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            Log.d(JoinUsFragment.TAG, "ERROR > " + e.getMessage());
            loadingDiag.dismiss();
            Messages.Dialog(context.getString(R.string.uknown_error),context);
        }

        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        loadingDiag.dismiss();
        if (null!= this)
        Messages.DialogOkLesener("تم إرسال طلبك بنجاح \n  سيتم التواصل معك قريباً",context, this);
    }

    @Override
    public void isClicked() {
        new Statics(context).setMY_PLACE("المنزل");
        context.startActivity(new Intent(context,MainActivity.class));    }
}
