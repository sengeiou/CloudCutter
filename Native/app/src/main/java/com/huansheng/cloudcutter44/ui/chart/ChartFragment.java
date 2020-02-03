package com.huansheng.cloudcutter44.ui.chart;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.huansheng.cloudcutter44.R;
import com.huansheng.cloudcutter44.ui.home.HomeFragment;

public class ChartFragment extends Fragment {
    private static int ShowType=0;
    private TabLayout tabhot;
    private TabItem t0;
    private TabItem t1;
    private View daily;
    private View models;
    private ChartViewModel mViewModel;

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.chart_fragment, container, false);

        this.tabhot = root.findViewById(R.id.tabhot);
        this.daily = root.findViewById(R.id.daily);
        this.models = root.findViewById(R.id.models);
        this.t0 = root.findViewById(R.id.t0);
        this.t1 = root.findViewById(R.id.t1);

        this.tabhot.getTabAt(ChartFragment.ShowType).select();

        this.tabhot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

//                Toast toast=Toast.makeText(getContext(),"Toast提示消息"+tab.getPosition(),Toast.LENGTH_SHORT    );
//                toast.show();
                ChartFragment.ShowType=tab.getPosition();
                setTabVisable();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChartViewModel.class);
        // TODO: Use the ViewModel
    }

    public void setTabVisable() {
        int position=ChartFragment.ShowType;
        Log.e("setTabVisable", String.valueOf(position));
        this.daily.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        this.models.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
    }
}
