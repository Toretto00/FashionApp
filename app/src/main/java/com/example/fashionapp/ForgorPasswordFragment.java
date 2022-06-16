package com.example.fashionapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgorPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgorPasswordFragment extends Fragment {

    private EditText emailResetPassword;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button resetPasswordBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForgorPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgorPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgorPasswordFragment newInstance(String param1, String param2) {
        ForgorPasswordFragment fragment = new ForgorPasswordFragment();
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
        return inflater.inflate(R.layout.fragment_forgor_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailResetPassword = view.findViewById(R.id.emailResetPassword);
        resetPasswordBtn = view.findViewById(R.id.resetPasswordBtn);
        
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
        
    }

    private void resetPassword() {
        String email = emailResetPassword.getText().toString();

        if(email.isEmpty()){
            emailResetPassword.setError("Email is required");
            emailResetPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailResetPassword.setError("Please provide valid Email!");
            emailResetPassword.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Please check your email to reset password!",
                            Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.contentContainer, new LoginFragment(), null);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else{
                    Toast.makeText(getContext(), "Try again, something wrong happened!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}