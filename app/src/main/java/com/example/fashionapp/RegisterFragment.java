package com.example.fashionapp;

import android.app.Dialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

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

    private EditText emailRegister, passwordRegister, confirmPasswordRegister, codeEditText;
    private TextView resendCodeBtn, wrongCodeTextView;
    private Button registerBtn = null, cancelBtn = null, verifyBtn = null;
    int code = 0;
    final String username = "phanchibap0007@gmail.com";
    final String password = "Pcb0941819910";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailRegister = view.findViewById(R.id.emailRegister);
        passwordRegister = view.findViewById(R.id.passwordRegister);
        confirmPasswordRegister = view.findViewById(R.id.confirmPasswordRegister);

        registerBtn = view.findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailRegister.getText().toString().compareTo("") != 0) {
                    showVerificationcodeDialog();
                    sendVerificationCode();
                }
                else
                {
                    Toast.makeText(getContext(), "Please fill all fields!", Toast.LENGTH_LONG).show();
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void sendVerificationCode(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            code = (int) Math.floor(((Math.random() * 899999) + 100000));
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailRegister.getText().toString().trim()));
            message.setSubject("You've got your verification code");
            message.setText("Your Verification Code is " + "[" + code +"]\n" +
                    "This Verification Code will be expired in 30 minutes.\n" +
                    "\n" +
                    "Thanks!\n" +
                    "The EAKTION Team\n" +
                    "\n" +
                    "*This e-mail was sent from a notification-only address that cannot accept incoming e-mails. Please do not reply to this message");
            Transport.send(message);
            Toast.makeText(getContext(), "Email send successfully!", Toast.LENGTH_LONG).show();
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void showVerificationcodeDialog(){
        Dialog verifyDialog = new Dialog(RegisterFragment.this.getContext());
        verifyDialog.setContentView(R.layout.verify_dialog);
        verifyDialog.show();


        resendCodeBtn = verifyDialog.findViewById(R.id.resendCodeBtn);
        cancelBtn = verifyDialog.findViewById(R.id.cancelBtn);
        verifyBtn = verifyDialog.findViewById(R.id.verifyBtn);
        wrongCodeTextView = verifyDialog.findViewById(R.id.wrongCodeTextView);
        codeEditText = verifyDialog.findViewById(R.id.codeEditText);

        resendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyDialog.dismiss();
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(codeEditText.getText().toString().trim().compareTo(String.valueOf(code))==0)
                {
                    verifyDialog.dismiss();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.contentContainer, new LoginFragment(), null);
                    fragmentTransaction.commit();

                    Toast.makeText(getContext(), "Register account successfully!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    wrongCodeTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}