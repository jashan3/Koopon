package com.han.koopon.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.han.koopon.R;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

public class MainRecyclerview extends RecyclerView.Adapter<MainRecyclerview.MainHolder> {

    //inner class
    class MainHolder extends RecyclerView.ViewHolder {
        TextView item_content1;
        TextView item_content2;
        ImageView item_imageview;
        CheckBox rv_item_checkbox;
        public MainHolder(@NonNull View itemView) {
            super(itemView);
            item_content1 = itemView.findViewById(R.id.item_content1);
            item_content2 = itemView.findViewById(R.id.item_content2);
            item_imageview = itemView.findViewById(R.id.item_imageview);
            rv_item_checkbox = itemView.findViewById(R.id.rv_item_checkbox);
        }
    }

    //var
    private Context context;
    private List<Coupon> list;
    private View.OnClickListener itemClickListner;
    private View.OnLongClickListener itemLongClickListner;

    //construct
    public MainRecyclerview(Context context, List<Coupon> list, View.OnClickListener itemClickListner, View.OnLongClickListener itemLongClickListner) {
        this.context = context;
        this.list = list;
        this.itemClickListner = itemClickListner;
        this.itemLongClickListner = itemLongClickListner;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_main_item2,parent,false);
        MainHolder mh = new MainHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        Logger.i("onBindViewHolder");
        if (list.isEmpty()){
            Logger.e("empty list");
        } else {
            holder.item_content1.setText(list.get(position).coupon_title);
            holder.item_content2.setText(list.get(position).date);
            holder.rv_item_checkbox.setChecked(list.get(position).isUse);
//            holder.item_imageview.setImageBitmap();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void updateList( List<Coupon> list){
        this.list = list;
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

    public void updateItems(List <Coupon>list){
        this.list = list;
        notifyDataSetChanged();
    }


}
