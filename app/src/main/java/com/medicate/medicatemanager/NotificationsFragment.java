package com.medicate.medicatemanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {
    View view;
    Statics statics;
    NotificationsDatabase database;
    List<Notifications_tiems> items;
    NotificationsAdapter adapter;
    RecyclerView recyclerView;
    Dialog dailog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        LoadingDigSetup();
        view.findViewById(R.id.linearLayout8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.noti_ref).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadData();
            }
        });
        recyclerView = view.findViewById(R.id.noti_recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ReadData();
        return view;
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

    private void ReadData() {
        items = new ArrayList<>();
        dailog.show();
        try {
            database = new NotificationsDatabase(getActivity());
            if (null != database.getData()) {
                Cursor cursor = database.getData();
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                   do {
                        items.add(new Notifications_tiems(
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3))
                        );
                    } while (cursor.moveToPrevious());
                    adapter = new NotificationsAdapter(getActivity(), items);
                    recyclerView.setAdapter(adapter);
                    dailog.dismiss();
                    database.SetStateToOld();
                } else {
                    dailog.dismiss();
                    Messages.Dialog(getString(R.string.no_new_noti), getActivity());
                }
            } else {
                dailog.dismiss();
                Messages.Dialog(getString(R.string.no_new_noti), getActivity());
            }
        } catch (NullPointerException e){
            Messages.Dialog(getString(R.string.no_new_noti), getActivity());
            Log.d("NOTIFICATIONS_TAG", "ReadData: " + e.getMessage());
        }
    }
}