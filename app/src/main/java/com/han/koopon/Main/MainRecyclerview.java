package com.han.koopon.Main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.transition.TransitionManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.han.koopon.Config;
import com.han.koopon.MainDetail.MainDetailActivity;
import com.han.koopon.R;
import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.StringUtil;
import com.han.koopon.animation.Stagger;
import com.han.koopon.dialog.KooponAlert;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

public class MainRecyclerview extends RecyclerView.Adapter<MainRecyclerview.MainHolder> {
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    /**
     * inner class
     */
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

        private void bind(){

        }

    }

    //var
    private Context context;
    private List<Coupon> list;
    private View.OnClickListener itemClickListner;
    private View.OnLongClickListener itemLongClickListner;
    private String userID;

    //construct
    public MainRecyclerview(Context context, List<Coupon> list, View.OnClickListener itemClickListner, View.OnLongClickListener itemLongClickListner) {
        this.context = context;
        this.list = list;
        this.itemClickListner = itemClickListner;
        this.itemLongClickListner = itemLongClickListner;
        userID = PFUtil.getPreferenceString(context,PFUtil.AUTO_LOGIN_ID);
        userID = StringUtil.emailToStringID(userID);

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
                Intent intent = new Intent(context, MainDetailActivity.class);
                Coupon cp = null;
                cp = list.get(position);
                intent.putExtra(Config.INTENT_EXTRA_TITLE,cp.coupon_title);
                intent.putExtra(Config.INTENT_EXTRA_CURRENT_COUNT,imgurl);
                intent.putExtra(Config.INTENT_EXTRA_DATE,cp.date);
                intent.putExtra(Config.INTENT_EXTRA_BODY,cp.coupon_body);


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, holder.item_imageview, "transition_item");
                context.startActivity(intent, options.toBundle());
            });

            holder.view.setOnLongClickListener(v->{
                KooponAlert.from(context, new KooponAlert.onKPClickListener() {
                    @Override
                    public void onPositive(View view) {
                        removeItem(position);
                    }

                    @Override
                    public void onNegetive(View view) {

                    }
                }).show();
                return true;
            });

            //체크박스
            if (list.get(position).isUse){
                int color = R.color.trangray;
                holder.view.setForeground(new ColorDrawable(ContextCompat.getColor(context, color)));
            }
            holder.rv_item_checkbox.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) ->{
                if (b){
                    Coupon c = null;
                    c = list.get(position);
                    c.isUse = true;
                    FireBaseQuery.insertFBNeedKey(StringUtil.base64(c.imgURL),c,userID);
                    int color = R.color.trangray;
                    holder.view.setForeground(new ColorDrawable(ContextCompat.getColor(context, color)));
                } else {
                    Coupon c = null;
                    c = list.get(position);
                    c.isUse = false;
                    FireBaseQuery.insertFBNeedKey(StringUtil.base64(c.imgURL),c,userID);
                    holder.view.setForeground(null);
                }
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
    public void removeItem(int p){
        Coupon c = null;
        c = list.get(p);
        FireBaseQuery.deleteFBNeedKey(StringUtil.base64(c.imgURL),c,userID);
        notifyDataSetChanged();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Logger.i("onMove");
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            Logger.i("onSwiped");
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Logger.i("onChildDraw");
        }
    };



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
