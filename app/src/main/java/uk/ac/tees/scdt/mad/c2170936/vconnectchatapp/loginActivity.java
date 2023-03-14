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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    Button callSignUp, loginBtn, forgetPasswordBtn;
    TextInputLayout regEmail, regPassword;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    //Using firebase to check if user account has been created already then login automatically.
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Intent intent = new Intent(loginActivity.this,HomeDrawerActivity.class);
            startActivity(intent);
        }
    }

    //Form validation
    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";

        if (val.isEmpty()){
            regEmail.setError("Email cannot be empty");
            return false;
        }
        else {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPassword.setError("Password cannot be empty");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }

//    //Save data in Firebase on button click
//    public void loginUser(View view) {
//        if (!validatePhone() | !validatePassword()){
//            return;
//        }
////        else{
////            isUSer();
////        }
//
//
//    }

//    private void isUSer() {
//        String userEnteredPhone = regPhone.getEditText().getText().toString().trim();
//        String userEnteredPassword = regPassword.getEditText().getText().toString().trim();
//
//        //Firebase call
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
//        Query checkUser = reference.orderByChild("phone").equalTo(userEnteredPhone);
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists()){
//
//                    regPhone.setError(null);
//                    regPhone.setErrorEnabled(false);
//
//                    String passwordFromDB = dataSnapshot.child(userEnteredPhone).child("password").getValue(String.class);
//
//                    if (passwordFromDB.equals(userEnteredPassword)){
//
//                        regPhone.setError(null);
//                        regPhone.setErrorEnabled(false);
//
//                        String nameFromDB = dataSnapshot.child(userEnteredPhone).child("name").getValue(String.class);
//                        String emailFromDB = dataSnapshot.child(userEnteredPhone).child("email").getValue(String.class);
//                        String phoneFromDB = dataSnapshot.child(userEnteredPhone).child("phone").getValue(String.class);
//
//                        Intent intent = new Intent(getApplicationContext(),PhoneAuthActivity.class);
//
//                        intent.putExtra("name",nameFromDB);
//                        intent.putExtra("email",emailFromDB);
//                        intent.putExtra("phone",phoneFromDB);
//
//                        startActivity(intent);
//                    }
//
//                    else {
//
//                        regPassword.setError("Wrong Password");
//                        regPassword.requestFocus();
//                    }
//                }
//                else {
//                    regPhone.setError("No such user exist");
//                    regPhone.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks to all xml elements in activity_login.xml
        callSignUp = findViewById(R.id.signup);
        loginBtn = findViewById(R.id.acctLogin);
        regEmail = findViewById(R.id.emailAddress);
        regPassword = findViewById(R.id.userPassword);
        forgetPasswordBtn = findViewById(R.id.forget_password);

        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                if (!validateEmail() | !validatePassword()) {
                    return;
                } else {
                    signing(email,password);
                }
            }
        });

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }


    public void signing(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(loginActivity.this, "Sign-in Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, HomeDrawerActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(loginActivity.this, "Failed! Please reconfirm sign-in details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}