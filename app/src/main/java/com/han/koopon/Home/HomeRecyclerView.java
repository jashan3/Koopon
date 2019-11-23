package com.han.koopon.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.han.koopon.R;

import java.util.List;

public class HomeRecyclerView extends RecyclerView.Adapter<HomeRecyclerView.HomeHolder> {

    private Context context;
    private List list;
    private View.OnClickListener itemClickListner;
    private View.OnLongClickListener itemLongClickListner;

    HomeRecyclerView(Context context, List list) { }
    HomeRecyclerView(Context context, List list,View.OnClickListener itemClickListner,View.OnLongClickListener itemLongClickListner) {
        this.context = context;
        this.list = list;
        this.itemClickListner = itemClickListner;
        this.itemLongClickListner = itemLongClickListner;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_main_item,parent,false);
        HomeHolder mh = new HomeHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        holder.rv_item_container.setOnLongClickListener(itemLongClickListner);
        holder.rv_item_container.setOnClickListener(itemClickListner);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class HomeHolder extends RecyclerView.ViewHolder {
        RelativeLayout rv_item_container;
        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            rv_item_container = itemView.findViewById(R.id.rv_item_container);
        }
    }
}
