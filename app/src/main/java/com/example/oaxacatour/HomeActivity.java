package com.example.oaxacatour;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oaxacatour.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.bumptech.glide.Glide;

import org.json.JSONException;


public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;
    private ImageView photoImageView;

    TextView name;

    Bundle datos;

    private GoogleApiClient googleApiClient;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Toast.makeText(this, "Data" + mAuth, Toast.LENGTH_SHORT).show();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this::onConnectionFailed)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //datos = getIntent().getExtras();
        //String datosObtenidos = datos.getString("usuario");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this, "currentUser" + currentUser.getUid(), Toast.LENGTH_SHORT).show();
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            //nameTextView.setText(account.getDisplayName());
            //setContentView(R.layout.nav_header_main);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            //navigationView.addHeaderView(navigationView);
            //View hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
            View hView =  navigationView.getHeaderView(0);
            ImageView imgvw = (ImageView)hView.findViewById(R.id.foto);
            TextView nombre = (TextView)hView.findViewById(R.id.nombre);
            TextView correo = (TextView)hView.findViewById(R.id.correo);
            Glide.with(this).load(account.getPhotoUrl()).into(imgvw);
            nombre.setText(account.getDisplayName());
            correo.setText(account.getEmail());
            //name = (TextView)findViewById(R.id.nombre);
            //name.setText(account.getDisplayName());
            //emailTextView.setText(account.getEmail());
            //idTextView.setText(account.getId());

            //Glide.with(this).load(account.getPhotoUrl()).into(photoImageView);
        } else if(currentUser != null){
            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View hView =  navigationView.getHeaderView(0);
            ImageView imgvw = (ImageView)hView.findViewById(R.id.foto);
            TextView nombre = (TextView)hView.findViewById(R.id.nombre);
            TextView correo = (TextView)hView.findViewById(R.id.correo);
            Glide.with(this).load(R.drawable.oaxacatour).into(imgvw);
            nombre.setText("Nombre de usuario");
            correo.setText(currentUser.getEmail());
        }else{
            goLogInScreen();
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void revoke(View view) {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo revocar el acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void mapa(View v) throws JSONException {
        Intent i = new Intent(this, MapsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}