package com.example.fashionapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingProfileFragment extends Fragment {

    EditText oldPassword, newPassword;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String confirmPassword;
    Button changePasswordBtn;
    TextView hintPass;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingProfileFragment newInstance(String param1, String param2) {
        SettingProfileFragment fragment = new SettingProfileFragment();
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
        return inflater.inflate(R.layout.fragment_setting_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String newPass, oldPass;
        oldPassword = view.findViewById(R.id.currentPasswordSetting);
        newPassword = view.findViewById(R.id.newPasswordSetting);
        changePasswordBtn = view.findViewById(R.id.changePasswordSetting);
        hintPass = view.findViewById(R.id.hintPass);

        newPass = newPassword.getText().toString();
        oldPass = oldPassword.getText().toString();

        FirebaseUser user = auth.getCurrentUser();

        fStore.collection("Users")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots
                                .getDocuments();
                        for (DocumentSnapshot snapshot: snapshotList){
                            hintPass.setText(snapshot.getString("password"));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newPassword.getText().length() < 6) {
                    Toast.makeText(getContext(), "Password length must be greater than 6 letter.",
                            Toast.LENGTH_SHORT).show();
                    newPassword.requestFocus();
                    return;
                }

                if (!oldPassword.getText().toString().equals(hintPass.getText().toString())) {
                    oldPassword.setError("Your old password is not correct");
                    oldPassword.requestFocus();
                    return;
                }

                user.updatePassword(newPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Please check your email to reset password!",
                                            Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Toast.makeText(getContext(), "Try again, something wrong happened!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}