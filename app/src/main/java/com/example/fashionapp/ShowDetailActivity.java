package com.example.fashionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowDetailActivity extends AppCompatActivity {

    private Button continueShopping, addCart;
    private TextView productNameDetail, productQuantityDetail, productPriceDetail, productDescribeDetail;
    private ImageView productImageDetail;
    private Spinner productColorDetail;
    private EditText productInsertSize;
    private RadioButton productSizeS, productSizeM, productSizeL, productSizeXL;
    DatabaseReference mData;
    ArrayAdapter adapter;
    private List<String> listColor = new ArrayList<>();
    private ArrayList<colorProduct> list = new ArrayList<>();
    private ArrayList<cartProduct> productArrayList = new ArrayList<cartProduct>();
    Database database;

    String name, color, size, linkImage, describe, type;
    int quantity, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        productNameDetail = findViewById(R.id.productNameDetail);
        productQuantityDetail = findViewById(R.id.productQuantityDetail);
        productDescribeDetail = findViewById(R.id.productDescribeDetail);
        productImageDetail = findViewById(R.id.productImageDetail);
        productColorDetail = findViewById(R.id.productColorDetail);
        productPriceDetail = findViewById(R.id.productPriceDetail);
        productInsertSize = findViewById(R.id.productInsertSize);
        productSizeS = findViewById(R.id.productSizeS);
        productSizeM = findViewById(R.id.productSizeM);
        productSizeL = findViewById(R.id.productSizeL);
        productSizeXL = findViewById(R.id.productSizeXL);

        mData = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        productNameDetail.setText(name);

        price = Integer.valueOf(intent.getStringExtra("price"));
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(price);
        productPriceDetail.setText(formattedNumber + " VNĐ");

        String link = intent.getStringExtra("link");
        Picasso.get().load(link).into(productImageDetail);

        describe = intent.getStringExtra("describe");
        productDescribeDetail.setText(describe);

        ProductDetail();
        selectSpinner();

        addCart = findViewById(R.id.addToCartBtn);
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productInsertSize.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập số lượng mua. Vui lòng nhập!", Toast.LENGTH_SHORT).show();
                }
                else if(productSizeS.isChecked() == false && productSizeM.isChecked() == false && productSizeL.isChecked() == false && productSizeXL.isChecked() == false){
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn size cho sản phẩm.", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(productInsertSize.getText().toString().trim()) > Integer.parseInt(productQuantityDetail.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(), "Số lượng sản phẩm không đủ!", Toast.LENGTH_SHORT).show();
                }
                else{
                    addProductToCart();
//                    byExtras();
                }
            }
        });

        continueShopping = (Button) findViewById(R.id.continueShopping);
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowDetailActivity.this, MainActivity.class));
            }
        });
    }

    public void ProductDetail(){
        mData.child(name).child("Màu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    colorProduct color = dataSnapshot.getValue(colorProduct.class);
                    listColor.add(color.getColor());
                    list.add(color);
                }
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listColor);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                productColorDetail.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mData.child(name).child("describe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productDescribeDetail.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void selectSpinner() {
        productColorDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(int j=0; j<list.size();j++){
                    if(list.get(j).getColor() == productColorDetail.getSelectedItem().toString()){
                        colorProduct colorproduct = new colorProduct(list.get(j));
                        color = colorproduct.getColor();
                        linkImage = colorproduct.getLinkImage();
                        Picasso.get().load(linkImage).into(productImageDetail);

                        productSizeS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productQuantityDetail.setText(String.valueOf(colorproduct.getSizeS()));
                                size = "S";
                            }
                        });
                        productSizeM.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productQuantityDetail.setText(String.valueOf(colorproduct.getSizeM()));
                                size = "M";
                            }
                        });
                        productSizeL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productQuantityDetail.setText(String.valueOf(colorproduct.getSizeL()));
                                size = "L";
                            }
                        });
                        productSizeXL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productQuantityDetail.setText(String.valueOf(colorproduct.getSizeXL()));
                                size = "XL";
                            }
                        });
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    public void addProductToCart(){
        boolean flag = false;
        String id = null;
        int newquantity = 0;
        database = new Database(getApplicationContext());
        productArrayList = database.readALLData();

        for(int i = 0; i<productArrayList.size(); i++){
            cartProduct product = productArrayList.get(i);
            if(name.equals(product.getName()) && color.equals(product.getColor()) && size.equals(product.getSize())){
                flag = true;
                id = product.getId();
                String temp = productInsertSize.getText().toString();
                if(!"".equals(temp)){
                    quantity = Integer.parseInt(temp);
                }
                newquantity = quantity + Integer.parseInt(String.valueOf(product.getQuantity()));
            }
        }

        if(flag){
            database.updateData(id, name, color, size, newquantity, price, linkImage);
        }
        else{
            String temp = productInsertSize.getText().toString();
            if(!"".equals(temp)){
                quantity = Integer.parseInt(temp);
            }
            database.addData(name, color, size, quantity, price, linkImage);
        }
    }

    public void byExtras(){
        Intent intent = new Intent(ShowDetailActivity.this, CartActivity.class);
        intent.putExtra("quantity",productQuantityDetail.getText().toString());
        startActivity(intent);
    }
}