package com.han.koopon.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.han.koopon.R;
import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.PhotoUtil;
import com.han.koopon.Util.StringUtil;
import com.han.koopon.dialog.kooponDialog;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class MainFragment extends Fragment {
    //var
    private static final String folderName = "바코드";
    private List<Coupon> couponList;
    public String userID;

    //permission
    private  String[] permisionList = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE };
    private static final int PERMISSION_CODE = 1000;

    //widget
    private ImageView add_btn;
    private RecyclerView rv;
    private ProgressBar main_progress_bar;
    private  Spinner spinner;

    //listener
    MainRecyclerview adapter;
    View.OnClickListener itemClickListner;
    View.OnLongClickListener itemLongClickListner;

    //firebase
    private final static String ROOT = "koopon";
    private final static String TYPE1 = "Coupon";
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {

//        CouponVO couponVO = new CouponVO();
//        couponVO.setCoupon_title("sdfgsdfgdsfgsdfgg 111111111.");
//        couponVO.setCoupon_body("설명");
//        couponVO.setImgURL("moimage");
//        couponVO.setDate("날짜");
//        couponVO.setUse(false);
//        writeFirebase(couponVO);

        couponList = new ArrayList();
        userID = PFUtil.getPreferenceString(getContext(),PFUtil.AUTO_LOGIN_ID);
        userID = StringUtil.emailToStringID(userID);

//        boolean isFolderExist =  PhotoUtil.isFolderExist(getContext(),folderName);
//        if (isFolderExist){
//            Toast.makeText(getContext(), "폴더확인", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "'바코드' 이름으로 폴더를 생성해주세요.", Toast.LENGTH_SHORT).show();
//        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.main_fragment, container, false);
        bindView(view);

        Logger.i("id : %s",userID);

        selectAlwaysFB(userID);
        return  view;
    }

    private void bindView (View view){
        rv = view.findViewById(R.id.main_rv);
        main_progress_bar = view.findViewById(R.id.main_progress_bar);
        adapter = new MainRecyclerview(getContext(),couponList,itemClickListner,itemLongClickListner);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        new ItemTouchHelper(adapter.itemTouchHelperCallback).attachToRecyclerView(rv);

        add_btn  = view.findViewById(R.id.add_btn);
        add_btn.setOnClickListener((v)->{
            kooponDialog.newInstance().show(getFragmentManager(),"");
        });

    }

    private void initFirebase (String userid){
        mDatabase.child("koopon").child(userid).child("Coupon").child(userid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Coupon post = snapshot.child(userid).child("Coupon").getValue(Coupon.class);
                    try {
                        Logger.i( post.coupon_title);

                    }catch (Exception e){
                        Logger.e(e.toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Logger.e("databaseError ==> %s",databaseError.getMessage());
            }
        });
    }

    public void  selectOnceFB(String userID) {
        main_progress_bar.setVisibility(View.VISIBLE);
        mDatabase.child(ROOT).child(userID).child(TYPE1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Coupon post = snapshot.getValue(Coupon.class);
                    couponList.add(post);
                }
                adapter.updateItems(couponList);
                adapter.notifyDataSetChanged();
                main_progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void  selectAlwaysFB(String userID) {
        main_progress_bar.setVisibility(View.VISIBLE);
        mDatabase.child(ROOT).child(userID).child(TYPE1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                couponList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Coupon post = snapshot.getValue(Coupon.class);
                    couponList.add(post);
                }
                Collections.sort(couponList, new Comparator<Coupon>() {
                    @Override
                    public int compare(Coupon coupon1, Coupon coupon2) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = format.parse(coupon1.date);
                            date2 = format.parse(coupon2.date);
                        }catch (Exception e){
                            Logger.e("casting error");
                        }


                        //date1이 클때 && 아직 사용 안햇을떄.&& !coupon1.isUse
                        if (date1.compareTo(date2) > 0 ) {
                            return -1;
                        }
                        //date1이 작을떄
                        else if (date1.compareTo(date2) < 0) {
                            return 1;
                        }
                        //같을때
                        else {
                            return 0;
                        }
                    }
                });

                Collections.sort(couponList, new Comparator<Coupon>() {
                    @Override
                    public int compare(Coupon coupon1, Coupon coupon2) {
                        //date1이 클때 && 아직 사용 안햇을떄.&& !coupon1.isUse
                        if (!coupon1.isUse ) {
                            return -1;
                        }
                        else {
                            return 1;
                        }
                    }
                });

                adapter.updateItems(couponList);
                adapter.notifyDataSetChanged();
                main_progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                main_progress_bar.setVisibility(View.GONE);
            }
        });
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