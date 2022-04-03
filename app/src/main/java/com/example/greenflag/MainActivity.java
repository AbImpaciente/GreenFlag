package com.example.greenflag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        btnOpen.setOnClickListener(view -> {
            Intent createAccount = new Intent(getApplicationContext(), CreateAccount.class);
            startActivity(createAccount);
        });
    }

    private void initViews() {
        btnOpen = findViewById(R.id.btn_login_todo);
    }
}