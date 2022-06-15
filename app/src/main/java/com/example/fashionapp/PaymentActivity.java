package com.example.fashionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private Spinner provinceCity, district, ward;
    private ArrayAdapter<CharSequence> provinceAdapter, districtAdapter, wardAdapter;
    private String selectedProvince, selectedDistrict, selectedWard;
    EditText namePayment, phonePayment, emailPayment, detailAddressPayment;
    TextView subtotalTV, discountTV, feeTV, totalTV;
    CheckBox paymentDeliveryCB, paymentCardCB;
    ArrayList<cartProduct> productArrayList = new ArrayList<cartProduct>();
    int fee, discount;
    Button completeShoppingBtn;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);

        subtotalTV = findViewById(R.id.subTotalTV);
        discountTV = findViewById(R.id.discountTV);
        feeTV = findViewById(R.id.feeTV);
        totalTV = findViewById(R.id.totalTV);
        completeShoppingBtn = findViewById(R.id.completeShoppingBtn);
        namePayment = findViewById(R.id.namePayment);
        emailPayment = findViewById(R.id.emailPayment);
        phonePayment = findViewById(R.id.phonePayment);
        detailAddressPayment = findViewById(R.id.detailAddressPayment);

        fee = Integer.parseInt(feeTV.getText().toString());
        discount = Integer.parseInt(discountTV.getText().toString());

        provinceCity = findViewById(R.id.provinceCity);

        provinceAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_vietnam_province, R.layout.spinner_layout);

        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        provinceCity.setAdapter(provinceAdapter);

        provinceCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district = findViewById(R.id.district);

                selectedProvince = provinceCity.getSelectedItem().toString();

                int parentID = parent.getId();
                if (parentID == R.id.provinceCity) {
                    switch (selectedProvince) {
                        case "* Select province/city":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_default_districts, R.layout.spinner_layout);
                            break;
                        case "Hà Nội":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_hanoi_districts, R.layout.spinner_layout);
                        case "Thành phố Hồ Chí Minh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_hoChiMinh_districts, R.layout.spinner_layout);
                        default:
                            break;
                    }
                    districtAdapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    district.setAdapter(districtAdapter);

                    district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ward = findViewById(R.id.ward);

                            selectedDistrict = district.getSelectedItem().toString();

                            int parentIDD = parent.getId();
                            if (parentIDD == R.id.district) {
                                switch (selectedDistrict) {
                                    case "Thành phố Thủ Đức":
                                        wardAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_tpthuduc_ward, R.layout.spinner_layout);
                                        break;
                                    case "* Select district":
                                        wardAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_default_ward, R.layout.spinner_layout);
                                        break;
                                    default:
                                        break;
                                }
                                wardAdapter.setDropDownViewResource(
                                        android.R.layout.simple_spinner_dropdown_item
                                );
                                ward.setAdapter(wardAdapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FirebaseUser user = fAuth.getCurrentUser();

        if(user != null){
            fStore.collection("Users")
                    .whereEqualTo("email", user.getEmail())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> snapshotList = queryDocumentSnapshots
                                    .getDocuments();
                            for (DocumentSnapshot snapshot: snapshotList){
                                namePayment.setText(snapshot.getString("name"));
                                emailPayment.setText(snapshot.getString("email"));
                                phonePayment.setText(snapshot.getString("phone"));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        database = new Database(getApplicationContext());
        productArrayList = database.readALLData();

        RecyclerView recyclerViewPayment = findViewById(R.id.recyclerViewPayment);
        recyclerViewPayment.setHasFixedSize(true);
        recyclerViewPayment.setLayoutManager(new LinearLayoutManager(this));

        CustomRecyclerview[] customRecyclerview = new CustomRecyclerview[productArrayList.size()];

        for (int i = 0; i < productArrayList.size(); i++){
            customRecyclerview[i] = new CustomRecyclerview(productArrayList.get(i).getLinkImage(),
                    productArrayList.get(i).getName(),
                    productArrayList.get(i).getColor() + ", " + productArrayList.get(i).getSize(),
                    productArrayList.get(i).getPrice(),
                    productArrayList.get(i).getQuantity());
        }

        CustomRecyclerviewAdapter customRecyclerviewArrayList = new CustomRecyclerviewAdapter(
                this, customRecyclerview
        );

        recyclerViewPayment.setAdapter(customRecyclerviewArrayList);

        paymentCardCB = findViewById(R.id.paymentCardCB);
        paymentDeliveryCB = findViewById(R.id.paymentDeliveryCB);

        paymentDeliveryCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    paymentCardCB.setChecked(false);
                }
            }
        });

        paymentCardCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    paymentDeliveryCB.setChecked(false);
                }
            }
        });

        int sum = 0;
        for(int i = 0; i < productArrayList.size(); i++){
            sum += productArrayList.get(i).getPrice() * productArrayList.get(i).getQuantity();
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(sum);
        subtotalTV.setText(formattedNumber + " VND");

        int finalTotal = sum - discount + fee;

        String formatterTotal = formatter.format(finalTotal);
        totalTV.setText(formatterTotal + " VND");

        String paymentMethod;
        if(paymentDeliveryCB.isChecked())
            paymentMethod = "Pay on delivery";
        else
            paymentMethod = "Pay by Card";

        completeShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", namePayment.getText().toString());
                    map.put("email", emailPayment.getText().toString());
                    map.put("phone", phonePayment.getText().toString());
                    map.put("province", provinceCity.getSelectedItem().toString());
                    map.put("district", district.getSelectedItem().toString());
                    map.put("ward", ward.getSelectedItem().toString());
                    map.put("detailAddress", detailAddressPayment.getText().toString());
                    map.put("paymentMethod", paymentMethod);
                    map.put("total", totalTV.getText().toString());
                    map.put("feeShipping", feeTV.getText().toString());

                    Map<String, Map> mapParent = new HashMap<>();
                    for (int i = 0; i < productArrayList.size(); i++) {
                        Map<String, Object> mapChild = new HashMap<>();
                        mapChild.put("nameProduct", productArrayList.get(i).getName());
                        mapChild.put("colorProduct", productArrayList.get(i).getColor());
                        mapChild.put("sizeProduct", productArrayList.get(i).getSize());
                        mapChild.put("priceProduct", productArrayList.get(i).getPrice());
                        mapChild.put("quantityProduct", productArrayList.get(i).getQuantity());
                        mapParent.put("order" + String.valueOf(i), mapChild);
                    }
                    map.put("order", mapParent);
                    map.put("timeCreate", new Timestamp(new Date()));

                    fStore.collection("Payments")
                            .document("currentUser")
                            .collection("Delivering")
                            .add(map)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Payment success!",
                                            Toast.LENGTH_SHORT).show();
                                    database.deleteAll();
                                    startActivity(new Intent(PaymentActivity.this,
                                            MainActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error: " + e,
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                else{
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", namePayment.getText().toString());
                    map.put("email", emailPayment.getText().toString());
                    map.put("phone", phonePayment.getText().toString());
                    map.put("province", provinceCity.getSelectedItem().toString());
                    map.put("district", district.getSelectedItem().toString());
                    map.put("ward", ward.getSelectedItem().toString());
                    map.put("detailAddress", detailAddressPayment.getText().toString());
                    map.put("paymentMethod", paymentMethod);
                    map.put("total", totalTV.getText().toString());
                    map.put("feeShipping", feeTV.getText().toString());

                    Map<String, Map> mapParent = new HashMap<>();
                    for (int i = 0; i < productArrayList.size(); i++) {
                        Map<String, Object> mapChild = new HashMap<>();
                        mapChild.put("nameProduct", productArrayList.get(i).getName());
                        mapChild.put("colorProduct", productArrayList.get(i).getColor());
                        mapChild.put("sizeProduct", productArrayList.get(i).getSize());
                        mapChild.put("priceProduct", productArrayList.get(i).getPrice());
                        mapChild.put("quantityProduct", productArrayList.get(i).getQuantity());
                        mapParent.put("order" + String.valueOf(i), mapChild);
                    }
                    map.put("order", mapParent);
                    map.put("timeCreate", new Timestamp(new Date()));

                    fStore.collection("Payments")
                            .document("nonAccount")
                            .collection("Delivering")
                            .add(map)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Payment success!",
                                            Toast.LENGTH_SHORT).show();
                                    database.deleteAll();
                                    startActivity(new Intent(PaymentActivity.this,
                                            MainActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error: " + e,
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}