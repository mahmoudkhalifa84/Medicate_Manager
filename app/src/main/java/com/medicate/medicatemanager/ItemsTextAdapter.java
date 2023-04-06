package com.medicate.medicatemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsTextAdapter extends RecyclerView.Adapter<ItemsTextAdapter.News> {
    List<TextItems> full;
    List<TextItems> demo;
    Context context;
    Statics statics;
    ItemsTextAdapter.onCLickLis2 MonCLickLi;

    public ItemsTextAdapter(Context context, List<TextItems> TextItems, ItemsTextAdapter.onCLickLis2 monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.full = TextItems;
        demo = full;
        statics = new Statics(context.getApplicationContext());
    }

    public List<TextItems> getIm() {
        return demo;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.text_items_row, parent, false);
        return new News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        holder.text.setText(demo.get(position).getTxt());

    }

    @Override
    public int getItemCount() {
        return demo.size();
    }


    public List<TextItems> getDemo(){
        return demo;
    }
    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ItemsTextAdapter.onCLickLis2 onCLickLi;

        public News(@NonNull View itemView, ItemsTextAdapter.onCLickLis2 onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            text = itemView.findViewById(R.id.txt_city_row);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onCLickLi.onCLick2(getAdapterPosition());
        }
    }

    public interface onCLickLis2 {
        void onCLick2(int p);
    }
}
