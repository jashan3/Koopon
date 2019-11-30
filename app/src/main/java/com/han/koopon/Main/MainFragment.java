package com.han.koopon.Main;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.han.koopon.Home.SQL.Coupon;
import com.han.koopon.R;
import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.PhotoUtil;
import com.han.koopon.dialog.kooponDialog;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainFragment extends Fragment {
    //var
    private static final String folderName = "바코드";
    private List<CouponVO> couponList;
    private String userID;

    //permission
    private  String[] permisionList = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE };
    private static final int PERMISSION_CODE = 1000;

    //widget
    private ImageView add_btn;
    private RecyclerView rv;

    //listener
    View.OnClickListener itemClickListner;
    View.OnLongClickListener itemLongClickListner;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        userID = PFUtil.getPreferenceString(getContext(),PFUtil.AUTO_LOGIN_ID);
        Logger.i("id : %s",userID);
//        initFirebase("sang9163");

//        CouponVO couponVO = new CouponVO();
//        couponVO.setCoupon_title("sdfgsdfgdsfgsdfgg 111111111.");
//        couponVO.setCoupon_body("설명");
//        couponVO.setImgURL("moimage");
//        couponVO.setDate("날짜");
//        couponVO.setUse(false);
//
//        writeFirebase(couponVO);

        boolean isFolderExist =  PhotoUtil.isFolderExist(getContext(),folderName);
        if (isFolderExist){
            Toast.makeText(getContext(), "폴더확인", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "'바코드' 이름으로 폴더를 생성해주세요.", Toast.LENGTH_SHORT).show();
        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        couponList = new ArrayList();
        View view =inflater.inflate(R.layout.main_fragment, container, false);
        bindView(view);

        return  view;
    }

    private void bindView (View view){
        rv = view.findViewById(R.id.main_rv);

        MainRecyclerview adapter = new MainRecyclerview(getContext(),couponList,itemClickListner,itemLongClickListner);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        add_btn  = view.findViewById(R.id.add_btn);
        add_btn.setOnClickListener((v)->{
            new kooponDialog(getContext()).show();
        });
    }

    private void initFirebase (String userid){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("koopon").child("Coupon").child(userid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CouponVO post = dataSnapshot.getValue(CouponVO.class);

                if(dataSnapshot.exists()){
                    Logger.i("success");

                }
                else {
                    Logger.e("not exists");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Logger.e("databaseError ==> %s",databaseError.getMessage());
            }
        });
    }

    private void writeFirebase(CouponVO couponVO){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("koopon").child("sang9163").child("Coupon").push().getKey();

        Map<String, Object> postValues = couponVO.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/koopon/sang9163/Coupon/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }


}
//        CouponVO couponVO = new CouponVO();
//        couponVO.setCoupon_userID(id);
//        couponVO.setCoupon_title("sdfgsdfgdsfgsdfgg 111111111.");
//        couponVO.setCoupon_body("설명");
//        couponVO.setImgURL("moimage");
//        couponVO.setDate("날짜");
//        couponVO.setUse(false);
//        couponList.add(couponVO);
//
//        CouponVO couponVO2 = new CouponVO();
//        couponVO2.setCoupon_userID(id);
//        couponVO2.setCoupon_title("sdfsdfgdsfgsdfg.");
//        couponVO2.setCoupon_body("설명123");
//        couponVO2.setImgURL("moimage124");
//        couponVO2.setDate("날짜veevadf");
//        couponVO2.setUse(true);
//        couponList.add(couponVO2);