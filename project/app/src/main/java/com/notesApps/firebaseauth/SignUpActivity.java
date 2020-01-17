package com.notesApps.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    EditText emailEt , passwordEt;
    Button signUpBtn;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBar= findViewById(R.id.progSignUp);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null)
        {
            Intent intent = new Intent(SignUpActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }

        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        signUpBtn = findViewById(R.id.signUpBtn);


        signUpBtn.setOnClickListener(v->{
            progressBar.setVisibility(View.VISIBLE);
            doSignUp(emailEt.getText().toString() , passwordEt.getText().toString());
            Log.d("test", "onCreate: out");
        });

    }

    private void doSignUp(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("test", "onCreate: in method");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {


                    Log.d("test", "onCreate: success");

                    reference.child(mAuth.getCurrentUser().getUid())
                            .child("email").setValue(mAuth.getCurrentUser().getEmail())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.GONE);

                                    Intent intent = new Intent(SignUpActivity.this , MainActivity.class);
                                    startActivity(intent);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK) ;
                                    finish();
                                }
                            });



                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);

                    Log.d("test", "onCreate: fail");
                    // If sign in fails, display a message to the user.
                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                });

    }
}
