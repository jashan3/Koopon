package com.han.koopon.Util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PhotoUtil {
    String strImage;
    public ArrayList<Listitem> getAllPhotoPathList(Context context) {
        ArrayList<Listitem> photos = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.DATA,
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        // 사진 아이디
        int columnIndexId = cursor.getColumnIndex(MediaStore.Images.Media._ID);

        // 사진 경로
        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        //사진 데이터
        int nCol = cursor.getColumnIndex(MediaStore.Images.Media.DATA); // bitmap

        while (cursor.moveToNext()) {
            strImage = cursor.getString(nCol);

            if( strImage.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath()))
            {
                Listitem Photo = new Listitem(cursor.getString(columnIndexId), cursor.getString(columnIndexData));
                photos.add(Photo);
            }
        }

        cursor.close();

        // 최근 순으로 정렬
        Collections.sort(photos, new DescendingId());

        return photos;
    }

    class DescendingId implements Comparator<Listitem> {
        @Override
        public int compare(Listitem Photo, Listitem t1) {
            return ((Integer)Integer.parseInt(t1.getId())).compareTo((Integer)Integer.parseInt(Photo.getId()));
        }
    }

    public static boolean isFolderExist (Context context , String folderName){
        File file = null;
        String[] folders = null;

        file =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        folders = file.list();
        if (folders!=null){
            for(String str :folders){
                if(str.equals(folderName)){
                   return true;
                }
            }
        }

        return false;
    }

    public static List<String> getSpecificImages (String CameraFolder){
        ArrayList<String> f = null;
        File[] listFile = null;
        f =  new ArrayList<String>();
        String absolutePath = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/"+CameraFolder;//사진 겔러리 위치
        File file= new File(absolutePath);
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
        return f;
    }
}
