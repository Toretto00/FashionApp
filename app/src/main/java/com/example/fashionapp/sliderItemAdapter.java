package com.example.fashionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class sliderItemAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<sliderImageItem> sliderImageItemArrayList;

    public sliderItemAdapter(Context context, ArrayList<sliderImageItem> sliderImageItemArrayList) {
        this.context = context;
        this.sliderImageItemArrayList = sliderImageItemArrayList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderlayout = inflater.inflate(R.layout.item_slider, null);

        ImageView imageView = sliderlayout.findViewById(R.id.imageSlider);

        imageView.setImageResource(sliderImageItemArrayList.get(position).getImage());

        container.addView(sliderlayout);

        return sliderlayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        if(sliderImageItemArrayList != null)
            return sliderImageItemArrayList.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
