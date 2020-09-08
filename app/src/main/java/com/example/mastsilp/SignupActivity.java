package com.example.mastsilp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText emailsignup;
    EditText passsignup;
    Button register;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailsignup=findViewById(R.id.emailsignup);
        passsignup=findViewById(R.id.passwordsignup);
        register=findViewById(R.id.register);
        mAuth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(emailsignup.getText().toString(),passsignup.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Registered Successfully!",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else{Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();}
                            }
                        });

            }
        });
    }
}
