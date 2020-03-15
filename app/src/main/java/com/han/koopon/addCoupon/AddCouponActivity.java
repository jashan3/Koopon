package com.han.koopon.addCoupon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.han.koopon.Main.Coupon;
import com.han.koopon.Main.FireBaseQuery;
import com.han.koopon.R;
import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.PhotoUtil;
import com.han.koopon.Util.StringUtil;
import com.orhanobut.logger.Logger;

import java.util.Calendar;

public class AddCouponActivity extends AppCompatActivity implements ScrollView.OnScrollChangeListener {
    private EditText koopon_edit_date,koopon_edit_title,memoET;
    private ImageView koopon_imgv,close_btn;
    private Button koopon_add_btn;
    private DatePicker datePicker;
    private ScrollView scrollView;

    private final static int ALBUM_REQUEST_CODE = 1001;

    private  String uriPath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_add_coupon);
        bindView();
    }

    private void bindView(){
        close_btn = findViewById(R.id.close_btn);
        koopon_edit_title =findViewById(R.id.koopon_edit_title);
        koopon_edit_date = findViewById(R.id.koopon_edit_date);
        koopon_edit_date.setEnabled(false);
        koopon_imgv = findViewById(R.id.koopon_imgv);
        koopon_add_btn = findViewById(R.id.koopon_add_btn);
        scrollView= findViewById(R.id.scrollView);
        memoET = findViewById(R.id.memo);
        scrollView.setOnScrollChangeListener(this);
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
            finish();
        });

        //데이트 픽커
        datePicker =  findViewById(R.id.koopon_datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Logger.i("Date : Year = %d ,Month= %d, day= %d",year,month+1,dayOfMonth);
                koopon_edit_date.setText(year+"/"+(month+1)+"/"+dayOfMonth);
            }
        });

        //쿠폰 추가
        koopon_add_btn.setOnClickListener(view -> {

            String title = koopon_edit_title.getText().toString();
            String date = koopon_edit_date.getText().toString();
            String memo = memoET.getText().toString();

            //유효성체크
            //파일 경로
            if (uriPath==null || "".equals(uriPath)){
                Toast.makeText(AddCouponActivity.this, "이미지를 올려주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            //제목
            if (title==null || "".equals(title)){
                Toast.makeText(AddCouponActivity.this, "쿠폰이름을 써주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            //날짜.
            if (date==null || "".equals(date)){
                Toast.makeText(AddCouponActivity.this, "날짜를 써주세요", Toast.LENGTH_SHORT).show();
                return;
            }



            //vo 셋팅
            Coupon coupon = new Coupon();
            coupon.imgURL = uriPath;/*필수 저장값*/
            coupon.coupon_title = title;/*필수 저장값*/
            coupon.date = date;/*필수 저장값*/
            coupon.isUse = false;
            coupon.coupon_body = memo;


            String userID = PFUtil.getPreferenceString(AddCouponActivity.this,PFUtil.AUTO_LOGIN_ID);
            userID = StringUtil.emailToStringID(userID);
            FireBaseQuery.insertFBNeedKey( StringUtil.base64(uriPath),coupon,userID);


            new Handler().postDelayed(()->{
                finish();
            },1000);
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
                    Uri selectedImage = data.getData();

                    uriPath = PhotoUtil.getRealPathFromURI(AddCouponActivity.this,selectedImage);/*저장값*/
                    Bitmap bitmap= PhotoUtil.getThumbnailToURI(AddCouponActivity.this,selectedImage);
                    koopon_imgv.setImageBitmap( bitmap);

                    /**************** string -> bitmap -> set image bitmap *****************/
//                    Uri uri = Uri.parse(uriPath);
//                    Bitmap bitmap = getBitmap(PhotoUtil.getRealPathFromURI(AddCouponActivity.this,selectedImage));
//                    koopon_imgv.setImageBitmap( bitmap);
                }catch (Exception e){
                    Logger.e("album error : %s",e.toString());
                }

            }
            // cancel select
            else {
                Toast.makeText(AddCouponActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        if (view.getId() == R.id.koopon_edit_title){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
