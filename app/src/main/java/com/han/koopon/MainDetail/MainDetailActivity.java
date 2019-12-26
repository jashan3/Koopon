package com.han.koopon.MainDetail;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.han.koopon.Config;
import com.han.koopon.R;
import com.orhanobut.logger.Logger;

import java.io.File;

public class MainDetailActivity extends AppCompatActivity {


    private ImageView detail_img;
    private TextView detail_title,detail_date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);
        bindView();
        getIntentData();
    }

    private void bindView(){
        detail_title =  findViewById(R.id.detail_title);
        detail_img = findViewById(R.id.detail_img);
        detail_date = findViewById(R.id.detail_date);
    }

    private void getIntentData(){
        Bundle b = getIntent().getExtras();
        String title =  b.getString(Config.INTENT_EXTRA_TITLE);
        String imgPath =  b.getString(Config.INTENT_EXTRA_CURRENT_COUNT);
        String date = b.getString(Config.INTENT_EXTRA_DATE);

        if (title != null && imgPath !=null && date!=null){
            Logger.i("TITLE : %s , IMG Path : %s",title,imgPath);
            //상단 텍스트
            detail_title.setText(title);
            detail_date.setText("~ "+date+"까지");

            //하단 이미지
            try {
                Logger.i("imgurl == > %s" ,imgPath);
                File file = new File(imgPath);
                if (file.exists()){
                    detail_img.setImageURI(Uri.parse(imgPath));
                }
                else {
                    detail_img.setImageResource(R.drawable.not_found_img);
                }
            }catch (Exception e){
                Logger.e("MainRecyclerview :" + e.toString());
                detail_img.setImageResource(R.drawable.not_found_img);
            }
        } else {
            Toast.makeText(this, "정보 처리 실패", Toast.LENGTH_SHORT).show();
        }

    }
}
