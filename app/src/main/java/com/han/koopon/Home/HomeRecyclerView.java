package com.han.koopon.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.han.koopon.R;

import java.io.File;
import java.util.List;

public class HomeRecyclerView extends RecyclerView.Adapter<HomeRecyclerView.HomeHolder> {

    private Context context;
    private List <String>list;
    private View.OnClickListener itemClickListner;
    private View.OnLongClickListener itemLongClickListner;

    HomeRecyclerView(Context context, List list) { }
    HomeRecyclerView(Context context, List <String> list,View.OnClickListener itemClickListner,View.OnLongClickListener itemLongClickListner) {
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
        Bitmap bitmap = null;
        bitmap =  stringToBitmap(list.get(position));

        if (bitmap !=null){
            holder.rv_item_thumnail.setImageBitmap(bitmap);
        }

    }

    private Bitmap stringToBitmap(String path){
        File imgFile =null;
        Bitmap myBitmap = null;
        imgFile = new  File(path);
        if(imgFile.exists()){
            myBitmap =  BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }

    public void updateItems(List <String>list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HomeHolder extends RecyclerView.ViewHolder {
        LinearLayout rv_item_container;
        ImageView rv_item_thumnail;
        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            rv_item_container = itemView.findViewById(R.id.rv_item_container);
            rv_item_thumnail = itemView.findViewById(R.id.rv_item_thumnail);
        }
    }
}
