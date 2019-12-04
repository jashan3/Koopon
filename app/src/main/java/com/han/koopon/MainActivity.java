package com.han.koopon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.han.koopon.Home.HomeFragment;

import com.han.koopon.Login.LoginFragment;
import com.han.koopon.Main.MainFragment;
import com.han.koopon.Util.Listitem;
import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.PermissionRequest;
import com.han.koopon.Util.PhotoUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FrameLayout mainFrame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFrame = findViewById(R.id.mainFrame);

        String id = PFUtil.getPreferenceString(this,PFUtil.AUTO_LOGIN_ID);
        Logger.i("information : %s",id);
        if (TextUtils.isEmpty(id)){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, LoginFragment.newInstance()).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, MainFragment.newInstance()).commit();
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Logger.i("onActivityResult "+requestCode +","+requestCode);
//        if (requestCode == 1000 && resultCode == RESULT_OK) {
//            try {
//                Uri uri = data.getData();
//
////                String struri =  PhotoUtil.getRealPathFromURI_API19(this,uri);
//                Logger.i(uri.getPath());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (requestCode == 1000 && resultCode == RESULT_CANCELED) {
//            Toast.makeText(this, "선택 취소", Toast.LENGTH_SHORT).show();
//        }
//    }
}
