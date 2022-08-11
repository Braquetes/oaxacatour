package com.example.oaxacatour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }

    public void cambiar(View v) throws JSONException {
        PasswordActivity createPackageContext;
        Intent i = new Intent(createPackageContext = PasswordActivity.this, LoginActivity.class);
        startActivity(i);
    }
}