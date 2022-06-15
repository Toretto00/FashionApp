package com.example.fashionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerviewAdapter extends RecyclerView.Adapter<CustomRecyclerviewAdapter
        .CustomRecyclerviewHolder> {
    CustomRecyclerview[] customRecyclerview;
    List<String> arrayProductName, arrayColorSize, arrayPrice, arrayQuantity;

    Context context;

    public static class CustomRecyclerviewHolder extends RecyclerView.ViewHolder{
        public ImageView productImageRec;
        public TextView productName, colorSize, priceProductRec;
        public TextView quantityRec;

        public CustomRecyclerviewHolder(@NonNull View itemView) {
            super(itemView);
            productImageRec = itemView.findViewById(R.id.imageViewProduct);
            productName = itemView.findViewById(R.id.productNameRec);
            colorSize = itemView.findViewById(R.id.colorSizeRec);
            priceProductRec = itemView.findViewById(R.id.priceRec);
            quantityRec = itemView.findViewById(R.id.quantityRec);
        }
    }

    public CustomRecyclerviewAdapter(Context context, CustomRecyclerview[] customRecyclerview){
        this.customRecyclerview = customRecyclerview;
        this.context=context;
    }

    @NonNull
    @Override
    public CustomRecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recycler_view, parent, false);
        CustomRecyclerviewHolder crh = new CustomRecyclerviewHolder(v);
        return crh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerviewHolder holder, int position) {
        final CustomRecyclerview mCustomRecyclerViewList = customRecyclerview[position];

        Picasso.get().load(mCustomRecyclerViewList.getProductImageRec()).into(holder.productImageRec);
        holder.productName.setText(mCustomRecyclerViewList.getProductName());
        holder.colorSize.setText(mCustomRecyclerViewList.getColorSize());
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(Integer.valueOf(mCustomRecyclerViewList
                .getPriceProductRec()));
        holder.priceProductRec.setText(formattedNumber + " VND");
        holder.quantityRec.setText(mCustomRecyclerViewList.getQuantityRec());
    }

    @Override
    public int getItemCount() {
        return customRecyclerview.length;
    }
}
