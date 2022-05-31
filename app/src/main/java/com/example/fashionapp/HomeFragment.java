package com.example.fashionapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private ViewPager viewPager;
    private CircleIndicator  tabLayout;
    private ArrayList<sliderImageItem> sliderImageItemArrayList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.viewPageSlider);
        tabLayout = view.findViewById(R.id.tabLayoutSlider);

        sliderImageItemArrayList = new ArrayList<>();
        sliderImageItemArrayList.add(new sliderImageItem(R.drawable.slider1));
        sliderImageItemArrayList.add(new sliderImageItem(R.drawable.slider2));
        sliderImageItemArrayList.add(new sliderImageItem(R.drawable.slider3));

        sliderItemAdapter sliderItemAdapter = new sliderItemAdapter(getContext(), sliderImageItemArrayList);
        viewPager.setAdapter(sliderItemAdapter);

        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new slider_time_task(), 3000, 3000);

        tabLayout.setViewPager(viewPager);
    }

    public class slider_time_task extends TimerTask{

        @Override
        public void run() {
            if(HomeFragment.this.getActivity() == null)
                return;
            HomeFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() < sliderImageItemArrayList.size()-1)
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    else
                        viewPager.setCurrentItem(0);
                }
            });
        }
    }
}