package com.han.koopon.Home;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.han.koopon.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView rv;
    private RelativeLayout rv_item_container;

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
        List list = new ArrayList();
        HomeRecyclerView adapter = new HomeRecyclerView(getContext(),list,itemClickListner,itemLongClickListner);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

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

}
