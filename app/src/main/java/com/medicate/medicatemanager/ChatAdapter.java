package com.medicate.medicatemanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.medicate.medicatemanager.ChatFragment.TAG;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.News> {
    Context context;
    Statics statics;
    onCLickLis MonCLickLi;
    List<ChatItems> list_all;
    List<ChatItems> list_filter;


    public ChatAdapter(Context context, List<ChatItems> list_all, onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.list_all = list_all;
        this.list_filter = list_all;
    }

    public ChatAdapter(Context context , onCLickLis monCLickLi) {
        this.context = context;
        this.list_all = new ArrayList<>();
        this.list_filter = list_all;
        this.MonCLickLi = monCLickLi;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_row, parent, false);
        statics = new Statics(context);
        return new News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        ChatItems items = list_filter.get(position);
        holder.comp.setVisibility(View.GONE);
        holder.user.setVisibility(View.GONE);
        try {
            if (items.getSend_by().contains(statics.getCompanyID())) {
                holder.user.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img_ar));
                holder.user.setVisibility(View.VISIBLE);
                holder.you_txt.setText(items.getMsg());
                holder.you_date.setText(items.getMsg_date().substring(10));
            } else {
                holder.comp.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img));
                holder.comp.setVisibility(View.VISIBLE);
                holder.comp_txt.setText(items.getMsg());
                holder.comp_date.setText(items.getMsg_date().substring(10));
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException: " + e.getMessage());
        }
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void RemoveAll(){
        list_filter.clear();
        list_all.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_filter.size();
    }

    public List<ChatItems> getIm() {
        return list_filter;
    }
    public void removeAt(int position) {
        list_filter.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_filter.size());
    }

    public void addNew(ChatItems items) {
        list_filter.add(items);
        notifyItemInserted(list_filter.size() - 1 );
        notifyItemRangeChanged(list_filter.size() -1 ,list_filter.size());
    }

    public static class News extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        onCLickLis onCLickLi;
        TextView you_txt, comp_txt, you_date , comp_date;
        LinearLayout user, comp;

        public News(@NonNull View itemView, ChatAdapter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            user = itemView.findViewById(R.id.from_you);
            comp = itemView.findViewById(R.id.from_comp);
            you_txt = itemView.findViewById(R.id.chat_you_txt);
            comp_txt = itemView.findViewById(R.id.chat_comp_txt);
            you_date = itemView.findViewById(R.id.chat_you_date);
            comp_date = itemView.findViewById(R.id.chat_comp_date);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onCLickLi.onCLick(getAdapterPosition());
            return true;
        }
    }

    public interface onCLickLis {
        void onCLick(int p);
    }
}
