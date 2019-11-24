package com.han.koopon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.han.koopon.Home.HomeFragment;

import com.han.koopon.Util.Listitem;
import com.han.koopon.Util.PermissionRequest;
import com.han.koopon.Util.PhotoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FrameLayout mainFrame;

    //
    private List<Listitem> list;
    private  String[] permisionList = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE };
    private static final int PERMISSION_CODE = 1000;

    //handler
    private static final int HANDLER_MSG_FOLDER_LIST = 100;
    private static final int HANDLER_MSG_IMAGE_LIST = 101;
    private static final int HANDLER_MSG_GET_ALL_FOLDER = 102;

    private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_MSG_FOLDER_LIST :
                    getFolerList();
                    break ;
                case HANDLER_MSG_IMAGE_LIST :
                    break ;
                case HANDLER_MSG_GET_ALL_FOLDER :
                    PhotoUtil photoUtil =  new PhotoUtil();
                    list = photoUtil.getAllPhotoPathList(MainActivity.this);
                    Log.e(TAG,"## "+list.size());
                    break ;
            }
        }
    } ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFrame = findViewById(R.id.mainFrame);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, HomeFragment.newInstance()).commit();
    }

    private void getFolerList (){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String[] folders = file.list();
        for(String str :folders){
            Log.d(TAG,"##"+ str);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
//            mHandler.sendEmptyMessage(HANDLER_MSG_GET_ALL_FOLDER);
        }catch (Exception e){
            Log.e(TAG,e.toString());

            boolean isGrant = false;
            isGrant = new PermissionRequest(this,permisionList).askPermission();
            if(!isGrant){
                Toast.makeText(this, "need permision", Toast.LENGTH_SHORT).show();
            } else {

            }
        }
    }


}
