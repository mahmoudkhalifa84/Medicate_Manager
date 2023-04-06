package com.medicate.medicatemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QAdapter extends RecyclerView.Adapter<QAdapter.News> {
    Context context;
    Statics statics;
    onCLickLis MonCLickLi;
    List<QItems> list_all;
    List<QItems> list_filter;


    public QAdapter(Context context, List<QItems> list_all, onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.list_all = list_all;
        this.list_filter = list_all;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_q_det,parent,false);
        statics = new Statics(context);
        return new News(view,MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        holder.txt.setText(list_filter.get(position).getTxt());
        holder.date.setText(list_filter.get(position).getDate());
        holder.name.setText(list_filter.get(position).getName());
     }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return list_filter.size();
    }
    public List<QItems> getIm(){
        return list_filter;
    }


    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView date,name,txt,but;
        onCLickLis onCLickLi;
        CardView constraintLayout;
        public News(@NonNull View itemView,onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            constraintLayout = itemView.findViewById(R.id.q_row_lay);
            date = itemView.findViewById(R.id.q_row_date);
            name = itemView.findViewById(R.id.q_row_name);
            txt = itemView.findViewById(R.id.q_row_txt);
            but = itemView.findViewById(R.id.q_row_but);
            but.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCLickLi.onCLick(getAdapterPosition());
        }
    }
    public interface onCLickLis{
        void onCLick(int p);
    }
}
