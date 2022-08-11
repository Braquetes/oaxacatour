package com.example.oaxacatour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;

public class CodigoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);
    }

    public void verificar(View v) throws JSONException {
        CodigoActivity createPackageContext;
        Intent i = new Intent(createPackageContext = CodigoActivity.this, PasswordActivity.class);
        startActivity(i);
    }
}