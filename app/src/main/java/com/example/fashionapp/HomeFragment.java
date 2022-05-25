package com.example.fashionapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Toolbar myToolbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //setSupportActionBar(myToolbar);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private ViewPager viewPager;
    private CircleIndicator  tabLayout;
    private ArrayList<sliderImageItem> sliderImageItemArrayList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myToolbar = (Toolbar) view.findViewById(R.id.myToolbar);
        myToolbar.inflateMenu(R.menu.menu_bar);
        myToolbar.setTitle(null);
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id)
                {
                    case R.id.cartShopping:
                        startActivity(new Intent(getContext(), CartActivity.class));
                        break;
                }
                return false;
            }
            });

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