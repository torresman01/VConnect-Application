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

public class ForgetPasswordActivity extends AppCompatActivity {

    Button callSignup, accountReset;
    TextInputLayout getEmail;

    FirebaseAuth auth;

    //Form validation
//    private Boolean validateEmail(){
//        String val = getEmail.getEditText().getText().toString();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
//
//        if (val.isEmpty()){
//            getEmail.setError("Email cannot be empty");
//            return false;
//        }
//        else if (emailPattern.matches(String.valueOf(getEmail))){
//            getEmail.setError("Wrong Email Format");
//            return true;
//        } else {
//            getEmail.setError(null);
//            return true;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        callSignup = findViewById(R.id.signup);
        getEmail = findViewById(R.id.emailAddress);
        accountReset = findViewById(R.id.passwordReset);

        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        accountReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getEmail.getEditText().getText().toString();
                if (email.equals("")){
                    Toast.makeText(ForgetPasswordActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ForgetPasswordActivity.this, "Reset link sent to email!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgetPasswordActivity.this, loginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ForgetPasswordActivity.this, "ERROR! Unable to send reset link", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}