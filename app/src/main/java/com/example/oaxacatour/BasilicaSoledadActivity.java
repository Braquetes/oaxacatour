package com.example.oaxacatour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BasilicaSoledadActivity extends AppCompatActivity {
    private TextView titulo,descripcion,direccion,horario,nombre,precios;
    private ImageView imagen;

    private DatabaseReference mDatabase;

    ArrayList<String> ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basilica_soledad);

        titulo = (TextView)findViewById(R.id.titulo);
        descripcion = (TextView)findViewById(R.id.descripcion);
        direccion = (TextView)findViewById(R.id.direccion);
        horario = (TextView)findViewById(R.id.horario);
        imagen = (ImageView)findViewById(R.id.imageView);
        nombre = (TextView)findViewById(R.id.nombre);
        precios = (TextView)findViewById(R.id.precios);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        data();
    }

    private void data(){
        mDatabase.child("basilicaSoledad").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String dato = ds.getValue(String.class);
                        ar.add(dato);
                    }
                    descripcion.setText(ar.get(0));
                    direccion.setText(ar.get(1));
                    horario.setText(ar.get(2));
                    precios.setText(ar.get(5));
                    titulo.setText(ar.get(4));
                    Glide.with(BasilicaSoledadActivity.this).load(ar.get(3)).into(imagen);

                }
                else {
                    Toast.makeText(BasilicaSoledadActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }

        });
    }

    public void mapa(View v){
        Intent i = new Intent(this, MapsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}