package com.han.koopon.Main;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public class FireBaseQuery {

    private final static String ROOT = "koopon";
    private final static String TYPE1 = "Coupon";
    private final static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



    public static void insertFB(Coupon coupon,String userID){
        String key = mDatabase.child(ROOT).child(userID).child(TYPE1).push().getKey();
        Logger.i("key %s",key);
        Map<String, Object> postValues = coupon.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+ROOT+"/"+userID+"/"+TYPE1+"/" +key, postValues);
        mDatabase.updateChildren(childUpdates)
                 .addOnSuccessListener((Void aVoid) ->{
                    Logger.i("insertFB success");
                 });
    }

    public static void insertFBNeedKey(String key,Coupon coupon,String userID){
        Logger.i("암호화된 key %s",key);
        Map<String, Object> postValues = coupon.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+ROOT+"/"+userID+"/"+TYPE1+"/" +key, postValues);
        mDatabase.updateChildren(childUpdates);
    }

    public static void  selectOnceFB(String userID) {
        mDatabase.child(ROOT).child(userID).child(TYPE1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Coupon post = snapshot.child(userID).child(TYPE1).getValue(Coupon.class);
                    try {
                        Logger.i( post.coupon_title);
                    }catch (Exception e){
                        Logger.e(e.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
