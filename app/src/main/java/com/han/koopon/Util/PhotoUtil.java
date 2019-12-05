package com.han.koopon.Util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PhotoUtil {

    public ArrayList<Listitem> getAllPhotoPathList(Context context) {
        String strImage =null;
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

    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);
        String id = wholeID.split(":")[1];
        String[] column = { MediaStore.Images.Media.DATA };
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{ id }, null);
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    //Uri -> Bitmap
    public static Bitmap getThumbnailToURI(Context context, Uri selectedImage){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return BitmapFactory.decodeFile(picturePath);
    }

    //썸네일 path리턴함
    public static List<String> getImageThumnail(Context context){
        // First request thumbnails what you want
        List<String> list = null;
        String[] projection = new String[] {MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID};
        Cursor thumbnails = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);

        // Then walk thru result and obtain imageId from records
        for (thumbnails.moveToFirst(); !thumbnails.isAfterLast(); thumbnails.moveToNext()) {
            String imageId = thumbnails.getString(thumbnails.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));

            // Request image related to this thumbnail
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor images =  context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, filePathColumn, MediaStore.Images.Media._ID + "=?", new String[] {imageId}, null);

            if (images != null && images.moveToFirst()) {
                // Your file-path will be here
                String filePath = images.getString(images.getColumnIndex(filePathColumn[0]));
                list.add(filePath);
            }

        }
        return list;
    }

    //    private void saveFile() {
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, "image_1024.JPG");
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
//
//        if (Build.VERSION.SDK_INT &gt;= Build.VERSION_CODES.Q) {
//            values.put(MediaStore.Images.Media.IS_PENDING, 1);
//        }
//
//        ContentResolver contentResolver = getContentResolver();
//        Uri item = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        try {
//            ParcelFileDescriptor pdf = contentResolver.openFileDescriptor(item, "w", null);
//
//            if (pdf == null) {
//                Log.d("asdf", "null");
//            } else {
//                String str = "heloo";
//                byte[] strToByte = str.getBytes();
//                FileOutputStream fos = new FileOutputStream(pdf.getFileDescriptor());
//                fos.write(strToByte);
//                fos.close();
//
//                if (Build.VERSION.SDK_INT &gt;= Build.VERSION_CODES.Q) {
//                    values.clear();
//                    values.put(MediaStore.Images.Media.IS_PENDING, 0);
//                    contentResolver.update(item, values, null, null);
//                }
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void readAllFile(Context context) {
        Uri externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.MIME_TYPE
        };
        Cursor cursor = context.getContentResolver().query(externalUri, projection, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            Logger.e("null or empty");
            return;
        }
        do {
            String contentUrl = externalUri.toString() + "/" + cursor.getString(0);

            try {
                InputStream is = context.getContentResolver().openInputStream(Uri.parse(contentUrl));
                int data = 0;
                StringBuilder sb = new StringBuilder();

                while ((data = is.read()) != -1) {
                    sb.append((char) data);
                }

                is.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } while (cursor.moveToNext());
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Bitmap getBitmap(String path) {
        try {
            Bitmap bitmap = null;
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
