package com.example.fashionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Button paymentCart;
    private GridView cart_list;
    private ArrayList<cartProduct> productArrayList = new ArrayList<cartProduct>();
    private custom_cart_gridview_adapter customCartGridviewAdapter;
    private TextView cart_textview, totalPriceCart, promotionPriceCart, shipPriceCart, intoPriceCart, quantityCart;

    private Database database;
    DatabaseReference mData;

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

        paymentCart = findViewById(R.id.paymentCart);
        cart_list = findViewById(R.id.cart_list);
        cart_textview = findViewById(R.id.cart_textview);
        totalPriceCart = findViewById(R.id.totalPriceCart);
        promotionPriceCart = findViewById(R.id.promotionPriceCart);
        shipPriceCart = findViewById(R.id.shipPriceCart);
        intoPriceCart = findViewById(R.id.intoPriceCart);
        quantityCart = findViewById(R.id.quantityCart);
        mData = FirebaseDatabase.getInstance().getReference();

        int total = 0;
        int promotion = 0;
        int ship =0;
        int into = 0;

        database = new Database(getApplicationContext());
        productArrayList = database.readALLData();


        if(productArrayList.size() != 0)
        {
            customCartGridviewAdapter = new custom_cart_gridview_adapter(getApplicationContext(), productArrayList);
            cart_list.setAdapter(customCartGridviewAdapter);

            cart_list.setVisibility(View.VISIBLE);
            cart_textview.setVisibility(View.INVISIBLE);
        }
        else
        {
            cart_list.setVisibility(View.INVISIBLE);
            cart_textview.setVisibility(View.VISIBLE);
        }

        registerForContextMenu(cart_list);

        for(int i = 0; i < productArrayList.size(); i++){
            total += productArrayList.get(i).getPrice() * productArrayList.get(i).getQuantity();
        }

        NumberFormat formatter = new DecimalFormat("#,###");
        totalPriceCart.setText(formatter.format(total) + " VNĐ");

        shipPriceCart.setText("30,000 VNĐ");

        into = total + 30000;
        intoPriceCart.setText(formatter.format(into) + " VNĐ");
        promotionPriceCart.setText("0 VNĐ");

        paymentCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i<productArrayList.size(); i++){
                    cartProduct product = new cartProduct(productArrayList.get(i));
                    String sizeCart = null;
                    if(product.getSize().equals("Size S")){
                        sizeCart = "sizeS";
                    }else if(product.getSize().equals("Size M")){
                        sizeCart = "sizeM";
                    }else if(product.getSize().equals("Size L")){
                        sizeCart = "sizeL";
                    }else if(product.getSize().equals("Size XL")){
                        sizeCart = "sizeXL";
                    }
                    String finalSizeCart = sizeCart;

//                    ArrayList<Integer> listSize = new ArrayList<>();
//                    Intent intent = getIntent();
//                    String temp = intent.getStringExtra("quantity");
//                    int size = Integer.parseInt(temp);
//                    listSize.add(size);



                    mData.child(product.getName()).child("Màu").child(product.getColor()).child(finalSizeCart).addValueEventListener(new ValueEventListener() {
                        boolean flag = false;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int temp = snapshot.getValue(Integer.class);

                            if(product.getQuantity()>temp){
                                Toast.makeText(getApplicationContext(),"Số lượng sản phẩm của cửa hàng không đủ", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else{
                                if(!flag){
                                    int newsize = temp - product.getQuantity();
                                    mData.child(product.getName()).child("Màu").child(product.getColor()).child(finalSizeCart).setValue(newsize);
                                    database.deleteOneRow(product.getId());
                                    productArrayList = database.readALLData();
                                    customCartGridviewAdapter = new custom_cart_gridview_adapter(getApplicationContext(), productArrayList);
                                    cart_list.setAdapter(customCartGridviewAdapter);
                                    flag = true;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

//                    if(product.getQuantity()>listSize.get(i)){
//                        Toast.makeText(getApplicationContext(),"Số lượng sản phẩm của cửa hàng không đủ", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    else{
//                        int newsize = listSize.get(i) - product.getQuantity();
//                        mData.child(product.getName()).child("Màu").child(product.getColor()).child(finalSizeCart).setValue(newsize);
//                    }
                }
//                database.deleteAll();
                Toast.makeText(getApplicationContext(),"Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                cart_list.setVisibility(View.INVISIBLE);
                cart_textview.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;

        cartProduct product = (cartProduct) cart_list.getItemAtPosition(index);
        switch (item.getItemId()){
            case R.id.delete_item: {
                database.deleteOneRow(product.getId());

                productArrayList = database.readALLData();
                customCartGridviewAdapter = new custom_cart_gridview_adapter(getApplicationContext(), productArrayList);
                cart_list.setAdapter(customCartGridviewAdapter);

                int total = 0;
                for(int i = 0; i < productArrayList.size(); i++){
                    total += productArrayList.get(i).getPrice();
                }

                NumberFormat formatter = new DecimalFormat("#,###");
                totalPriceCart.setText(formatter.format(total) + " VNĐ");
                int into = total + 30000;
                intoPriceCart.setText(formatter.format(into) + " VNĐ");
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }
}