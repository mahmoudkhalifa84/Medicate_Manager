package com.medicate.medicatemanager;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ChatFragment extends Fragment implements ChatAdapter.onCLickLis {
    View view;
    int count = 1, items_num = 0;

    Statics statics;
    RecyclerView recyclerView;
    ChatAdapter adapter;
    List<ChatItems> items;
    boolean internet = false;
    Dialog no_int_did, dailog;
    static String TAG = "CHATDOC";
    DatabaseReference databaseReference, send_database;
    FirebaseDatabase firebaseDatabase;
    EditText editTextTextMultiLine;
    String formattedDate;
    SimpleDateFormat df;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault());

        statics = new Statics(getActivity());

        Log.d(TAG, "onDataChange: compid > " + statics.getCompanyID());


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

    public void NoInternet() {
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

    public void getData() {

        dailog.show();

            ValueEventListener dataFetchEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        count = ((int) dataSnapshot.getChildrenCount()) - 1;
                        for (DataSnapshot child: dataSnapshot.getChildren()) { // 👈 loop over the child nodes
                            items.add(new ChatItems(child.child("msg").getValue(String.class),
                                    child.child("msg_date").getValue(String.class),
                                    child.child("send_by").getValue(String.class)));
                            adapter.addNew(items.get(items.size()-1));
                        }

                        dailog.dismiss();
                    } else {
                        adapter.RemoveAll();
                        items.clear();
                        items_num = 0;
                        count = 1;
                        Log.d(TAG, "ERROR: NO DATA > ");
                        dailog.dismiss();
                    }

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter.getItemCount() > 0)
                                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    });
                    // You async code goes here
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    dailog.dismiss();
                    NoInternet();
                }
            };
            databaseReference.child("Chat").child("comp".concat(statics.getCompanyID())).addValueEventListener(dataFetchEventListener);

    }

    private void setClipboard(String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("تم نسخ النص", text);
        clipboard.setPrimaryClip(clip);
    }

    public void sendMessage() {
        if (CheckConnection.isNetworkConnected(getActivity())) {
            String txt = editTextTextMultiLine.getText().toString();
            editTextTextMultiLine.getText().clear();
            send_database = firebaseDatabase.getReference().getRoot().child("MedicateManager").child("Chat");;
            Log.d(TAG, "send_databasex > " + statics.getCompName());

            send_database.child("comp".concat(statics.getCompanyID())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    formattedDate = df.format(Calendar.getInstance().getTime());

                    if (snapshot.exists()) {
                        send_database.child("comp".concat(statics.getCompanyID())).child("").push().setValue(new ChatItems(txt.trim(),
                                formattedDate, "comp".concat(statics.getCompanyID())))

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                /*    adapter.updateSent(items.get(count-1).getMsg_date() ,
                                            items.get(count -1 ).getMsg());*/
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Write failed
                                        // ...
                                    }
                                });
                    } else {
                        send_database.child("comp".concat(statics.getCompanyID())).child("").push().setValue(new ChatItems(txt.trim(),
                                    formattedDate, "comp".concat(statics.getCompanyID())))/// "comp".concat(statics.getCompanyID())))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                 /*   adapter.updateSent(items.get(count-1).getMsg_date() ,
                                            items.get(count -1 ).getMsg());*/
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    // ...
                                }
                            });
                    // todo > SEND THE COMPANY NAME
                    send_database.child("comp".concat(statics.getCompanyID())).child("comp_name").setValue(statics.getCompName());
                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Messages.Dialog(getString(R.string.no_internet_con), getActivity());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (CheckConnection.isNetworkConnected(requireActivity())) {
            internet = true;
        }
        LoadingDigSetup();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);
        adapter = new ChatAdapter(getActivity(), this);
        recyclerView = view.findViewById(R.id.chat_recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        items = new ArrayList<>();
        if (internet) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().getRoot().child("MedicateManager");

            getData();
        } else {
            NoInternet();
        }
        if (CheckConnection.isNetworkConnected(getActivity())) {
            if (!statics.getTxt().equals("null")) {
                editTextTextMultiLine.setText(statics.getTxt());
                sendMessage();
                statics.setMainTxt("null");
            }
        }

        view.findViewById(R.id.send_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextTextMultiLine.getText().length() > 0) {
                   /* formattedDate = df.format(Calendar.getInstance().getTime());
                    adapter.addNew(new ChatItems(editTextTextMultiLine.getText().toString().trim(),
                            formattedDate, "comp".concat(statics.getCompanyID())));
                    editTextTextMultiLine.getText().clear();*/
                    if (CheckConnection.isNetworkConnected(getActivity())) {
                        sendMessage();
                    } else {
                        Messages.Dialog(getString(R.string.no_internet_con), getActivity());
                    }

                }
            }
        });
        view.findViewById(R.id.imageView12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckConnection.isNetworkConnected(getActivity())) {
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference().getRoot().child("MedicateManager");
                } else {
                    NoInternet();
                }
            }
        });
        view.findViewById(R.id.linearLayout7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.imageView46).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel: 0900155555"));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    startActivity(i);
                }
            }
        });
        return view;
    }

    @Override
    public void onCLick(int p) {
        Dialog long_prs = new Dialog(getActivity(), R.style.PauseDialog);
        long_prs.setCancelable(true);
        long_prs.setCanceledOnTouchOutside(true);
        long_prs.setContentView(R.layout.on_long_click);
        if (items.get(p).getSend_by().contains("admin")) {
            ((TextView) long_prs.findViewById(R.id.comp_name)).setText(getString(R.string.medicate));

        } else {
            ((TextView) long_prs.findViewById(R.id.comp_name)).setText(statics.getCompName());

        }
        ((TextView) long_prs.findViewById(R.id.comp_date)).setText(items.get(p).getMsg_date());

        long_prs.findViewById(R.id.copy_txt_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long_prs.dismiss();
                setClipboard(adapter.getIm().get(p).getMsg());
                Toast.makeText(getActivity(), getString(R.string.copy_text_secc), Toast.LENGTH_SHORT).show();
            }
        });
        long_prs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        long_prs.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel: 0900155555"));
                startActivity(i);
            }
        }
    }
}