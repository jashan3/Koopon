package com.han.koopon.Home;

import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.han.koopon.R;
import com.han.koopon.Util.PermissionRequest;
import com.han.koopon.Util.PhotoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String folderName = "바코드";
    private static final String TAG = "HomeFragment";
    private HomeViewModel mViewModel;
    private RecyclerView rv;
    private RelativeLayout rv_item_container;
    private List<String> imagePathList;
    private  String[] permisionList = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE };
    private static final int PERMISSION_CODE = 1000;

    View.OnClickListener itemClickListner = (v)->{
        Toast.makeText(getContext(), "Show image", Toast.LENGTH_SHORT).show();
    };

    View.OnLongClickListener itemLongClickListner = (v)->{
        Toast.makeText(getContext(), "edit this line", Toast.LENGTH_SHORT).show();
        return true;
    };

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.home_fragment, container, false);
        bindView(view);

        //recycler view
        imagePathList = new ArrayList();
        HomeRecyclerView adapter = new HomeRecyclerView(getContext(),imagePathList,itemClickListner,itemLongClickListner);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


        boolean isFolderExist =  PhotoUtil.isFolderExist(getContext(),"바코드");
        if (isFolderExist){
            imagePathList = PhotoUtil.getSpecificImages(folderName);
            Log.i(TAG,"## imagePathList size : "+imagePathList.size());
            adapter.updateItems(imagePathList);
        } else {
            Toast.makeText(getContext(), "'바코드' 이름으로 폴더를 생성해주세요.", Toast.LENGTH_SHORT).show();
        }

//        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "coupon").build();
//        db.userDao().getAll();
        return  view;
    }

    private void bindView (View view){
        rv = view.findViewById(R.id.home_rv);
        rv_item_container  = view.findViewById(R.id.rv_item_container);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
//            mHandler.sendEmptyMessage(HANDLER_MSG_GET_ALL_FOLDER);
//            mHandler.sendEmptyMessage(HANDLER_MSG_FOLDER_LIST);
        }catch (Exception e){
            Log.e(TAG,e.toString());
            boolean isGrant = false;
            isGrant = new PermissionRequest(getContext(),permisionList).askPermission();
            if(!isGrant){
                Toast.makeText(getContext(), "need permision", Toast.LENGTH_SHORT).show();
            } else {

            }
        }
    }
//    private void getSpecificImages (String CameraFolder){
//        ArrayList<String> f = new ArrayList<String>();// list of file paths
//        File[] listFile;
//        String absolutePath = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/"+CameraFolder;//사진 겔러리 위치
//        File file= new File(absolutePath);
//        if (file.isDirectory()) {
//            Log.d(TAG,"## isDirectory");
//            listFile = file.listFiles();
//            for (int i = 0; i < listFile.length; i++) {
////                Log.d(TAG,"## getAbsolutePath() : "+listFile[i].getAbsolutePath());
//                f.add(listFile[i].getAbsolutePath());
//            }
//        }
//
//    }
}
