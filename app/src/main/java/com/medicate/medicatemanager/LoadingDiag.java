package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoadingDiag extends Dialog {
    public LoadingDiag(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.loading_layout);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }
}
