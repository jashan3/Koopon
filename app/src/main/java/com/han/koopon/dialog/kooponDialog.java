package com.han.koopon.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.han.koopon.Main.Coupon;
import com.han.koopon.Main.FireBaseQuery;
import com.han.koopon.Main.MainFragment;
import com.han.koopon.MainActivity;
import com.han.koopon.R;
import com.orhanobut.logger.Logger;

import java.io.File;

public class kooponDialog extends DialogFragment {

    private Context mcontext;
    private EditText koopon_edit_date,koopon_edit_title;
    private ImageView koopon_imgv,close_btn;
    private Button koopon_add_btn;
    private final static int ALBUM_REQUEST_CODE = 1001;

    public static kooponDialog newInstance(){
        return new kooponDialog();
    }
//    public kooponDialog(@NonNull Context context) {
//        super(context);
//        this.mcontext = context;
//    }
//
//    public kooponDialog(@NonNull Context context, int themeResId) {
//        super(context, themeResId);
//    }

//    protected kooponDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_koopon_edit);
//        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
//
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_koopon_edit,container,false);
        bindView(view);
        return view;
    }

    private void bindView(View v){
        close_btn = v.findViewById(R.id.close_btn);
        koopon_edit_title =v.findViewById(R.id.koopon_edit_title);
        koopon_edit_date = v.findViewById(R.id.koopon_edit_date);
        koopon_imgv = v.findViewById(R.id.koopon_imgv);
        koopon_add_btn = v.findViewById(R.id.koopon_add_btn);


        //이미지 불러오기
        koopon_imgv.setOnClickListener(view -> {
//            Toast.makeText(mcontext, "image call!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
             startActivityForResult(intent, ALBUM_REQUEST_CODE);
        });



        //종료
        close_btn.setOnClickListener((view)->{
            dismiss();
        });

        //쿠폰 추가
        koopon_add_btn.setOnClickListener(view -> {
            Coupon coupon = new Coupon();
            coupon.coupon_title = koopon_edit_title.getText().toString();
            coupon.date = koopon_edit_date.getText().toString();
            Toast.makeText(mcontext, coupon.CouponToString(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i("onActivityResult : %s,%s",requestCode,resultCode);
        if (requestCode == ALBUM_REQUEST_CODE){
            //select picture
            if (resultCode == Activity.RESULT_OK){
                try {
                    Uri uri = data.getData();
                    Logger.i(uri.getPath());
                    File file = new File(uri.getPath());
                    if (file.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        koopon_imgv.setImageBitmap(myBitmap);
                    } else {
                        Toast.makeText(mcontext, "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Logger.e("album error : %s",e.toString());
                }

            }
            // cancel select
            else {
                Toast.makeText(mcontext, "취소!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
