package com.han.koopon.Main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.han.koopon.Config;
import com.han.koopon.MainDetail.MainDetailActivity;
import com.han.koopon.R;
import com.han.koopon.Util.PhotoUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

public class MainRecyclerview extends RecyclerView.Adapter<MainRecyclerview.MainHolder> {
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);


    //inner class
    class MainHolder extends RecyclerView.ViewHolder {
        View view;
        TextView item_content1;
        TextView item_content2;
        ImageView item_imageview;
        CheckBox rv_item_checkbox;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            item_content1 = view.findViewById(R.id.item_content1);
            item_content2 = view.findViewById(R.id.item_content2);
            item_imageview = view.findViewById(R.id.item_imageview);
            rv_item_checkbox = view.findViewById(R.id.rv_item_checkbox);
        }

        public void setListner(CompoundButton.OnCheckedChangeListener listner){
            rv_item_checkbox.setOnCheckedChangeListener(listner);
        }

        public void setClickListener(View.OnClickListener listener){
            view.setOnClickListener(listener);
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
        mh.setListner((CompoundButton compoundButton, boolean b) ->{
            if (b){
                Logger.i("Check");
            } else {
                Logger.i("unCheck");
            }
        });
//        mh.setClickListener(v->{
//            Logger.i("ccc");
//            Intent intent = new Intent(context, MainDetailActivity.class);
//            intent.putExtra(Config.INTENT_EXTRA_TITLE,"INTENT_EXTRA_TITLE");
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, v, "transition_item");
//            context.startActivity(intent, options.toBundle());
//        });
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        Logger.i("onBindViewHolder");

        if (list.isEmpty()){
            Logger.e("empty list");
        } else {
            Logger.i("coopon list == > %s",list.get(position).CouponToString());
            String imgurl = list.get(position).imgURL;

            try {
                Logger.i("imgurl == > %s" ,imgurl);
                File file = new File(imgurl);
                if (file.exists()){
                    holder.item_imageview.setImageURI(Uri.parse(imgurl));
                }
                else {
                    holder.item_imageview.setImageResource(R.drawable.not_found_img);
                }
            }catch (Exception e){
                Logger.e("MainRecyclerview :" + e.toString());
                holder.item_imageview.setImageResource(R.drawable.not_found_img);
            }

            holder.item_content1.setText(list.get(position).coupon_title);
            holder.item_content2.setText(list.get(position).date);
            holder.rv_item_checkbox.setChecked(list.get(position).isUse);
            holder.view.setOnClickListener(v->{
                Logger.i("ccc");
                Intent intent = new Intent(context, MainDetailActivity.class);
                intent.putExtra(Config.INTENT_EXTRA_TITLE,list.get(position).coupon_title);
                intent.putExtra(Config.INTENT_EXTRA_CURRENT_COUNT,imgurl);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, v, "transition_item");
                context.startActivity(intent, options.toBundle());
            });
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
