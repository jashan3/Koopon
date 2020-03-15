package com.han.koopon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.han.koopon.R;

public  class KooponAlert extends Dialog implements View.OnClickListener {

    public interface onKPClickListener{
         void onPositive(View view);
         void onNegetive(View view);
    }

    private LinearLayout ll_titleContainer,ll_btnContainer;
    private ImageView iv_icon;
    private TextView tv_title,tv_body;
    private ImageButton ib_close;
    private Button btn_del,btn_cancel;

    private onKPClickListener kpListener;
    private String title,body,ok,cancel;

    public KooponAlert(@NonNull Context context) {
        super(context);
    }

    public KooponAlert(@NonNull Context context,onKPClickListener kpListener) {
        super(context);
        this.kpListener = kpListener;
    }

    public KooponAlert(@NonNull Context context,String title,String body,String ok,String cancel,onKPClickListener kpListener) {
        super(context);
        this.title = title;
        this.body = body;
        this.ok = ok;
        this.cancel = cancel;
        this.kpListener = kpListener;
    }



    public static KooponAlert from( Context context,onKPClickListener kpListener){
        return  new KooponAlert(context,kpListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert_koopon);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        bindView();
        settingListner();
    }

    private void bindView(){
        ll_titleContainer = findViewById(R.id.dialog_title_container);
        iv_icon = findViewById(R.id.dialog_iv);
        tv_title = findViewById(R.id.dialog_title_tv);
        ib_close = findViewById(R.id.dialog_close_iv);

        tv_body = findViewById(R.id.dialog_body_tv);

        ll_btnContainer = findViewById(R.id.dialog_btn_container);
        btn_del = findViewById(R.id.dialog_btn_del);
        btn_cancel = findViewById(R.id.dialog_btn_cancel);

        if(title !=null){
            tv_title.setText(title);
        }

        if(tv_body !=null){
            tv_body.setText(body);
        }

        if(ok !=null){
            btn_del.setText(ok);
        }

        if(cancel !=null){
            btn_cancel.setText(cancel);
        }


    }

    private void settingListner(){
        ib_close.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        dismiss();

       switch (view.getId()){
           case R.id.dialog_btn_del:
               kpListener.onPositive(view);
               break;
           case R.id.dialog_btn_cancel:
               kpListener.onNegetive(view);
               break;
       }
    }
}
