package com.example.fashionapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    FirebaseAuth auth;
    Button loginBtn;
    EditText emailLogin, passwordLogin;
    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    ImageButton googleSign;
    BeginSignInRequest signInRequest;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private TextView register, forgotPassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        loginBtn = (Button) view.findViewById(R.id.loginBtn);
        emailLogin = (EditText) view.findViewById(R.id.emailLogin);
        passwordLogin = (EditText) view.findViewById(R.id.passwordLogin);
        register = (TextView) view.findViewById(R.id.register);
        googleSign = (ImageButton) view.findViewById(R.id.googleSign);
        forgotPassword = view.findViewById(R.id.forgotPassword);

        if(auth.getCurrentUser() != null){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.contentContainer, new AccountDetailFragment(), null);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.contentContainer, new ForgorPasswordFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.contentContainer, new RegisterFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private void loginUser() {

        String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();

        if(TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password)){
            Toast.makeText(getContext(), "Please fill all fields!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.contentContainer, new AccountDetailFragment(), null);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                            Toast.makeText(getContext(), "Login successful!",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Error: "+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
