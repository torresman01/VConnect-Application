package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordActivity extends AppCompatActivity {

    Button profileLinkReset;
    TextInputLayout getEmail;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        profileLinkReset = findViewById(R.id.sendPasswordLink1);
        getEmail = findViewById(R.id.profileEmailAddress1);

        profileLinkReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getEmail.getEditText().getText().toString();
                if (email.equals("")){
                    Toast.makeText(ChangePasswordActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    passwordReset(email);
                }
            }
        });

        auth = FirebaseAuth.getInstance();

    }


    public void passwordReset(String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ChangePasswordActivity.this, "Reset link sent to email!", Toast.LENGTH_SHORT).show();
                    auth.signOut();
                    Intent intent = new Intent(ChangePasswordActivity.this, loginActivity.class);
                    startActivity(intent);

                    finish();

                }else{
                    Toast.makeText(ChangePasswordActivity.this, "ERROR! Unable to send reset link", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}