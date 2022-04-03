package com.example.greenflag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    AppCompatButton btnBack;
    AppCompatButton btnCreate;
    EditText etEmail;
    EditText etPass;
    EditText etConfirmPass;
    ImageView ivEmail;
    ImageView ivPass;
    ImageView ivPass2;

    List<Account> listAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        initViews();

        btnBack.setOnClickListener(view -> finish());

        btnCreate.setOnClickListener(view -> {
            Account account = new Account();
            account.email = etEmail.getText().toString();
            account.pass1 = etPass.getText().toString();
            account.pass2 = etConfirmPass.getText().toString();
            listAccount.add(account);
            Clear(etEmail);
            Clear(etPass);
            Clear(etConfirmPass);
            ivPass2.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
        });

        etEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = etEmail.getText().toString();
                IsValidEmail(email);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass1 = etPass.getText().toString();
                IsValidPass1(pass1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String pass1 = etPass.getText().toString();
                String pass2 = etConfirmPass.getText().toString();
                IsValidPass2(pass1, pass2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void Clear(EditText etGeneric) {
        etGeneric.getText().clear();
        etGeneric.setError(null);
        etGeneric.setBackgroundResource(R.drawable.white);
    }

    private void ShowError(EditText etGeneric, String message, ImageView ivGeneric) {
        etGeneric.setError(message);
        etGeneric.setBackgroundResource(R.drawable.failed);
        etGeneric.setTag(getString(R.string.incorrect));
        btnCreate.setEnabled(false);
        etGeneric.requestFocus();
        ivGeneric.setVisibility(View.INVISIBLE);
    }

    private void SetSuccess(EditText etGeneric, ImageView ivGeneric) {
        etGeneric.setBackgroundResource(R.drawable.succes);
        etGeneric.setTag(getString(R.string.correct));
        ivGeneric.setVisibility(View.VISIBLE);
    }

    private void IsValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean isValid = pattern.matcher(email).matches();
        // Is Valid Email?
        Account account;
        String error = "";
        for (int i = 0; i < listAccount.size(); i++) {
            account = listAccount.get(i);
            //do something with i
            if (account.email.equals(email)) {
//                ShowError(etEmail, "An account already exist for this Email", ivEmail);
                error = "An account already exist for this Email";
            }
        }
        if (!isValid) {
            // Incorrect Email
//            ShowError(etEmail, "Write a valid Email", ivEmail);
            error = "Write a valid Email";
        }
        if (!error.isEmpty()) {
            ShowError(etEmail, error, ivEmail);
        } else {
            SetSuccess(etEmail, ivEmail);
        }

        Clear(etPass);
        Clear(etConfirmPass);

        if (etEmail.getTag().toString().equals(getString(R.string.correct))) {
            etPass.setEnabled(true);
        } else {
            etPass.setEnabled(false);
            etConfirmPass.setEnabled(false);
        }
        ivPass2.setVisibility(View.INVISIBLE);
    }

    private void IsValidPass1(String pass1) {
        Pattern mPattern;
        mPattern = Pattern.compile(getString(R.string.regex_email));
        Matcher matcher = mPattern.matcher(pass1);
        Clear(etConfirmPass);
        if (!matcher.find()) {
            // Incorrect Pass
            ShowError(etPass, "The password you entered was not valid", ivPass);

        } else {
            SetSuccess(etPass, ivPass);
            etConfirmPass.setEnabled(true);
        }
    }

    private void IsValidPass2(String pass1, String pass2) {
        if (!pass1.equals(pass2)) {
            // Password doesn't match
            ShowError(etConfirmPass, "Your passwords don't match", ivPass2);

        } else {
            SetSuccess(etConfirmPass, ivPass2);
            btnCreate.setEnabled(true);
        }
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        btnCreate = findViewById(R.id.btn_create_todo);
        etEmail = findViewById(R.id.txt_email_into);
        etPass = findViewById(R.id.txt_pass_into);
        etConfirmPass = findViewById(R.id.txt_pass2_into);
        ivEmail = findViewById(R.id.iv_email_correct);
        ivPass = findViewById(R.id.iv_pass_correct);
        ivPass2 = findViewById(R.id.iv_pass2_correct);
        listAccount = new ArrayList<>();
    }
}