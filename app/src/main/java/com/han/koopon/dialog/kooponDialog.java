package com.han.koopon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.han.koopon.R;

public class kooponDialog extends Dialog {

    private Context mcontext;
    private EditText koopon_edit_date,koopon_edit_title;
    private ImageView koopon_imgv,close_btn;
    private Button koopon_add_btn;

    public kooponDialog(@NonNull Context context) {
        super(context);
        this.mcontext = context;
    }

    public kooponDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected kooponDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_koopon_edit);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        bindView();
    }


    private void bindView(){
        close_btn =  findViewById(R.id.close_btn);
        koopon_edit_title = findViewById(R.id.koopon_edit_title);
        koopon_edit_date = findViewById(R.id.koopon_edit_date);
        koopon_imgv = findViewById(R.id.koopon_imgv);
        koopon_add_btn = findViewById(R.id.koopon_add_btn);


        //이미지 불러오기
        koopon_imgv.setOnClickListener(view -> {
            Toast.makeText(mcontext, "image call!", Toast.LENGTH_SHORT).show();
        });

        //쿠폰 추가
        koopon_add_btn.setOnClickListener(view -> {
            Toast.makeText(mcontext, "add koopon", Toast.LENGTH_SHORT).show();
        });

        //종료
        close_btn.setOnClickListener((v)->{
            dismiss();
        });
    }
}
