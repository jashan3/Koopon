package com.han.koopon.MainDetail;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.han.koopon.Config;
import com.han.koopon.Main.Coupon;
import com.han.koopon.Main.FireBaseQuery;
import com.han.koopon.R;
import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.StringUtil;
import com.han.koopon.addCoupon.AddCouponActivity;
import com.han.koopon.dialog.KooponAlert;
import com.orhanobut.logger.Logger;

import java.io.File;

public class MainDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView detail_img;
    private TextView detail_title,detail_date;
    private EditText detail_body;
    private Button edit_btn;

    private String imgPath;
    private  String title;
    private String date;
    private  String body;

    private final static int HANDLER_EDIT_COUPON = 1000;
      Handler detailHandler = new  Handler(){
          @Override
          public void handleMessage(@NonNull Message msg) {
              super.handleMessage(msg);
              switch (msg.what){
                  case HANDLER_EDIT_COUPON :
                      upadateData();
                      break;
              }
          }
      };

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
        detail_body = findViewById(R.id.detail_body);
        edit_btn =  findViewById(R.id.edit_btn);
        edit_btn.setOnClickListener(this);
    }

    private void getIntentData(){
        Bundle b = getIntent().getExtras();

        title =  b.getString(Config.INTENT_EXTRA_TITLE);
        imgPath =  b.getString(Config.INTENT_EXTRA_CURRENT_COUNT);
        date = b.getString(Config.INTENT_EXTRA_DATE);
        body = b.getString(Config.INTENT_EXTRA_BODY);

        if (title != null && imgPath !=null && date!=null){
            Logger.i("TITLE : %s , IMG Path : %s",title,imgPath);
            //상단 텍스트
            detail_title.setText(title);
            detail_date.setText("~ "+date+"까지");
            detail_body.setText(body);

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

    @Override
    public void onClick(View view) {
         switch (view.getId()) {
             case R.id.edit_btn:
                 detailHandler.sendEmptyMessage(HANDLER_EDIT_COUPON);
                 break;
         }
    }

    private void upadateData(){
        edit_btn.setVisibility(View.GONE);
        //vo 셋팅
        Coupon coupon = new Coupon();
        coupon.imgURL = imgPath;/*필수 저장값*/
        coupon.coupon_title = title;/*필수 저장값*/
        coupon.date = date;/*필수 저장값*/
        coupon.isUse = false;
        coupon.coupon_body = detail_body.getText().toString();

        String userID = PFUtil.getPreferenceString(MainDetailActivity.this,PFUtil.AUTO_LOGIN_ID);
        userID = StringUtil.emailToStringID(userID);
        FireBaseQuery.insertFBNeedKeyWithCallback(StringUtil.base64(imgPath), coupon, userID, new FireBaseQuery.InsertCallback() {
            @Override
            public void onSuccess() {
                showToast("onSuccess");
                edit_btn.setVisibility(View.VISIBLE);
                body = detail_body.getText().toString();
            }

            @Override
            public void onFail(Exception e) {
                showToast("onFail : "+ e.toString());
                edit_btn.setVisibility(View.VISIBLE);
            }
        });
    }


    private void showToast(String m){
        Toast.makeText(MainDetailActivity.this,m,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        if (!body.equals(detail_body.getText().toString())){
           new KooponAlert(MainDetailActivity.this, "", "변경된 작업이 있습니다.\n저장 하지않고 돌아가시겠습니까? ", "예", "아니오", new KooponAlert.onKPClickListener() {
               @Override
               public void onPositive(View view) {
                    finish();
               }

               @Override
               public void onNegetive(View view) {

               }
           }).show();
        } else {
            super.onBackPressed();

        }
    }
}
