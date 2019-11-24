package com.han.koopon.Util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PermissionRequest {

    private Context mContext;
    private final static int REQUEST_CODE_PERMISSIONS = 1000;
   String [] permissionList = null;


    public PermissionRequest (Context mContext,   String [] permissionList){
        this.mContext = mContext;
        this.permissionList = permissionList;
    }

    public boolean askPermission(){
        boolean hasPermission = false;
        for (String permission : permissionList){
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(mContext,permission)){
                hasPermission = true;
            } else {
                hasPermission = false;
                return false;
            }
        }
        return hasPermission;
    }
}
