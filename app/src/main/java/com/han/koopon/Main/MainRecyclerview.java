package com.han.koopon.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.han.koopon.R;
import com.han.koopon.Util.PhotoUtil;
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
            Logger.i("coopon list == > %s",list.get(position).CouponToString());

            try {
                String imgurl = "content:/"+list.get(position).imgURL;
                Logger.i("imgurl == > %s" ,imgurl);

                Uri uri = Uri.parse(imgurl);
                File file = new File(PhotoUtil.getRealPathFromURI_API19(context,uri));
                if (file.exists()){
                    Logger.i("oo 잇음");
                }
                Bitmap bitmap = PhotoUtil.getBitmap(PhotoUtil.getRealPathFromURI_API19(context,uri));
                holder.item_imageview.setImageBitmap(bitmap);
            }catch (Exception e){
                Logger.e("MainRecyclerview :" + e.toString());
                holder.item_imageview.setImageResource(R.drawable.not_found_img);
            }

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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Uri[] getSafUris (Context context, File file) {

        Uri[] uri = new Uri[2];
        String scheme = "content";
        String authority = "com.android.externalstorage.documents";

        // Separate each element of the File path
        // File format: "/storage/XXXX-XXXX/sub-folder1/sub-folder2..../filename"
        // (XXXX-XXXX is external removable number
        String[] ele = file.getPath().split(File.separator);
        //  ele[0] = not used (empty)
        //  ele[1] = not used (storage name)
        //  ele[2] = storage number
        //  ele[3 to (n-1)] = folders
        //  ele[n] = file name

        // Construct folders strings using SAF format
        StringBuilder folders = new StringBuilder();
        if (ele.length > 4) {
            folders.append(ele[3]);
            for (int i = 4; i < ele.length - 1; ++i) folders.append("%2F").append(ele[i]);
        }

        String common = ele[2] + "%3A" + folders.toString();

        // Construct TREE Uri
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme);
        builder.authority(authority);
        builder.encodedPath("/tree/" + common);
        uri[0] = builder.build();

        // Construct DOCUMENT Uri
        builder = new Uri.Builder();
        builder.scheme(scheme);
        builder.authority(authority);
        if (ele.length > 4) common = common + "%2F";
        builder.encodedPath("/document/" + common + file.getName());
        uri[1] = builder.build();

        return uri;
    }

}
