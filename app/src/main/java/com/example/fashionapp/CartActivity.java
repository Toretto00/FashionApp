package com.example.fashionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private GridView cart_list;
    private ArrayList<product> productArrayList = new ArrayList<product>();
    private custom_gridview_adapter customGridviewAdapter;
    private TextView cart_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        imageButton = findViewById(R.id.cart_back_to_maim_Btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });

        cart_list = findViewById(R.id.cart_list);
        cart_textview = findViewById(R.id.cart_textview);
//        productArrayList.add(new product("Product 1", "100.000"+"đ", R.drawable.home));
//        productArrayList.add(new product("Product 2", "200.000"+"đ", R.drawable.cheque));
//        productArrayList.add(new product("Product 3", "300.000"+"đ", R.drawable.shopping_bag));
//        productArrayList.add(new product("Product 4", "400.000"+"đ", R.drawable.buying));
//        productArrayList.add(new product("Product 5", "500.000"+"đ", R.drawable.user));

        if(productArrayList.size() != 0)
        {
            customGridviewAdapter = new custom_gridview_adapter(R.layout.item_custom_grid_view, this, productArrayList);
            cart_list.setAdapter(customGridviewAdapter);
            cart_list.setVisibility(View.VISIBLE);
            cart_textview.setVisibility(View.INVISIBLE);
        }
        else
        {
            cart_list.setVisibility(View.INVISIBLE);
            cart_textview.setVisibility(View.VISIBLE);
        }


//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startActivity(new Intent(getContext(), ShowDetailActivity.class));
//            }
//        });
    }
}