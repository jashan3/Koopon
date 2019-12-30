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

public class KooponAlert extends Dialog implements View.OnClickListener {
    private LinearLayout ll_titleContainer,ll_btnContainer;
    private ImageView iv_icon;
    private TextView tv_title,tv_body;
    private ImageButton ib_close;
    private Button btn_del,btn_cancel;
    private View.OnClickListener delListner;

    public KooponAlert(@NonNull Context context) {
        super(context);
    }

    public KooponAlert(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected KooponAlert(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

//    public interface CustomDialogListener{
//        public void onPositiveClicked(View.OnClickListener v);
//    }
//
//    //호출할 리스너
//    public void setDialogListener(CustomDialogListener customDialogListener){
//        this.customDialogListener = customDialogListener;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert_koopon);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        bindView();
        settingListner();
    }

    public void setListner(View.OnClickListener v){
        this.delListner = v;
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
    }

    private void settingListner(){
        ib_close.setOnClickListener(this);
        btn_del.setOnClickListener(delListner);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
