package com.medicate.medicatemanager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.News> {
    Context context;
    List<Notifications_tiems> items_full;
    List<Notifications_tiems> items_filtterd;

    public NotificationsAdapter(Context context, List<Notifications_tiems> items_full) {
        this.context = context;
        this.items_full = items_full;
        items_filtterd = items_full;

    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.noti_row, parent, false);
        return new News(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        if (!items_full.isEmpty()) {
            holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
            holder.body.setText(items_filtterd.get(position).getBody());
            holder.date.setText(items_filtterd.get(position).getDate());
            if (items_filtterd.get(position).getState().equals("1")) {
                holder.new_noti.setVisibility(View.VISIBLE);
            } else {
                holder.new_noti.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if (items_filtterd.isEmpty())
            return 0;
        return this.getIm().size();
    }

    public List<Notifications_tiems> getIm() {
        return items_filtterd;
    }

    public static class News extends RecyclerView.ViewHolder {
        TextView title, body, date, new_noti;
        CardView constraintLayout;

        public News(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.ll_noti_lay);
            body = itemView.findViewById(R.id.noti_body);
            date = itemView.findViewById(R.id.noti_date);
            new_noti = itemView.findViewById(R.id.noti_new);
        }
    }
}
