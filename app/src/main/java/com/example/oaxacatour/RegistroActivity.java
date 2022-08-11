package com.example.oaxacatour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

public class RegistroActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btnRegistrarse;

    //VARIABLES DE LOS DATOS A REGISTRAR
    private String correo="";
    private String contra="";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    public static final int SIGN_IN_CODE = 777;

    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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

        signInButton = (SignInButton) findViewById(R.id.signInButton);

        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });

        email = (EditText) findViewById(R.id.TxtEmail);
        password = (EditText) findViewById(R.id.TxtPassword);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = email.getText().toString();
                contra = password.getText().toString();

                if(!correo.isEmpty() && !contra.isEmpty()){
                    if(password.length() >= 8){
                        registrarUser();
                    }else{
                        Toast.makeText(RegistroActivity.this, "Ingresa una contraseña mayor a 8 ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            goMainScreen();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void registrarUser(){
        mAuth.createUserWithEmailAndPassword(correo,contra).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user !=null){
                        Toast.makeText(RegistroActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistroActivity.this, HomeActivity.class));
                        finish();
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegistroActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registro(View v) throws JSONException {
        RegistroActivity createPackageContext;
        Intent i = new Intent(createPackageContext = RegistroActivity.this, HomeActivity.class);
        startActivity(i);
    }

    public void singIn(View v){
        RegistroActivity createPackageContext;
        Intent i = new Intent(createPackageContext = RegistroActivity.this, LoginActivity.class);
        startActivity(i);
    }
}