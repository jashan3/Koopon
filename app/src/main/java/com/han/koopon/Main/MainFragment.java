package com.han.koopon.Main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.han.koopon.Home.HomeRecyclerView;
import com.han.koopon.R;
import com.han.koopon.Util.PhotoUtil;
import com.han.koopon.dialog.kooponDialog;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    private ImageView add_btn;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.main_fragment, container, false);
        bindView(view);
        return  view;
    }

    private void bindView (View view){
//        rv = view.findViewById(R.id.home_rv);
        //recycler view
//        imagePathList = new ArrayList();
//        HomeRecyclerView adapter = new HomeRecyclerView(getContext(),imagePathList,itemClickListner,itemLongClickListner);
//        LinearLayoutManager lm = new LinearLayoutManager(getContext());
//        rv.setLayoutManager(lm);
//        rv.setAdapter(adapter);

        add_btn  = view.findViewById(R.id.add_btn);
        add_btn.setOnClickListener((v)->{
            new kooponDialog(getContext()).show();
        });
    }
}
