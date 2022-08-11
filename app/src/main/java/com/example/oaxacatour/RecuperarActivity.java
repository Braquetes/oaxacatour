package com.example.oaxacatour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;

public class RecuperarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
    }

    public void solicitar(View v) throws JSONException {
        RecuperarActivity createPackageContext;
        Intent i = new Intent(createPackageContext = RecuperarActivity.this, CodigoActivity.class);
        startActivity(i);
    }
}