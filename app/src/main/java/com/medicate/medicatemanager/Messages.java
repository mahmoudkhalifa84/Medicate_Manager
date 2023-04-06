package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

public class Messages {


    public static void Dialog(String txt , Context context){
        Dialog dialogsa = new Dialog(context, R.style.PauseDialog);
        dialogsa.setContentView(R.layout.per_requst);
        TextView textView = dialogsa.findViewById(R.id.textView40);
        TextView but = dialogsa.findViewById(R.id.textView42);
        textView.setText(txt);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsa.dismiss();
            }
        });
        dialogsa.setCancelable(false);
        dialogsa.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogsa.show();
    }

    public static void DialogOkLesener(String txt , Context context, OkClicked okClicked){
        Dialog dialogsa = new Dialog(context, R.style.PauseDialog);
        dialogsa.setContentView(R.layout.per_requst);
        TextView textView = dialogsa.findViewById(R.id.textView40);
        TextView but = dialogsa.findViewById(R.id.textView42);
        textView.setText(txt);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsa.dismiss();
                okClicked.isClicked();
            }
        });
        dialogsa.setCancelable(false);
        dialogsa.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogsa.show();
    }
    public static void RateDialog(Context context){
        Dialog dialogsa = new Dialog(context, R.style.PauseDialog);
        dialogsa.setContentView(R.layout.plz_rate_us);
        TextView but = dialogsa.findViewById(R.id.textView43);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsa.dismiss();
                Statics.OpenMyAppStorePage(context, "null");
            }
        });
        dialogsa.setCancelable(true);
        dialogsa.setCanceledOnTouchOutside(true);
        dialogsa.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogsa.show();
    }

    public interface OkClicked{
    public void isClicked();
    }
}
