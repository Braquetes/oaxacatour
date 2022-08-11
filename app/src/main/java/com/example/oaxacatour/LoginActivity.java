package com.example.oaxacatour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    public static final int SIGN_IN_CODE = 777;

    private String usuario;

    //VARIABLES DE LOS DATOS A REGISTRAR
    private String correo="";
    private String contra="";

    private EditText email;
    private EditText password;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    public void login(View v){

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        correo = email.getText().toString();
        contra = password.getText().toString();

        if(!correo.isEmpty() && !contra.isEmpty()){
            if(password.length() >= 8){

                EditText emailText = (EditText)findViewById(R.id.email);
                String email = emailText.getText().toString();
                EditText passwordText = (EditText)findViewById(R.id.password);
                String password = passwordText.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        // Name, email address, and profile photo Url
                                        String name = user.getDisplayName();
                                        String email = user.getEmail();
                                        Uri photoUrl = user.getPhotoUrl();

                                        // Check if user's email is verified
                                        boolean emailVerified = user.isEmailVerified();

                                        String uid = user.getUid();

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("email", email);
                                        map.put("uid", uid);

                                        mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("users").child(uid).setValue(map);

                                        Toast.makeText(LoginActivity.this, "user: " + map, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

            }else{
                Toast.makeText(LoginActivity.this, "Ingresa una contraseña mayor a 8 ", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LoginActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void registro(View v) throws JSONException {
        LoginActivity createPackageContext;
        Intent i = new Intent(createPackageContext = LoginActivity.this, RegistroActivity.class);
        startActivity(i);
    }

    public void recuperar(View v) throws JSONException {
        LoginActivity createPackageContext;
        Intent i = new Intent(createPackageContext = LoginActivity.this, RecuperarActivity.class);
        startActivity(i);
    }
}