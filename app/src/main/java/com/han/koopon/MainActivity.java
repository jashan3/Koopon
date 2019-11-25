package com.han.koopon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.han.koopon.Home.HomeFragment;

import com.han.koopon.Login.LoginFragment;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFrame = findViewById(R.id.mainFrame);
//        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, HomeFragment.newInstance()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, LoginFragment.newInstance()).commit();

    }




}
