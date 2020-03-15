package com.han.koopon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.han.koopon.Login.LoginFragment;
import com.han.koopon.Main.MainFragment;
import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.PushUtil;
import com.han.koopon.Util.SchedulerUtil;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FrameLayout mainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFrame = findViewById(R.id.mainFrame);
        askpermission();
        String id = PFUtil.getPreferenceString(this,PFUtil.AUTO_LOGIN_ID);
        Logger.i("information : %s",id);

        if (id == null){
            toFragment(LoginFragment.newInstance());
        }
        else if ("".equals(id)){
            toFragment(LoginFragment.newInstance());
        }
        else {
            toFragment(MainFragment.newInstance());
            SchedulerUtil.runScheudlerAlways(this);
        }

        PushUtil.createNotificationChannel(this);
    }

    private void toFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
//                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.mainFrame, fragment)
                .commit();
    }

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i("onNewIntent : ");
    }


    private void askpermission(){
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1111);
            } else {
                //granted
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
