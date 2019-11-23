package com.han.koopon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.han.koopon.Home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mainFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFrame = findViewById(R.id.mainFrame);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, HomeFragment.newInstance()).commit();


    }
}
