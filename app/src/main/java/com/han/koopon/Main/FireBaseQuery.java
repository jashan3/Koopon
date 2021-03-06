package com.han.koopon.Main;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireBaseQuery {

    public final static String ROOT = "koopon";
    public final static String TYPE1 = "Coupon";
    public final static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



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

    public interface InsertCallback{
        void onSuccess();
        void onFail( Exception e);
    }
    public static void insertFBNeedKeyWithCallback(String key,Coupon coupon,String userID,InsertCallback callback){
        Logger.i("암호화된 key %s",key);
        Map<String, Object> postValues = coupon.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+ROOT+"/"+userID+"/"+TYPE1+"/" +key, postValues);
        mDatabase.updateChildren(childUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(e);
            }
        });
    }

    public static void deleteFBNeedKey(String key,Coupon coupon,String userID){
        Logger.i("암호화된 key %s",key);
        Map<String, Object> postValues = coupon.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+ROOT+"/"+userID+"/"+TYPE1+"/" +key, null);
        mDatabase.updateChildren(childUpdates);
    }

    public static void  selectOnceFB(String userID) {
        List list = new ArrayList();
        mDatabase.child(ROOT).child(userID).child(TYPE1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Coupon post = snapshot.getValue(Coupon.class);
                    list.add(post);
                }
                Logger.i(list.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
