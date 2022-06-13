package com.example.fashionapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fashionapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    private EditText emailRegister, passwordRegister, confirmPasswordRegister, nameRegister,
            phoneRegister, genderRegister, addressRegister;
    private Button registerBtn;
    private TextView login, birthdayRegister;
    private  DatePickerDialog datePickerDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        login = (TextView) view.findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.contentContainer, new LoginFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        birthdayRegister = (TextView) view.findViewById(R.id.birthdayRegister);

        emailRegister = view.findViewById(R.id.emailRegister);
        passwordRegister = view.findViewById(R.id.passwordRegister);
        confirmPasswordRegister = view.findViewById(R.id.confirmPasswordRegister);
        nameRegister = view.findViewById(R.id.nameRegister);
        phoneRegister = view.findViewById(R.id.phoneRegister);
        genderRegister = view.findViewById(R.id.genderRegister);
        addressRegister = view.findViewById(R.id.addressRegister);


        registerBtn = view.findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();


//                final String username = "phanchibap0007@gmail.com";
//                final String password = "Pcb0941819910";
//
////                String messageToSend =
//                Properties properties = new Properties();
//                properties.put("mail.smtp.auth", "true");
//                properties.put("mail.smtp.starttls.enable", "true");
//                properties.put("mail.smtp.host", "smtp.gmail.com");
//                properties.put("mail.smtp.port", "587");
//                Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
//                    PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//                try {
//                    Message message = new MimeMessage(session);
//                    message.setFrom(new InternetAddress(username));
//                }
//                catch ()
            }
        });

        initDatePicker1();
    }

    private void createUser() {
        String name = nameRegister.getText().toString();
        String email = emailRegister.getText().toString();
        String password = passwordRegister.getText().toString();
        String confirmPassword = confirmPasswordRegister.getText().toString();
        String birthday = birthdayRegister.getText().toString();
        String gender = genderRegister.getText().toString();
        String address = addressRegister.getText().toString();
        String phone = phoneRegister.getText().toString();

        if(TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword) ||
                TextUtils.isEmpty(birthday) ||
                TextUtils.isEmpty(gender) ||
                TextUtils.isEmpty(address)){
            Toast.makeText(getContext(), "Please fill all fields!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(getContext(), "Password length must be greater than 6 letter.",
                    Toast.LENGTH_SHORT).show();
        }

        if(passwordRegister.equals(password)){
            Toast.makeText(getContext(), "Password and confirm password must be match.",
                    Toast.LENGTH_SHORT).show();
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            UserModel userModel = new UserModel(name, phone, email, password,
                                    gender, birthday, address);
                            String id = task.getResult().getUser().getUid();
                            userInfo();

                            Toast.makeText(getContext(), "Registration successful!",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Error: "+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void userInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", nameRegister.getText().toString());
        map.put("phone", phoneRegister.getText().toString());
        map.put("email", emailRegister.getText().toString());
        map.put("password", passwordRegister.getText().toString());
        map.put("gender", genderRegister.getText().toString());
        map.put("birthday", birthdayRegister.getText().toString());
        map.put("address", addressRegister.getText().toString());

        fStore.collection("Users")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                });
    }

    private void initDatePicker1() {
        DatePickerDialog.OnDateSetListener dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                birthdayRegister.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener1, year, month, day);

        birthdayRegister.setOnClickListener(new View.OnClickListener() {
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