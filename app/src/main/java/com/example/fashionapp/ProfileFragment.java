package com.example.fashionapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    ArrayAdapter<CharSequence> genderAdapter;
    Spinner genderProfile;
    TextView birthdayProfile, emailProfile;
    EditText fullNameProfile, phoneProfile, addressProfile;
    Button updateProfileBtn;
    String userID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        genderProfile = view.findViewById(R.id.genderProfile);
        birthdayProfile = view.findViewById(R.id.birthdayProfile);
        fullNameProfile = view.findViewById(R.id.fullNameProfile);
        emailProfile = view.findViewById(R.id.emailProfile);
        phoneProfile = view.findViewById(R.id.phoneProfile);
        addressProfile = view.findViewById(R.id.addressProfile);
        updateProfileBtn = view.findViewById(R.id.updateProfileBtn);

        genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_array,
                R.layout.spinner_layout);

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderProfile.setAdapter(genderAdapter);

        initDatePicker1();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        fStore.collection("Users")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots
                                .getDocuments();
                        for (DocumentSnapshot snapshot: snapshotList){
                            fullNameProfile.setText(snapshot.getString("name"));
                            emailProfile.setText(snapshot.getString("email"));
                            phoneProfile.setText(snapshot.getString("phone"));
                            addressProfile.setText(snapshot.getString("address"));
                            birthdayProfile.setText(snapshot.getString("birthday"));
                            genderProfile.setSelection(getIndex(genderProfile,
                                    snapshot.getString("gender").toString()));
                            userID = snapshot.getId();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Map<String, Object> map = new HashMap<>();
//                map.put("name", fullNameProfile.getText());
//                map.put("phone", phoneProfile.getText());
//                map.put("gender", genderProfile.getSelectedItem());
//                map.put("address", addressProfile.getText());
//                map.put("birthday", birthdayProfile.getText());

                fStore.collection("Users")
                        .document(userID)
                        .update(
                                "name", fullNameProfile.getText().toString(),
                "phone", phoneProfile.getText().toString(),
                                    "gender", genderProfile.getSelectedItem().toString(),
                                    "address", addressProfile.getText().toString(),
                                    "birthday", birthdayProfile.getText().toString()
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                fragmentTransaction.replace(R.id.contentContainer, new AccountDetailFragment(), null);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private int getIndex(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s))
                return i;
        }
        return 0;
    }

    private void initDatePicker1() {
        DatePickerDialog.OnDateSetListener dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                birthdayProfile.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener1, year, month, day);

        birthdayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }
}