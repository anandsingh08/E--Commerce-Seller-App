package com.example.mastsilp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText email;
    EditText pass;
    Button login,signup;

    FirebaseAuth auth;

    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        auth=FirebaseAuth.getInstance();

        spinner.setVisibility(View.GONE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);

            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = auth.getCurrentUser();
                                    spinner.setVisibility(View.GONE);
                                    updateUI(user);
                                    email.setText("");
                                    pass.setText("");
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    spinner.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });


    }

    /*
  @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser!=null){
        updateUI(currentUser);}
    }*/



    public void updateUI(FirebaseUser currentuser){
        Intent mainIntent=new Intent(this,MainActivity.class);
        startActivity(mainIntent);
    }
}
